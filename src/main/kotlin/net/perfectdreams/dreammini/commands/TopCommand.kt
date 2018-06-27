package net.perfectdreams.dreammini.commands

import net.perfectdreams.dreamcore.utils.commands.AbstractCommand
import net.perfectdreams.dreamcore.utils.withoutPermission
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class TopCommand : AbstractCommand("top") {
	override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<String>): Boolean {
		if (!p0.hasPermission("dreammini.top")) {
			p0.sendMessage(withoutPermission)
			return true
		}

		if (p0 is Player) {
			p0.teleport(p0.location.world.getHighestBlockAt(p0.location).location)
			p0.sendMessage("§aVocê chegou ao topo!")
		} else {
			p0.sendMessage("§cUsuário inválido!")
		}
		return true
	}
}