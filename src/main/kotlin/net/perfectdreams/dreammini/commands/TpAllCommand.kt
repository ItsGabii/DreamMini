package net.perfectdreams.dreammini.commands

import net.perfectdreams.libs.acf.BaseCommand
import net.perfectdreams.libs.acf.annotation.CatchUnknown
import net.perfectdreams.libs.acf.annotation.CommandAlias
import net.perfectdreams.libs.acf.annotation.CommandPermission
import net.perfectdreams.libs.acf.annotation.Default
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("tpall")
@CommandPermission("dreammini.tpall")
class TpAllCommand : BaseCommand() {
	@Default
	@CatchUnknown
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

		val _user = user
		Bukkit.getOnlinePlayers().forEach {
			it.teleport(_user.location)
		}

		p0.sendMessage("§aTodos os players vieram até §b${user.name}§a!")
		return true
	}
}