package bakacraft;

import bakacraft.WeaponSkills.Random;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DeadLimiter extends BAKAPlugin implements Listener {
    public final String DEAD_AXE_NAME = Random.Colorilize("&e死亡之木斧 (5 分钟后可丢弃)");
    public final Map<Player, List<ItemStack>> backups = new HashMap<>();

    public DeadLimiter(JavaPlugin instance) {
        super(instance);
        instance.getServer().getPluginManager().registerEvents(this, instance);

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventHandler
    public void onPlayerDead(PlayerDeathEvent event) {
        ItemStack DEAD_AXE = new ItemStack(Material.WOOD_AXE, 1);
        ItemMeta DEAD_AXE_META = DEAD_AXE.getItemMeta();

        Player deader = event.getEntity();
        event.setKeepInventory(true);
        List<ItemStack> drops = event.getDrops();

        DEAD_AXE_META.setDisplayName(DEAD_AXE_NAME);
        List<String> lores = new ArrayList<>();
        lores.add(Random.Colorilize("&c死亡时间:"));
        lores.add(LocalDateTime.now().toString());
        lores.add(Random.Colorilize("&3无法丢弃"));
        lores.add(Random.Colorilize("死亡惩罚."));
        DEAD_AXE_META.setLore(lores);

        DEAD_AXE.setItemMeta(DEAD_AXE_META);

        drops.add(new ItemStack(DEAD_AXE));
        backups.put(event.getEntity(), drops);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (backups.containsKey(event.getPlayer())) {
            for (ItemStack item : backups.get(event.getPlayer())) {
                event.getPlayer().getInventory().addItem(item);
            }
            backups.remove(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event) {
        ItemMeta meta = event.getItemDrop().getItemStack().getItemMeta();
        if (DEAD_AXE_NAME.equals(meta.getDisplayName())) {
            List<String> lores = meta.getLore();
            LocalDateTime dateTime = LocalDateTime.parse(lores.get(1));
            if (dateTime.plusMinutes(5).isAfter(LocalDateTime.now())) {
                //时间未满，不能丢弃
                event.getPlayer().sendRawMessage("时间未满，无法丢弃惩罚物品");
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerUseItem(PlayerInteractEvent event) {
        if (event.hasItem()) {
            if (DEAD_AXE_NAME.equals(event.getItem().getItemMeta().getDisplayName())) {
                //禁止使用
                event.getPlayer().sendRawMessage("无法使用惩罚物品");
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerFireItem(org.bukkit.event.inventory.InventoryDragEvent event) {
        if (event.getCursor() != null && DEAD_AXE_NAME.equals(event.getCursor().getItemMeta().getDisplayName())) {
            //禁止点击（放进熔炉）
            ((Player) event.getWhoClicked()).sendRawMessage("无法烧制惩罚物品");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerSwitchHeld(PlayerItemHeldEvent e) {
        ItemStack newItem = e.getPlayer().getInventory().getItem(e.getNewSlot());
        if (newItem != null && DEAD_AXE_NAME.equals(newItem.getItemMeta().getDisplayName())) {
            e.getPlayer().sendRawMessage("无法切换到惩罚物品");
            e.setCancelled(true);
        }
    }
}
