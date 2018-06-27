package net.perfectdreams.dreammini.commands

import net.perfectdreams.dreamcore.utils.commands.AbstractCommand
import net.perfectdreams.dreamcore.utils.translateColorCodes
import net.perfectdreams.dreamcore.utils.withoutPermission
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class LoreCommand : AbstractCommand("lore") {
	override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<String>): Boolean {
		if (!p0.hasPermission("dreammini.lore")) {
			p0.sendMessage(withoutPermission)
			return true
		}

		val name = p3.joinToString(" ").translateColorCodes()

		var user: Player? = null

		if (p0 is Player) {
			user = p0
		}

		if (user == null) {
			p0.sendMessage("§cUsuário inválido!")
			return true
		}

		val item = user.inventory.itemInMainHand
		val type = item?.type
		if (type != Material.AIR) {
			val meta = item.itemMeta
			if (name.isNotEmpty()) {
				val lore = name.split("\\n")
				meta.lore = lore
				item.itemMeta = meta

				user.sendMessage("§aAgora a descrição do item é...")
				for (entry in lore) {
					user.sendMessage("§5§o$entry")
				}
			} else {
				user.sendMessage("§aDescrição do item removida!")
			}
		} else {
			user.sendMessage("§cSegure um item na sua mão antes de usar!")
		}
		return true
	}
}