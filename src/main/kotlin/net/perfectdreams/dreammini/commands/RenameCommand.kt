package net.perfectdreams.dreammini.commands

import net.perfectdreams.dreamcore.utils.translateColorCodes
import net.perfectdreams.libs.acf.BaseCommand
import net.perfectdreams.libs.acf.annotation.CommandAlias
import net.perfectdreams.libs.acf.annotation.CommandPermission
import net.perfectdreams.libs.acf.annotation.Default
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("rename|renomear")
@CommandPermission("dreammini.rename")
class RenameCommand : BaseCommand() {
	@Default
	fun onCommand(p0: CommandSender, p3: Array<String>): Boolean {
		val name = p3.joinToString(" ").translateColorCodes()

		var user: Player? = null

		if (p0 is Player) {
			user = p0
		}

		if (user == null) {
			p0.sendMessage("§cUsuário inválido!")
			return true
		}

		val item = user.inventory.itemInMainHand
		val type = item?.type
		if (type != Material.AIR) {
			val meta = item.itemMeta
			if (name.isNotEmpty()) {
				meta.displayName = name
				item.itemMeta = meta

				user.sendMessage("§aAgora o nome do item é §r${name}§a!")
			} else {
				meta.displayName = null
				item.itemMeta = meta

				user.sendMessage("§aNome do item removido!")
			}
		} else {
			user.sendMessage("§cSegure um item na sua mão antes de usar!")
		}
		return true
	}
}