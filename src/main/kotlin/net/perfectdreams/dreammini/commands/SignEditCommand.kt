package net.perfectdreams.dreammini.commands

import net.perfectdreams.dreamcore.utils.translateColorCodes
import net.perfectdreams.libs.acf.BaseCommand
import net.perfectdreams.libs.acf.annotation.CatchUnknown
import net.perfectdreams.libs.acf.annotation.CommandAlias
import net.perfectdreams.libs.acf.annotation.CommandPermission
import net.perfectdreams.libs.acf.annotation.Default
import org.bukkit.Material
import org.bukkit.block.Sign
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("signedit")
@CommandPermission("signedit.edit")
class SignEditCommand : BaseCommand() {
	@Default
	@CatchUnknown
	fun onCommand(p0: CommandSender, p3: Array<String>): Boolean {
		if (p0 is Player) {
			if (p3.isNotEmpty()) {
				val targetBlock = p0.getTargetBlock(null as Set<Material>?, 10)
				val type = targetBlock?.type

				val line = p3[0].toIntOrNull()

				if (line == null || (line) !in 1..4) {
					p0.sendMessage("§cVocê não colocou uma linha válida! Linhas devem ser entre 1 e 4!")
					return true
				}

				if (type == null || !type.name.contains("SIGN")) {
					p0.sendMessage("§cVocê precisa estar olhando para uma placa!")
					return true
				}

				val sign = targetBlock.state as Sign

				val lines = p3.toMutableList()
				lines.removeAt(0)

				sign.setLine(line - 1, lines.joinToString(" ").translateColorCodes())
				sign.update()

				p0.sendMessage("§aLinha $line atualizada!")
			} else {
				p0.sendMessage("§e/signedit linha texto")
			}
		} else {
			p0.sendMessage("Apenas players!")
		}
		return true
	}
}