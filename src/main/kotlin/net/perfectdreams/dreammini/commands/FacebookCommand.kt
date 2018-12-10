package net.perfectdreams.dreammini.commands

import net.perfectdreams.dreamcore.utils.commands.AbstractCommand
import net.perfectdreams.dreamcore.utils.commands.annotation.Subcommand
import net.perfectdreams.dreammini.DreamMini
import org.bukkit.command.CommandSender

class FacebookCommand(val m: DreamMini) : AbstractCommand("facebook") {
	@Subcommand
	fun root(sender: CommandSender) {
		sender.sendMessage("§aCurta a nossa página no Facebook!§9 https://facebook.com/SparklyPower")
	}
}