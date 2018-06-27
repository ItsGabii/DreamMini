package net.perfectdreams.dreammini.commands

import net.perfectdreams.dreamcore.utils.commands.AbstractCommand
import net.perfectdreams.dreamcore.utils.withoutPermission
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class HatCommand : AbstractCommand("hat", listOf("head")) {
	override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<String>): Boolean {
		if (!p0.hasPermission("dreammini.hat")) {
			p0.sendMessage(withoutPermission)
			return true
		}

		var user: Player? = null

		if (p0 is Player) {
			user = p0
		}

		if (user == null) {
			p0.sendMessage("§cUsuário inválido!")
			return true
		}

		val item = user.inventory.itemInMainHand
		if (item != null) {
			val type = item.type
			user.inventory.helmet = item
			if (type != Material.AIR) {
				user.sendMessage("§aAdorei esse seu novo look amig@!")
			} else {
				user.sendMessage("§a#sdds do seu capacete")
			}
		} else {
			user.sendMessage("§cSegure um item/bloco na sua mão antes de usar!")
		}
		return true
	}
}