package net.perfectdreams.dreammini.commands

import net.perfectdreams.libs.acf.BaseCommand
import net.perfectdreams.libs.acf.annotation.CommandAlias
import net.perfectdreams.libs.acf.annotation.CommandPermission
import net.perfectdreams.libs.acf.annotation.Default
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("fly|voar")
@CommandPermission("dreammini.fly")
class FlyCommand : BaseCommand() {
	@Default
	fun onCommand(p0: CommandSender, p3: Array<String>): Boolean {
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