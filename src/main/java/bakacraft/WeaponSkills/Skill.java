package bakacraft.WeaponSkills;

import bakacraft.BAKAPlugin;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import pluginMain.BAKAScoreboard;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by admin on 2017/7/7.
 */
public class Skill extends BAKAPlugin implements Listener {
    public final static ItemStack SOUL_ASH = new ItemStack(Material.FLINT, 1);
    public final static ItemStack SOUL_STONE = new ItemStack(Material.EMERALD, 1);

    private Set<Player> status = new HashSet<>();

    public Axe axe = new Axe();
    public Sword sword = new Sword();
    public Bow bow = new Bow();
    private ShapelessRecipe recipe_axe, recipe_sword, recipe_bow;

    public Skill(JavaPlugin instance) {
        super(instance);
        ItemMeta meta = null;
        meta = SOUL_ASH.getItemMeta();
        meta.setDisplayName(Random.Colorilize("灵魂之烬"));
        SOUL_ASH.setItemMeta(meta);

        meta = SOUL_STONE.getItemMeta();
        meta.setDisplayName(Random.Colorilize("&c灵魂之石"));
        SOUL_STONE.setItemMeta(meta);

        instance.getServer().getPluginManager().registerEvents(this, instance);

        ShapedRecipe recipe_ash = new ShapedRecipe(SOUL_ASH);
        recipe_ash.shape("AAA", "BCD", "AAA");
        recipe_ash.setIngredient('A', Material.SOUL_SAND);
        recipe_ash.setIngredient('B', Material.ROTTEN_FLESH);
        recipe_ash.setIngredient('C', Material.SPIDER_EYE);
        recipe_ash.setIngredient('D', Material.BONE);

        ShapedRecipe recipe_stone = new ShapedRecipe(SOUL_STONE);
        recipe_stone.shape("AAA", "BCD", "AAA");
        recipe_stone.setIngredient('A', Material.STONE);
        recipe_stone.setIngredient('B', Material.ROTTEN_FLESH);
        recipe_stone.setIngredient('C', Material.FLINT);
        recipe_stone.setIngredient('D', Material.BONE);

        recipe_axe = new ShapelessRecipe(new ItemStack(Material.DIAMOND_AXE));
        recipe_axe.addIngredient(Material.EMERALD);
        recipe_axe.addIngredient(Material.DIAMOND_AXE);

        recipe_sword = new ShapelessRecipe(new ItemStack(Material.DIAMOND_SWORD));
        recipe_sword.addIngredient(Material.EMERALD);
        recipe_sword.addIngredient(Material.DIAMOND_SWORD);

        recipe_bow = new ShapelessRecipe(new ItemStack(Material.BOW));
        recipe_bow.addIngredient(Material.EMERALD);
        recipe_bow.addIngredient(Material.BOW);
        recipe_bow.addIngredient(Material.DIAMOND);
        recipe_bow.addIngredient(Material.DIAMOND);

        instance.getServer().addRecipe(recipe_ash);
        instance.getServer().addRecipe(recipe_stone);
        instance.getServer().addRecipe(recipe_axe);
        instance.getServer().addRecipe(recipe_sword);
        instance.getServer().addRecipe(recipe_bow);

        instance.getServer().getPluginManager().registerEvents(axe, instance);
        instance.getServer().getPluginManager().registerEvents(sword, instance);
        instance.getServer().getPluginManager().registerEvents(bow, instance);


    }
/*
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.hasItem() && event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType().equals(Material.SOUL_SAND)) {
            if (status.contains(event.getPlayer())) {
                ItemStack selected = event.getItem();
                if (selected.getEnchantments().size() > 0) {
                    event.getPlayer().sendRawMessage("无法对已经附魔的道具启灵");
                    return;
                }
                ItemStack newItem = null;
                Material mat = selected.getType();
                switch (mat) {
                    case DIAMOND_AXE:
                        newItem = AxeEffects.UnlockLore(selected);
                        break;
                    case DIAMOND_SWORD:
                        newItem = sword.UnlockLore(selected);
                        break;
                    case BOW:
                        newItem = bow.UnlockLore(selected);
                        break;
                    default:
                        event.getPlayer().sendRawMessage("无法启灵此类物品，请再次选择");
                        return;
                }
                newItem.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
                event.getPlayer().getInventory().addItem(newItem);
                event.getPlayer().getInventory().remove(selected);
                event.getPlayer().sendRawMessage("启灵完成");
                status.remove(event.getPlayer());
                return;
            }

            ItemStack stack = event.getItem();
            if (SOUL_STONE.getItemMeta().getDisplayName().equals(stack.getItemMeta().getDisplayName())) {
                event.getPlayer().sendRawMessage("你选择了启灵石，下面请选择一件装备");
                if (stack.getAmount() > 1) {
                    stack.setAmount(stack.getAmount() - 1);
                } else {
                    event.getPlayer().getInventory().remove(stack);
                }
                status.add(event.getPlayer());
            }

        }
    }
*/
    @EventHandler
    public void onPlayerCraft(CraftItemEvent event) {
        if (SOUL_STONE.getItemMeta().getDisplayName().equals(event.getCurrentItem().getItemMeta().getDisplayName())) {
            for (ItemStack item : event.getInventory()) {
                if (SOUL_ASH.getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) {
                    return;
                }
            }

            event.setCancelled(true);
        } else {
            Recipe rec = event.getRecipe();
            if (rec.getResult().equals(recipe_axe.getResult()) || rec.getResult().equals(recipe_bow.getResult()) || rec.getResult().equals(recipe_sword.getResult())) {
                if (event.getInventory().contains(SOUL_STONE)) {
                    ItemStack selected = event.getCurrentItem();
                    Player p = (Player) event.getWhoClicked();
                    ItemStack newItem = null;
                    Material mat = selected.getType();
                    String type = "";
                    switch (mat) {
                        case DIAMOND_AXE:
                            newItem = axe.UnlockLore(selected);
                            type = "斧";
                            break;
                        case DIAMOND_SWORD:
                            newItem = sword.UnlockLore(selected);
                            type = "剑";
                            break;
                        case BOW:
                            newItem = bow.UnlockLore(selected);
                            newItem.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
                            type = "弓";
                            break;
                    }
                    ItemMeta meta = newItem.getItemMeta();
                    List<String> lore = meta.getLore();
                    lore.add(Random.Colorilize("&6[武器类型] " + type));
                    meta.setLore(lore);
                    newItem.setItemMeta(meta);
                    newItem.addUnsafeEnchantment(Enchantment.DURABILITY, 100);
                    event.setCurrentItem(newItem);
                    return;

                } else {

                }
            }
        }
    }
}
