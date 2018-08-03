package net.perfectdreams.dreammini.commands

import net.perfectdreams.libs.acf.BaseCommand
import net.perfectdreams.libs.acf.annotation.CatchUnknown
import net.perfectdreams.libs.acf.annotation.CommandAlias
import net.perfectdreams.libs.acf.annotation.CommandPermission
import net.perfectdreams.libs.acf.annotation.Default
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

@CommandAlias("skull")
@CommandPermission("dreammini.skull")
class SkullCommand : BaseCommand() {
	@Default
	@CatchUnknown
	fun onCommand(p0: CommandSender, p3: Array<String>): Boolean {
		val owner = p3.getOrNull(0)

		if (owner == null) {
			p0.sendMessage("§e/skull player")
			return true
		}

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
		if (type == Material.PLAYER_HEAD && item.durability == 3.toShort()) {
			// É necessário "clonar" o item, se não for "clonado", não será possível usar "meta.owner" caso a skull já tenha
			// um owner anterior
			val skull = ItemStack(Material.PLAYER_HEAD, 1)
			skull.amount = item.amount
			skull.addEnchantments(item.enchantments)
			val meta = item.itemMeta as SkullMeta
			val skullMeta = skull.itemMeta as SkullMeta

			skullMeta.addItemFlags(*meta.itemFlags.toTypedArray())
			skullMeta.displayName = meta.displayName
			skullMeta.lore = meta.lore
			skullMeta.owner = owner

			skull.itemMeta = skullMeta
			user.inventory.itemInMainHand = skull

			user.sendMessage("§aAgora o dono da cabeça do player atual é §b${skullMeta.owner}§a!")
		} else {
			user.sendMessage("§cSegure uma cabeça de um player na sua mão antes de usar!")
		}
		return true
	}
}