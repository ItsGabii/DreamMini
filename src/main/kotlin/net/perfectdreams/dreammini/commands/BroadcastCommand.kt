package net.perfectdreams.dreammini.commands

import net.perfectdreams.dreamcore.utils.commands.AbstractCommand
import net.perfectdreams.dreamcore.utils.commands.annotation.ArgumentType
import net.perfectdreams.dreamcore.utils.commands.annotation.InjectArgument
import net.perfectdreams.dreamcore.utils.commands.annotation.Subcommand
import net.perfectdreams.dreamcore.utils.generateCommandInfo
import net.perfectdreams.dreammini.DreamMini
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

class BroadcastCommand(val m: DreamMini) : AbstractCommand("broadcast", permission = "dreammini.broadcast") {
	@Subcommand
	fun root(sender: CommandSender) {
		sender.sendMessage(
				generateCommandInfo("broadcast")
		)
	}

	@Subcommand
	fun request(sender: CommandSender, @InjectArgument(ArgumentType.ARGUMENT_LIST) arguments: String) {
		Bukkit.broadcastMessage("§8[§c§lAnúncio§8] §a$arguments")
	}
}