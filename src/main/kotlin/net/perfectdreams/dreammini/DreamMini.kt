package net.perfectdreams.dreammini

import com.okkero.skedule.CoroutineTask
import com.okkero.skedule.schedule
import net.perfectdreams.dreamcore.utils.*
import net.perfectdreams.dreamcore.utils.extensions.hasStoredMetadataWithKey
import net.perfectdreams.dreammini.commands.*
import net.perfectdreams.dreammini.utils.TpaManager
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.EntityType
import org.bukkit.entity.Firework
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.SignChangeEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class DreamMini : KotlinPlugin(), Listener {
	val joined = mutableSetOf<Player>()
	val left = mutableSetOf<Player>()
	var currentJoinSchedule: CoroutineTask? = null
	var currentLeftSchedule: CoroutineTask? = null
	var tpaManager = TpaManager()

	override fun softEnable() {
		super.softEnable()

		registerCommand(SkullCommand())
		registerCommand(FeedCommand())

		registerEvents(this)
		registerCommand(SignEditCommand())
		registerCommand(SpeedCommand())
		registerCommand(FlyCommand())
		registerCommand(GameModeCommand())
		registerCommand(HatCommand())
		registerCommand(HealCommand())
		registerCommand(TpAllCommand())
		registerCommand(TopCommand())
		registerCommand(SudoCommand())
		registerCommand(MemoryCommand())
		registerCommand(RenameCommand())
		registerCommand(LoreCommand())
		registerCommand(SpawnCommand())

		registerCommand(TpaCommand(this))
		registerCommand(TpaAceitarCommand(this))
		registerCommand(TpaNegarCommand(this))
		registerCommand(BroadcastCommand(this))
	}

	override fun softDisable() {
		super.softDisable()
	}

	@EventHandler
	fun onEdit(e: SignChangeEvent) {
		if (e.player.hasPermission("dreammini.colorize")) {
			for (idx in 0..3) {
				e.setLine(idx, e.getLine(idx).translateColorCodes())
			}
		}
	}

	@EventHandler
	fun onJoin(e: PlayerJoinEvent) {
		if (config.getBoolean("resource-pack.enabled", false)) {
			scheduler().schedule(this) {
				waitFor(100)
				e.player.setResourcePack(config.getString("resource-pack.link"), config.getString("resource-pack.hash"))
			}
		}

		if (e.player.hasPermission("dreammini.keepfly") && !e.player.isOnGround) { // Se o usuário deslogar enquanto está no ar, ative o fly
			scheduler().schedule(this) {
				waitFor(20)
				e.player.allowFlight = true
				e.player.isFlying = true
				e.player.sendMessage("§3Modo de vôo foi ativado automaticamente já que você não estava no chão ao sair, para desativar, use §6/fly")
			}
		}

		if (config.getBoolean("fancy-join", true)) {
			// Remover mensagem de entrada/saída
			e.joinMessage = null
			joined.add(e.player)
			left.remove(e.player)

			currentJoinSchedule?.cancel()

			val schedule = scheduler().schedule(this) {
				waitFor(20 * 7) // Esperar sete segundos
				val joinedPlayers = joined.filter { it.isOnline }

				if (joinedPlayers.isEmpty())
					return@schedule

				val aux = if (joinedPlayers.size == 1) {
					"entrou"
				} else {
					"entraram"
				}

				broadcast("§8[§a+§8] §b${joinedPlayers.joinToString("§a, §b", transform = { it.name })}§a $aux no jogo!")

				joined.clear()
			}

			currentJoinSchedule = schedule

			// Spawnar fireworks com cores aleatórias quando o player entrar no servidor
			val r = DreamUtils.random.nextInt(0, 256)
			val g = DreamUtils.random.nextInt(0, 256)
			val b = DreamUtils.random.nextInt(0, 256)

			val fadeR = Math.max(0, r - 60)
			val fadeG = Math.max(0, g - 60)
			val fadeB = Math.max(0, b - 60)

			val fireworkEffect = FireworkEffect.builder()
					.withTrail()
					.withColor(Color.fromRGB(r, g, b))
					.withFade(Color.fromRGB(fadeR, fadeG, fadeB))
					.with(FireworkEffect.Type.values()[DreamUtils.random.nextInt(0, FireworkEffect.Type.values().size)])
					.build()

			val firework = e.player.world.spawnEntity(e.player.location, EntityType.FIREWORK) as Firework
			val fireworkMeta = firework.fireworkMeta

			fireworkMeta.power = 1
			fireworkMeta.addEffect(fireworkEffect)

			firework.fireworkMeta = fireworkMeta
		}
	}

	@EventHandler
	fun onQuit(e: PlayerQuitEvent) {
		tpaManager.requests = tpaManager.requests.asSequence().filter { it.requestee != e.player || it.requester != e.player }.toMutableList()

		if (getConfig().getBoolean("fancy-quit", true)) {
			// Remover mensagem de entrada/saída
			e.quitMessage = null
			left.add(e.player)
			joined.remove(e.player)

			currentLeftSchedule?.cancel()

			val schedule = scheduler().schedule(this) {
				waitFor(20 * 7) // Esperar sete segundos
				val leftPlayers = left.filter { !it.isOnline }

				if (leftPlayers.isEmpty())
					return@schedule

				val aux = if (leftPlayers.size == 1) {
					"saiu"
				} else {
					"sairam"
				}

				broadcast("§8[§c-§8] §b${leftPlayers.joinToString("§c, §b", transform = { it.name })}§c $aux do jogo...")

				left.clear()
			}

			currentLeftSchedule = schedule
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	fun onItemDrop(e: PlayerDropItemEvent) {
		if (config.getBoolean("special-lucky-drop-item", true)) {
			val type = e.itemDrop?.itemStack?.type
			val player = e.player

			if (type == Material.GOLD_NUGGET || type == Material.GOLD_INGOT || type == Material.GOLD_BLOCK) {
				scheduler().schedule(this) {
					waitFor(1)
					for (idx in 0 until 10) {
						if (!e.itemDrop.isValid)
							return@schedule

						val location = e.itemDrop.location

						if (location.block.type == Material.WATER) {
							if (WorldGuardUtils.isWithinRegion(location, "loja-sorte")) {
								var rewarded = false
								for (amount in 0 until e.itemDrop.itemStack.amount) {
									var chance = DreamUtils.random.nextInt(0, 101)

									if (type == Material.GOLD_INGOT) {
										chance = Math.min(chance * 2, 100)
									}
									if (type == Material.GOLD_BLOCK) {
										chance = Math.min(chance * 4, 100)
									}

									// TODO: Prêmios
									if (chance == 100) {
										player.balance += 1750
										player.sendMessage("§aHoje é o seu dia de sorte!")
										rewarded = true
									}
								}
								if (rewarded) {
									e.itemDrop.location.world.spawnParticle(Particle.VILLAGER_HAPPY, e.itemDrop.location, 5, 0.5, 0.5, 0.5)
								} else {
									e.player.sendMessage("§cQue pena, pelo visto você não ganhou nada...")
									e.itemDrop.location.world.spawnParticle(Particle.VILLAGER_ANGRY, e.itemDrop.location, 5, 0.5, 0.5, 0.5)
								}
								e.itemDrop.remove()
								return@schedule
							}
						}
						waitFor(10)
					}
					return@schedule
				}
			}
		}
	}

	@EventHandler
	fun onCraft(e: CraftItemEvent) {
		for (item in e.inventory) {
			if (item != null && item.hasStoredMetadataWithKey("disallowCrafting"))
				e.isCancelled = true
		}
	}

	@EventHandler
	fun onDeath(e: PlayerDeathEvent) {
		if (config.getBoolean("disable-death-messages", true)) {
			e.deathMessage = null
		}
	}
}