package net.perfectdreams.dreammini.commands

import net.perfectdreams.dreamcore.DreamCore
import net.perfectdreams.dreamcore.utils.commands.AbstractCommand
import net.perfectdreams.dreamcore.utils.commands.annotation.ArgumentType
import net.perfectdreams.dreamcore.utils.commands.annotation.InjectArgument
import net.perfectdreams.dreamcore.utils.commands.annotation.Subcommand
import net.perfectdreams.dreamcore.utils.commands.annotation.SubcommandPermission
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SpawnCommand : AbstractCommand("spawn") {
	@Subcommand
	fun spawn(player: Player) {
		player.teleport(DreamCore.dreamConfig.spawn)
	}

	@Subcommand
	@SubcommandPermission("dreammini.spawn.move")
	fun moveOther(sender: CommandSender, @InjectArgument(ArgumentType.PLAYER) player: Player?) {
		if (player == null) {
			sender.sendMessage("§cPlayer está offline!")
			return
		}

		player.teleport(DreamCore.dreamConfig.spawn)
	}
}