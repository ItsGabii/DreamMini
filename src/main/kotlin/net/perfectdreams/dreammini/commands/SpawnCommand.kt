package net.perfectdreams.dreammini.commands

import net.perfectdreams.dreamcore.DreamCore
import net.perfectdreams.dreamcore.utils.commands.AbstractCommand
import net.perfectdreams.dreamcore.utils.commands.ExecutedCommandException
import net.perfectdreams.dreamcore.utils.commands.annotation.ArgumentType
import net.perfectdreams.dreamcore.utils.commands.annotation.InjectArgument
import net.perfectdreams.dreamcore.utils.commands.annotation.Subcommand
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SpawnCommand : AbstractCommand("spawn") {
	@Subcommand
	fun spawn(sender: CommandSender, @InjectArgument(ArgumentType.PLAYER) player: Player?) {
		val target = if (player != null && sender.hasPermission("dreammini.spawn.move")) {
			player
		} else if (sender is Player) {
			sender
		} else {
			throw ExecutedCommandException("§cPlayer está offline!")
		}
		target.teleport(DreamCore.dreamConfig.spawn)
	}
}