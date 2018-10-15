package net.perfectdreams.dreammini.commands

import net.perfectdreams.dreamcore.utils.commands.AbstractCommand
import net.perfectdreams.dreamcore.utils.commands.annotation.Subcommand
import net.perfectdreams.dreammini.DreamMini
import org.bukkit.entity.Player

class TpaNegarCommand(val m: DreamMini) : AbstractCommand("tpaceitar", listOf("tpaaceitar", "tpaccept", "tpaccept"), "dreammini.tpaceitar") {
	@Subcommand
	fun root(sender: Player) {
		val tpaRequest = m.tpaManager.requests.firstOrNull { it.requestee == sender }

		if (tpaRequest == null) {
			sender.sendMessage("§cVocê não tem nenhum pedido de teletransporte pendente!")
			return
		}

		val requester = tpaRequest.requester
		sender.sendMessage("§aVocê rejeitou o pedido de teletransporte de §b${requester.displayName}§a!")
		requester.sendMessage("§b${sender.displayName}§c rejeitou o seu pedido de teletransporte!")
		return
	}
}