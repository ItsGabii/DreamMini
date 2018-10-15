package net.perfectdreams.dreammini.commands

import net.perfectdreams.dreamcore.utils.blacklistedTeleport
import net.perfectdreams.dreamcore.utils.commands.AbstractCommand
import net.perfectdreams.dreamcore.utils.commands.annotation.Subcommand
import net.perfectdreams.dreammini.DreamMini
import org.bukkit.entity.Player

class TpaAceitarCommand(val m: DreamMini) : AbstractCommand("tpaceitar", listOf("tpaaceitar", "tpaccept", "tpaccept"), "dreammini.tpaceitar") {
	@Subcommand
	fun root(sender: Player) {
		val tpaRequest = m.tpaManager.requests.firstOrNull { it.requestee == sender }

		if (tpaRequest == null) {
			sender.sendMessage("§cVocê não tem nenhum pedido de teletransporte pendente!")
			return
		}

		val requester = tpaRequest.requester

		if (sender.location.blacklistedTeleport) {
			sender.sendMessage("§cNossos sistemas de localização não permitem que você deixe §b${requester.displayName}§c teletransporte para aonde você está!")
			return
		}

		requester.teleport(sender)
		requester.sendMessage("§b${sender.displayName}§a aceitou o seu pedido de teletransporte!")
		sender.sendMessage("§aVocê aceitou o pedido de teletransporte de §b${requester.displayName}§a!")

		m.tpaManager.requests.remove(tpaRequest)
	}
}