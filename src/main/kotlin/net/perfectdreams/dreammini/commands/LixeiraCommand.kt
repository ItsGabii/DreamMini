package net.perfectdreams.dreammini.commands

import net.perfectdreams.dreamcore.utils.commands.AbstractCommand
import net.perfectdreams.dreamcore.utils.commands.annotation.Subcommand
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class LixeiraCommand : AbstractCommand("lixeira", permission = "dreammini.lixeira", aliases = listOf("garbage", "lixo")) {

    @Subcommand
    fun lixeira(sender: Player) {
        val inventory = Bukkit.createInventory(null, 54, "§4§lLixeira")

        sender.openInventory(inventory)
    }
}