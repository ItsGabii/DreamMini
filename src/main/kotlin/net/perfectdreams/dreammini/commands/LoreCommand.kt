package net.perfectdreams.dreammini.commands

import net.perfectdreams.dreamcore.utils.translateColorCodes
import net.perfectdreams.libs.acf.BaseCommand
import net.perfectdreams.libs.acf.annotation.CommandAlias
import net.perfectdreams.libs.acf.annotation.CommandPermission
import net.perfectdreams.libs.acf.annotation.Default
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("lore")
@CommandPermission("dreammini.lore")
class LoreCommand : BaseCommand() {
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
				val lore = name.split("\\n")
				meta.lore = lore
				item.itemMeta = meta

				user.sendMessage("§aAgora a descrição do item é...")
				for (entry in lore) {
					user.sendMessage("§5§o$entry")
				}
			} else {
				user.sendMessage("§aDescrição do item removida!")
			}
		} else {
			user.sendMessage("§cSegure um item na sua mão antes de usar!")
		}
		return true
	}
}