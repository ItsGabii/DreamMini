package net.perfectdreams.dreammini.commands

import net.perfectdreams.dreamcore.utils.commands.AbstractCommand
import net.perfectdreams.dreamcore.utils.withoutPermission
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class FlyCommand : AbstractCommand("fly", listOf("voar")) {
	override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<String>): Boolean {
		if (!p0.hasPermission("dreammini.fly")) {
			p0.sendMessage(withoutPermission)
			return true
		}

		var user: Player? = null

		if (p0 is Player) {
			user = p0
		}

		val playerName = p3.getOrNull(0)

		if (playerName != null) {
			user = Bukkit.getPlayer(playerName)

			if (user == null) {
				p0.sendMessage("§b$playerName §cnão existe ou está offline!")
				return true
			}
		}

		if (user == null) {
			p0.sendMessage("§cUsuário inválido!")
			return true
		}

		if (!user.allowFlight) {
			user.allowFlight = true
			user.isFlying = true
			p0.sendMessage("§aModo de vôo ativado para §b${user.name}§a, woosh!")
		} else {
			user.allowFlight = false
			p0.sendMessage("§aModo de vôo desativado para §b${user.name}")
		}
		return true
	}
}