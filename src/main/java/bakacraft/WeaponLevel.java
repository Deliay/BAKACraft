package bakacraft;

import bakacraft.EnemySpawn.EnemyMetadata;
import bakacraft.WeaponSkills.Bow;
import bakacraft.WeaponSkills.Random;
import bakacraft.WeaponSkills.Skill;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class WeaponLevel extends BAKAPlugin implements Listener {

    public class Grown
    {
        public String name;
        public Integer type;
        public Double value;
        public Double initial;

        public Grown(String n, Integer t, Double v, Double i)
        {
            name = n;type = t;value = v;initial = i;
        }

        public final double getDamage(int level)
        {
            //exp(x/55)*2.2*7.5-8
            return initial + Math.exp(level / 55) * value * 7.5 - 8 + level / 4;
            //return initial + Math.log(level) * value;
        }
    }

    public class GrownTable
    {
        public List<Grown> growns = new ArrayList<>();

        public void put(String name, Integer type, double value, double init)
        {
            growns.add(new Grown(name, type, value, init));
        }

        public Grown get(String name, Integer type)
        {
            for (Grown g : growns)
            {
                if(g.name.equals(name) && g.type.equals(type)) return g;
            }
            return null;
        }

        public Grown get_match(String name, Integer type)
        {
            for (Grown g : growns)
            {
                if(name.contains(g.name) && type.equals(g.type)) return g;
            }
            return null;
        }

        public Grown get(ItemStack item)
        {
            int rank;
            String name = item.getItemMeta().getDisplayName();


            if(name.endsWith("SSR")) rank = 3;
            else if(name.endsWith("SR")) rank = 2;
            else if(name.endsWith("R")) rank = 1;
            else rank = 0;

            return get_match(name, rank);
        }
    }

    public final static ItemStack LEVEL_STONE = new ItemStack(Material.EMERALD, 1);

    public final static HashSet<Player> status = new HashSet<>();

    public final static ExpTable EXP_TABLE = new ExpTable();

    public final GrownTable GROWN_TABLE = new GrownTable();

    public WeaponLevel(JavaPlugin instance) {
        super(instance);

        //等级石需要2个启灵石合成，中间留空
        ItemMeta meta = LEVEL_STONE.getItemMeta();

        List<String> lores = new ArrayList<>();
        meta.setDisplayName(Random.Colorilize("&9灵智石"));
        lores.add("右键灵智石，再右键选择需要附加灵智的武器");
        meta.setLore(lores);
        LEVEL_STONE.setItemMeta(meta);
        ShapelessRecipe recipe_level = new ShapelessRecipe(LEVEL_STONE);
        recipe_level.addIngredient(Material.EMERALD);
        recipe_level.addIngredient(Material.EMERALD);

        getInstance().getServer().addRecipe(recipe_level);

        getInstance().getServer().getPluginManager().registerEvents(this, getInstance());

        {

            EXP_TABLE.putExp(EntityType.ZOMBIE, 4);
            EXP_TABLE.putExp(EntityType.SKELETON, 5);
            EXP_TABLE.putExp(EntityType.SPIDER, 4);
            EXP_TABLE.putExp(EntityType.ENDERMAN, 5);
            EXP_TABLE.putExp(EntityType.PIG_ZOMBIE, 4);
            EXP_TABLE.putExp(EntityType.CAVE_SPIDER, 5);
            EXP_TABLE.putExp(EntityType.CREEPER, 4);

            GROWN_TABLE.put("开山之斧", 0, 1.8, 7);
            GROWN_TABLE.put("开山之斧", 1, 1.8125, 7);
            GROWN_TABLE.put("开山之斧", 2, 1.9, 8);
            GROWN_TABLE.put("开山之斧", 3, 2.0, 8);
            GROWN_TABLE.put("魔龙战斧", 0, 1.8, 7);
            GROWN_TABLE.put("魔龙战斧", 1, 1.8125, 7);
            GROWN_TABLE.put("魔龙战斧", 2, 1.9, 8);
            GROWN_TABLE.put("魔龙战斧", 3, 2.0, 8);
            GROWN_TABLE.put("无上战魁之斧", 2, 2.1125, 8);
            GROWN_TABLE.put("无上战魁之斧", 3, 2.2, 9);
            GROWN_TABLE.put("红雾战斧", 0, 1.8, 6);
            GROWN_TABLE.put("红雾战斧", 1, 1.8125, 6);
            GROWN_TABLE.put("红雾战斧", 2, 1.9, 7);
            GROWN_TABLE.put("红雾战斧", 3, 2.0, 7);

            GROWN_TABLE.put("剑道雅客长剑", 0, 1.5, 5);
            GROWN_TABLE.put("剑道雅客长剑", 1, 1.65, 5);
            GROWN_TABLE.put("剑道雅客长剑", 2, 1.675, 6);
            GROWN_TABLE.put("剑道雅客长剑", 3, 1.7, 6);
            GROWN_TABLE.put("古卷之剑坎图沙", 0, 0.95, 2);
            GROWN_TABLE.put("古卷之剑坎图沙", 1, 0.95, 2);
            GROWN_TABLE.put("古卷之剑坎图沙", 2, 0.975, 3);
            GROWN_TABLE.put("古卷之剑坎图沙", 3, 1.025, 3);
            GROWN_TABLE.put("无穷神力之刃", 2, 1.95, 6);
            GROWN_TABLE.put("无穷神力之刃", 3, 1.975, 7);
            GROWN_TABLE.put("炎铸大太刀", 0, 1.45, 4);
            GROWN_TABLE.put("炎铸大太刀", 1, 1.55, 4);
            GROWN_TABLE.put("炎铸大太刀", 2, 1.675, 5);
            GROWN_TABLE.put("炎铸大太刀", 3, 1.7, 5);

            GROWN_TABLE.put("霜之獠牙长弓", 0, 1.715, 3);
            GROWN_TABLE.put("霜之獠牙长弓", 1, 1.725, 3);
            GROWN_TABLE.put("霜之獠牙长弓", 2, 1.735, 4);
            GROWN_TABLE.put("霜之獠牙长弓", 3, 1.735, 4);
            GROWN_TABLE.put("呼啸狂风之弓", 0, 1.715, 5);
            GROWN_TABLE.put("呼啸狂风之弓", 1, 1.725, 5);
            GROWN_TABLE.put("呼啸狂风之弓", 2, 1.735, 6);
            GROWN_TABLE.put("呼啸狂风之弓", 3, 1.735, 6);
            GROWN_TABLE.put("霸王蝶", 2, 2.0125, 6);
            GROWN_TABLE.put("霸王蝶", 3, 2.1, 7);
            GROWN_TABLE.put("倾城之弓", 0, 1.715, 5);
            GROWN_TABLE.put("倾城之弓", 1, 1.725, 5);
            GROWN_TABLE.put("倾城之弓", 2, 1.735, 6);
            GROWN_TABLE.put("倾城之弓", 3, 1.735, 6);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event)
    {
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR))
        {
            ItemStack stack = event.getItem();

            if(status.contains(event.getPlayer()))
            {
                if (LEVEL_STONE.getItemMeta().getDisplayName().equals(stack.getItemMeta().getDisplayName()))
                {
                    event.getPlayer().sendRawMessage("不能给灵智石开启灵智");
                }
                else
                {
                    ItemMeta meta = event.getItem().getItemMeta();
                    List<String> lore = (meta.hasLore() ? meta.getLore() : new ArrayList<String>());

                    if(hasType(lore)) identifyType(event.getItem());
                    if(!hasType(lore))
                    {
                        event.getPlayer().sendRawMessage("无法给这个武器启灵智！请再次选择。");
                        return;
                    }


                    if(checkLore(lore))
                    {
                        event.getPlayer().sendRawMessage("这件武器已经开启过灵智了。");
                        return;
                    }
                    lore.add(Random.Colorilize("&2-----------------"));
                    lore.add(Random.Colorilize("&2[等级]"));
                    lore.add("1");
                    lore.add(Random.Colorilize("&2[经验]"));
                    lore.add("0/" + getNextLevelExp(0));
                    lore.add(Random.Colorilize("&2-----------------"));

                    meta.setLore(lore);

                    event.getItem().setItemMeta(meta);
                    status.remove(event.getPlayer());
                }

            }

            if (LEVEL_STONE.getItemMeta().getDisplayName().equals(stack.getItemMeta().getDisplayName())) {
                event.getPlayer().sendRawMessage("你选择了灵智石，请选择需要附加灵智的武器");
                if (stack.getAmount() > 1) {
                    stack.setAmount(stack.getAmount() - 1);
                } else {
                    event.getPlayer().getInventory().remove(stack);
                }
                status.add(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent event)
    {
        ItemStack result = event.getRecipe().getResult();
        if(!result.getType().equals(Material.EMERALD)) return;
        if(!result.hasItemMeta()) return;
        if(!result.getItemMeta().getDisplayName().equals(LEVEL_STONE.getItemMeta().getDisplayName())) return;;

        for (ItemStack item : event.getInventory().getMatrix())
        {
            if(item != null && item.getType() != Material.AIR && item.hasItemMeta() && !item.getItemMeta().getDisplayName().equals(Skill.SOUL_STONE.getItemMeta().getDisplayName()))
            {
                event.setCancelled(true);
                return;
            }
        }

        event.setCurrentItem(LEVEL_STONE);
    }


    public final static boolean checkLore(List<String> lore)
    {
        if(lore == null) return false;
        for (String line : lore)
        {
            if(line.matches(".*\\[等级].*"))
            {
                return true;
            }
        }
        return false;
    }

    public final static int getLevel(List<String> lore)
    {
        for (int i = 0; i < lore.size(); i++) {
            if(lore.get(i).matches(".*\\[等级].*")){
                return Integer.valueOf(lore.get(i+1));
            }
        }
        return 1;
    }

    public final static int getExp(List<String> lore)
    {
        for (int i = 0; i < lore.size(); i++) {
            if(lore.get(i).matches(".*\\[经验].*")){
                String l = lore.get(i+1);
                return Integer.valueOf(l.substring(0, l.contains("/")  ? l.indexOf('/') : l.length()));
            }
        }
        return 1;
    }

    public  final static List<String> setLevel(List<String> lore, int level)
    {
        List<String> ll = lore;
        for (int i = 0; i < ll.size(); i++) {
            if(ll.get(i).matches(".*\\[等级].*")){
                ll.set(i + 1, "" + level);
                break;
            }
        }
        return ll;
    }

    public  final static int getNextLevelExp(int level)
    {
        return (int)Math.pow(level + 14, 1.50) - 30;
    }

    public  final static List<String> setExp(List<String> lore, int exp)
    {
        List<String> ll = lore;
        for (int i = 0; i < ll.size(); i++) {
            if(ll.get(i).matches(".*\\[等级].*")){
                ll.set(i + 3, "" + exp + "/" + getNextLevelExp(getLevel(lore)));
                break;
            }
        }
        return ll;
    }

    public final static boolean hasType(List<String> lore)
    {
        if(lore == null) return false;
        for (String s : lore)
        {
            if(s.contains("[武器类型]")) return true;
        }
        return false;
    }

    public final static void identifyType(ItemStack stack)
    {
        ItemMeta meta = stack.getItemMeta();
        if(meta.hasLore() && hasType(meta.getLore())) return;
        String type = null;
        int id = stack.getTypeId();
        switch (id)
        {
            case 4995:
            case 4996:
            case 5001:
            case 5002:
                type = "斧";
                break;
            case 261:
                type = "弓";
                break;
            case 5084:
            case 4917:
            case 4932:
            case 4942:
                type = "剑";
                break;
            default:
                return;
        }
        List<String> lore = meta.getLore();
        lore.add(Random.Colorilize("&6[武器类型] " + type));
        meta.setLore(lore);
        stack.setItemMeta(meta);

    }

    public final static String getType(List<String> lore)
    {
        for (String i : lore)
        {
            if(i.contains("[武器类型]"))
            {
                return i.substring(i.indexOf(' ') + 1);
            }
        }
        return "unknown";
    }

    public double getGrownByType(ItemStack stack)
    {
        return GROWN_TABLE.get(stack).value;
    }

    public int getExpByEntity(LivingEntity entity, String type)
    {
        //return EXP_TABLE.getExp(entity.getCustomName());
        //return 50;
        if(entity.hasMetadata(EnemyMetadata.ENEMY_META_FLAG))
        {
            EnemyMetadata enemyMetadata = (EnemyMetadata)entity.getMetadata(EnemyMetadata.ENEMY_META_FLAG).get(0);
            int baseExp;
            if(enemyMetadata.hasWeaponExp())
            {
                baseExp = enemyMetadata.getWeaponExp();
            }
            else
            {
                baseExp = (int)(Math.log(enemyMetadata.getLevel()) * EXP_TABLE.getExp(entity.getType()));
            }

            double proMul = 1.0;
            switch (type)
            {
                case "斧":
                break;
                case "剑":
                    proMul+=0.2;
                    break;
                case "弓":
                    proMul+=0.1;
                    break;
            }
            return (int)(baseExp * proMul);

        }
        else
        {
            return 5;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDead(EntityDeathEvent event)
    {
        if (event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent lastDamageCause = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause() ;
            onDead(lastDamageCause);
        }
        else
        {

        }
    }


    public void onDead(EntityDamageByEntityEvent event)
    {
        if ((event.getDamager() instanceof Player || event.getDamager() instanceof Projectile) &&
                event.getEntity() instanceof LivingEntity) {

            LivingEntity entityV = (LivingEntity) event.getEntity();

            ItemStack weapon = null;
            Entity entity = null;

            if (event.getDamager() instanceof Projectile)
            {
                Projectile projectile = (Projectile) event.getDamager();
                if(projectile.hasMetadata("Shooter"))
                    entity = ((Bow.MetaDataPlayer)(projectile.getMetadata("Shooter").get(0))).asPlayer();
            }
            else
            {
                entity = event.getDamager();
            }

            if (entity instanceof Player) {
                weapon = ((Player) entity).getItemInHand();
            }
            else if (entity instanceof LivingEntity) {
                weapon = ((LivingEntity) entity).getEquipment().getItemInHand();
            }

            ItemMeta meta = weapon.getItemMeta();
            List<String> lore = meta.getLore();

            if(checkLore(lore))
            {
                int exp = getExp(lore);
                if(exp < 0) exp = 0;
                exp += getExpByEntity(entityV, getType(lore));

                int lvl = getLevel(lore);
                int nextExp = getNextLevelExp(lvl);
                if(exp  > nextExp)
                {

                    lore = setExp(setLevel(lore, lvl + 1), exp - nextExp);
                    //升级之后重置耐久
                    weapon.setDurability((short)65535);

                    if(entity instanceof Player)
                    {
                        Player p = (Player) entity;
                        Grown grown = GROWN_TABLE.get(weapon);
                        if(grown != null) {
                            p.sendRawMessage(Random.Colorilize("&2您的武器已经升级到&fLv.&7" + lvl + "&2级，基础伤害: " + String.format("%.2f", grown.getDamage(lvl))));
                        }
                    }
                }
                else
                {
                    lore = setExp(lore, exp);
                }
                meta.setLore(lore);
            }

            weapon.setItemMeta(meta);
        }

    }

    @EventHandler(priority = EventPriority.LOW)
    public void replaceBaseDamage(EntityDamageByEntityEvent event)
    {

        ItemStack weapon = null;
        Entity entity = null;

        if (event.getDamager() instanceof Projectile)
        {
            Projectile projectile = (Projectile) event.getDamager();
            if(projectile.hasMetadata("Shooter"))
                entity = ((Bow.MetaDataPlayer)(projectile.getMetadata("Shooter").get(0))).asPlayer();
        }
        else
        {
            entity = event.getDamager();
        }

        if (entity instanceof Player) {
            weapon = ((Player) entity).getItemInHand();
        }
        else if (entity instanceof LivingEntity) {
            weapon = ((LivingEntity) entity).getEquipment().getItemInHand();
        }

        if(weapon == null) return;
        if(!weapon.hasItemMeta()) return;
        ItemMeta meta = weapon.getItemMeta();
        if(!meta.hasLore()) return;
        if(!checkLore(meta.getLore())) return;
        if(!hasType(meta.getLore())) return;

        Grown grown = GROWN_TABLE.get(weapon);
        if(grown == null) return;

        if(weapon.getType() == Material.BOW)
        {
            event.setDamage(1);
        }
        String node = null;
        switch (getType(meta.getLore()))
        {
            case "斧":
                node = "Basaker";
                break;
            case "剑":
                node = "Saber";
                break;
            case "弓":
                node = "Archer";
                break;
        }

        if (entity instanceof Player) {
            if (!BAKACraft.CHECK_PLAYER_PERM(entity, "BAKACraft." + node)) {
                Player damager = (Player) entity;
                damager.sendRawMessage(Random.Colorilize("您不是&b" + node + "&f，武器:" + meta.getDisplayName() + "&f的伤害大幅降低。(技能也无法触发)"));
                event.setDamage(1);
            }
            else
            {
                if(weapon.getType().equals(Material.BOW) && event.getDamager() instanceof Player)
                {
                    event.setDamage(1);
                }
                else event.setDamage(grown.getDamage(getLevel(meta.getLore())));
            }

            int lvl = getLevel(meta.getLore());
            BAKACraft.TIPS_BOARD.showInfo((Player)entity, "武器等级", lvl);
            BAKACraft.TIPS_BOARD.showInfo((Player)entity, "武器经验", getExp(meta.getLore()));
            return;
        }

        event.setDamage(grown.getDamage(getLevel(meta.getLore())));


    }

}
