package net.perfectdreams.dreammini.commands

import net.perfectdreams.libs.acf.BaseCommand
import net.perfectdreams.libs.acf.annotation.CatchUnknown
import net.perfectdreams.libs.acf.annotation.CommandAlias
import net.perfectdreams.libs.acf.annotation.CommandPermission
import net.perfectdreams.libs.acf.annotation.Default
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("sudo")
@CommandPermission("dreammini.sudo")
class SudoCommand : BaseCommand() {
	@Default
	@CatchUnknown
	fun onCommand(p0: CommandSender, p3: Array<String>): Boolean {
		var user: Player? = null

		val playerName = p3.getOrNull(0)

		if (playerName != null) {
			user = Bukkit.getPlayer(playerName)

			if (user == null) {
				p0.sendMessage("§b$playerName §cnão existe ou está offline!")
				return true
			}
		}

		if (user == null) {
			p0.sendMessage("§e/sudo player comando")
			p0.sendMessage("§e/sudo player c:texto")
			return true
		}

		if (user.hasPermission("dreammini.unsudoable") && !p0.hasPermission("dreamini.overridesudo")) {
			p0.sendMessage("§cVocê não pode usar sudo em §b${user.name}§c!")
			return true
		}

		val list = p3.toMutableList()
		list.removeAt(0)
		val execute = list.joinToString(" ")

		if (execute.startsWith("c:")) {
			val chat = execute.replaceFirst("c:", "")
			user.chat(chat)
			p0.sendMessage("§b${user.name} §afoi forçado a enviar no chat §e$chat")
		} else {
			user.performCommand(execute)
			p0.sendMessage("§b${user.name} §afoi forçado a usar o comando §e/$execute")
		}
		return true
	}
}