package net.perfectdreams.dreammini.commands

import net.perfectdreams.commands.bukkit.SparklyCommand
import net.perfectdreams.dreamcore.utils.LocationUtils
import net.perfectdreams.dreamcore.utils.blacklistedTeleport
import net.perfectdreams.commands.annotation.Subcommand
import net.perfectdreams.dreamcore.utils.extensions.getSafeDestination
import net.perfectdreams.dreammini.DreamMini
import org.bukkit.entity.Player

class TpaAceitarCommand(val m: DreamMini) : SparklyCommand(arrayOf("tpaceitar", "tpaccept", "tpaccept"), permission = "dreammini.tpaceitar") {

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

		val location = try { sender.location.getSafeDestination() } catch (e: LocationUtils.HoleInFloorException) {
			sender.sendMessage("§cVocê não está em um local seguro para aceitar o pedido!")
			return
		}

		requester.teleport(location)
		requester.sendMessage("§b${sender.displayName}§a aceitou o seu pedido de teletransporte!")
		sender.sendMessage("§aVocê aceitou o pedido de teletransporte de §b${requester.displayName}§a!")

		m.tpaManager.requests.remove(tpaRequest)
	}
}