package net.perfectdreams.dreammini.commands

import net.perfectdreams.dreamcore.utils.commands.AbstractCommand
import net.perfectdreams.dreamcore.utils.withoutPermission
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SpeedCommand : AbstractCommand("speed") {
	override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<String>): Boolean {
		if (!p0.hasPermission("dreammini.speed")) {
			p0.sendMessage(withoutPermission)
			return true
		}

		var user: Player? = null

		if (p0 is Player) {
			user = p0
		}

		val userSpeed = p3.getOrNull(0)?.toFloatOrNull()
		val playerName = p3.getOrNull(1)

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
		if (userSpeed != null) {
			val speed = userSpeed / 5
			if (speed !in 0.0..1.0) {
				p0.sendMessage("§cVelocidade precisa estar entre 0 e 5!")
				return true
			}

			var type = p3.getOrNull(2)

			if (type == null) {
				type = if (user.isOnGround) {
					"walk"
				} else {
					"fly"
				}
			}

			if (type == "walk") {
				user.walkSpeed = speed
				p0.sendMessage("§aVelocidade no chão de §b${user.name}§a foi alterada para §9${userSpeed}§a!")
			} else {
				user.flySpeed = speed
				p0.sendMessage("§aVelocidade de vôo de §b${user.name}§a foi alterada para §9${userSpeed}§a!")
			}
			return true
		}

		p0.sendMessage("§e/speed velocidade player walk/fly")
		return true
	}
}