package hazae41.wakeup

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.EnumWrappers.Hand
import com.comphenix.protocol.wrappers.EnumWrappers.getHandConverter
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerLocaleChangeEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BookMeta
import org.bukkit.inventory.meta.BookMeta.Generation.ORIGINAL
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin(), Listener {
  val protocol get() = ProtocolLibrary.getProtocolManager()

  override fun onEnable() {
    super.onEnable()

    server.pluginManager.registerEvents(this, this)
  }

  fun Player.wakeUp(locale: String? = null) {
    val lang = getLang(locale ?: getLocale())
    val book = ItemStack(Material.WRITTEN_BOOK)
    val meta = book.itemMeta as? BookMeta ?: return

    meta.title = lang.title()
    meta.author = name
    meta.generation = ORIGINAL
    meta.addPage(*lang.book())

    book.itemMeta = meta

    val previous = inventory.itemInMainHand
    inventory.setItemInMainHand(book)
    inventory.addItem(previous)

    val packet = PacketContainer(PacketType.Play.Server.OPEN_BOOK)
    val hand = getHandConverter().getGeneric(Hand.MAIN_HAND)
    packet.modifier.write(0, hand)
    protocol.sendServerPacket(this, packet)
  }

  @EventHandler(priority = EventPriority.MONITOR)
  fun onjoin(e: PlayerJoinEvent) {
    if (e.player.hasPlayedBefore()) return

    server.pluginManager.registerEvents(object : Listener {
      @EventHandler(priority = EventPriority.MONITOR)
      fun onjoin(e: PlayerLocaleChangeEvent) {
        HandlerList.unregisterAll(this)
        e.player.wakeUp(e.locale)
      }
    }, this)
  }

  @EventHandler(priority = EventPriority.MONITOR)
  fun onrespawn(e: PlayerRespawnEvent) {
    if (e.isBedSpawn || e.isAnchorSpawn) return

    server.scheduler.runTaskLater(this, { _ ->
      e.player.wakeUp()
    }, 20)
  }
}