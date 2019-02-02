package net.perfectdreams.dreammini.commands

import net.perfectdreams.commands.annotation.Subcommand
import net.perfectdreams.commands.bukkit.SparklyCommand
import net.perfectdreams.dreamcore.utils.generateCommandInfo
import net.perfectdreams.dreamcore.utils.translateColorCodes
import net.perfectdreams.dreammini.DreamMini
import org.bukkit.Material
import org.bukkit.entity.Player

class RenameCommand(val m: DreamMini) : SparklyCommand(arrayOf("rename", "renomear")){

	@Subcommand
	fun root(sender: Player){
		sender.sendMessage(generateCommandInfo("rename <newname>"))
	}

	@Subcommand
	fun rename(sender: Player, itemName: String){
		val name = itemName.translateColorCodes()

		val item = sender.inventory.itemInMainHand
		val type = item?.type

		if(type != Material.AIR){
			val meta = item.itemMeta

			meta.displayName = name
			item.itemMeta = meta

			sender.sendMessage("§aAgora o nome do item é §r${name}§a!")
		}else {
			sender.sendMessage("§cSegure um item na sua mão antes de usar!")
		}
	}

	@Subcommand(["resetname"])
	fun reset(sender: Player) {
		val item = sender.inventory.itemInMainHand
		val type = item?.type

		if (type != Material.AIR) {
			val meta = item.itemMeta

			meta.displayName = null
			item.itemMeta = meta

			sender.sendMessage("§aNome do item removido!")
		}
	}
}