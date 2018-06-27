package net.perfectdreams.dreammini.commands

import net.perfectdreams.dreamcore.utils.commands.AbstractCommand
import net.perfectdreams.dreamcore.utils.withoutPermission
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GameModeCommand : AbstractCommand("gamemode", listOf("gm")) {
	override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<String>): Boolean {
		if (!p0.hasPermission("dreammini.fly")) {
			p0.sendMessage(withoutPermission)
			return true
		}

		var user: Player? = null

		if (p0 is Player) {
			user = p0
		}

		val gameModeArg = p3.getOrNull(0)

		if (gameModeArg == null) {
			p0.sendMessage("§e/gamemode 0/1/2/3/survival/creative/adventure/spectator")
			return true
		}

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

		var gameMode: GameMode? = null
		val gmNumber = gameModeArg.toIntOrNull()

		if (gmNumber != null && gmNumber in 0 until GameMode.values().size) {
			gameMode = GameMode.getByValue(gmNumber)
		} else {
			try {
				gameMode = GameMode.valueOf(gameModeArg.toUpperCase())
			} catch (e: Exception) {}
		}

		if (gameMode == null) {
			p0.sendMessage("§9${gameModeArg}§c não é um modo de jogo válido!")
		} else {
			user.gameMode = gameMode
			p0.sendMessage("§aModo de jogo de §b${user.name}§a foi alterado para §9${gameMode.name}§a!")
		}
		return true
	}
}