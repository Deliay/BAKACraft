package bakacraft;

import bakacraft.WeaponSkills.Random;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class CraftLimit extends BAKAPlugin implements Listener {


    public final static String CRAFT = Random.Colorilize("&2制造");
    public final static String USE = Random.Colorilize("&3使用");
    public final static String BREAK = Random.Colorilize("&c破坏");

    public CraftLimit(JavaPlugin instance) {
        super(instance);
        getInstance().getServer().getPluginManager().registerEvents(this, instance);

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
    //制造限制插件
    //必须学习技能之后才有制造某件东西的权限
    //限制下列工具：
    //木、石、铁、金、钻石 稿、斧、剑
    //铲和锄不限制
    //工作台、熔炉、打火机、剪刀、箱子

    //合成除了原始合成之外，还需要旧的一件工具合成
    //正常合成铁镐之后，会有无法使用标志
    //需要使用一个石稿进行解锁

    //熔炉需要打火机解锁才能使用。（消耗1点打火机耐久）

    //工作台、打火机、剪刀、箱子无需前置解锁即可使用（）

    //挖掘限制：
    //木头可以手撸
    //要挖石头必须木镐及以上
    //要挖铁必须石镐及以上
    //依次类推

    //烧制相关矿石需要相关前置解锁


    public boolean breakable(Material mat) {
        switch (mat) {
            case GOLD_ORE:
            case IRON_ORE:
            case DIAMOND_ORE:
                return true;
            default:
                return false;
        }
    }

    public boolean craftable(Material mat) {
        switch (mat) {
            case WOOD_AXE:
            case GOLD_AXE:
            case IRON_AXE:
            case STONE_AXE:
            case DIAMOND_AXE:
            case WOOD_PICKAXE:
            case GOLD_PICKAXE:
            case IRON_PICKAXE:
            case STONE_PICKAXE:
            case DIAMOND_PICKAXE:
            case GOLD_SWORD:
            case STONE_SWORD:
            case IRON_SWORD:
            case WOOD_SWORD:
            case DIAMOND_SWORD:
            case WORKBENCH:
            case FURNACE:
            case SHEARS:
            case CHEST:
                return true;
            default:
                return false;
        }

    }

    public boolean usable(Material mat) {
        switch (mat) {
            case WOOD_AXE:
            case GOLD_AXE:
            case IRON_AXE:
            case STONE_AXE:
            case DIAMOND_AXE:
            case WOOD_PICKAXE:
            case GOLD_PICKAXE:
            case IRON_PICKAXE:
            case STONE_PICKAXE:
            case DIAMOND_PICKAXE:
            case GOLD_SWORD:
            case STONE_SWORD:
            case IRON_SWORD:
            case WOOD_SWORD:
            case DIAMOND_SWORD:
                return true;
            default:
                return false;
        }

    }

    @EventHandler
    public void onPlayerCraft(CraftItemEvent e) {

        if (!craftable(e.getRecipe().getResult().getType())) return;

        if (e.getWhoClicked() instanceof Player) {
            Player p = (Player) e.getWhoClicked();
            if (p.isOp()) return;
            if (!p.hasPermission(CRAFT + e.getCurrentItem().getItemMeta().getDisplayName())) {
                e.setCancelled(true);
            } else {
                List<String> lores = new ArrayList<>();
                lores.add(Random.Colorilize("&e需要权限:"));
                lores.add(USE + e.getCurrentItem().getItemMeta().getDisplayName());
                ItemMeta meta = e.getCurrentItem().getItemMeta();
                meta.setLore(lores);
                e.getCurrentItem().setItemMeta(meta);
            }
        }
    }

    @EventHandler
    public void onPlayerFurnaceOpen(InventoryOpenEvent e) {
        if (e.getPlayer().isOp()) return;
        if (e.getInventory().getType() == InventoryType.FURNACE) {
            if (!e.getPlayer().getItemInHand().getType().equals(Material.FLINT_AND_STEEL)) {
                ((Player) e.getPlayer()).sendRawMessage("你需要一个打火石来点燃熔炉");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getPlayer().isOp()) return;
        if (!usable(e.getPlayer().getItemInHand().getType())) return;
        if (e.getPlayer().hasPermission(CRAFT + e.getPlayer().getItemInHand().getItemMeta().getDisplayName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerBreak(BlockBreakEvent e) {
        if (e.getPlayer().isOp()) return;
        if (!breakable(e.getBlock().getType())) return;

        if (e.getPlayer().hasPermission(BREAK + e.getPlayer().getItemInHand().getItemMeta().getDisplayName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerSwitchHeld(PlayerItemHeldEvent e) {
        if (e.getPlayer().isOp()) return;
        ItemStack newItem = e.getPlayer().getInventory().getItem(e.getNewSlot());
        if (newItem == null || !usable(newItem.getType())) return;
        if (e.getPlayer().hasPermission(USE + newItem.getItemMeta().getDisplayName())) {
            e.getPlayer().sendRawMessage("你需要解锁之后才能使用这件装备");
            e.setCancelled(true);
        }
    }

}
