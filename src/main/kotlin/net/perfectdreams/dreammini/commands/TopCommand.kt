package net.perfectdreams.dreammini.commands

import net.perfectdreams.libs.acf.BaseCommand
import net.perfectdreams.libs.acf.annotation.CommandAlias
import net.perfectdreams.libs.acf.annotation.CommandPermission
import net.perfectdreams.libs.acf.annotation.Default
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("top")
@CommandPermission("dreammini.top")
class TopCommand : BaseCommand() {
	@Default
	fun onCommand(p0: CommandSender, p3: Array<String>): Boolean {
		if (p0 is Player) {
			p0.teleport(p0.location.world.getHighestBlockAt(p0.location).location)
			p0.sendMessage("§aVocê chegou ao topo!")
		} else {
			p0.sendMessage("§cUsuário inválido!")
		}
		return true
	}
}