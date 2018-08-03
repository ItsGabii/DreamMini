package net.perfectdreams.dreammini.commands

import net.perfectdreams.libs.acf.BaseCommand
import net.perfectdreams.libs.acf.annotation.CatchUnknown
import net.perfectdreams.libs.acf.annotation.CommandAlias
import net.perfectdreams.libs.acf.annotation.CommandPermission
import net.perfectdreams.libs.acf.annotation.Default
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("hat|head")
@CommandPermission("dreammini.hat")
class HatCommand : BaseCommand() {
	@Default
	@CatchUnknown
	fun onCommand(p0: CommandSender, p3: Array<String>): Boolean {
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