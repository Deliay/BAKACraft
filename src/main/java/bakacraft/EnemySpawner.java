package bakacraft;

import bakacraft.EnemySpawn.EnemyEventListener;
import bakacraft.EnemySpawn.EnemyManager;
import bakacraft.EnemySpawn.EnemyMetadata;
import bakacraft.EnemySpawn.SpawnCommand;
import bakacraft.WeaponSkills.Random;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.event.EventPriority.MONITOR;

/**
 * Created by admin on 2017/7/16.
 */
public class EnemySpawner extends BAKAPlugin implements Listener
{
    public static EnemyManager ENEMY_MANAGER;
    public static SpawnCommand SPAWN_COMMAND;
    public static EnemyEventListener ENEMY_EVENTS;

    public EnemySpawner(JavaPlugin instance) {
        super(instance);
        getInstance().getServer().getPluginManager().registerEvents(this, instance);
        ENEMY_MANAGER = new EnemyManager();
        SPAWN_COMMAND = new SpawnCommand();
        ENEMY_EVENTS = new EnemyEventListener(ENEMY_MANAGER);
        BAKACraft.instance.getServer().getPluginManager().registerEvents(ENEMY_EVENTS, BAKACraft.instance);
    }

    @EventHandler(priority = MONITOR)
    public void onNaturalAttack(EntityDamageByEntityEvent event)
    {
        if(event.getDamager().hasMetadata(EnemyMetadata.ENEMY_META_FLAG))
        {
            EnemyMetadata meta = (EnemyMetadata)event.getDamager().getMetadata(EnemyMetadata.ENEMY_META_FLAG).get(0);
            event.setDamage(meta.getDamage());
        }
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event)
    {
        CreatureSpawnEvent.SpawnReason reason = event.getSpawnReason();
        switch (reason)
        {
            case SPAWNER_EGG:
            case DISPENSE_EGG:
            case NATURAL:
            case SLIME_SPLIT:
            case LIGHTNING:
                onNatureSpawn(event);
                return;

            case CUSTOM:
                onPlugin(event);
                return;
            default:
                break;
        }
    }

    public static final String getChinese(EntityType type)
    {
        switch (type)
        {
            case ZOMBIE:
                return "僵尸";
            case PIG_ZOMBIE:
                return "僵尸猪人";
            case SKELETON:
                return "骷髅战士";
            case ENDERMAN:
                return "末影人";
            case CREEPER:
                return "苦力啪";
            case SPIDER:
                return "蜘蛛";
            case CAVE_SPIDER:
                return "洞穴蜘蛛";
            case GHAST:
                return "恶魂";
            case SLIME:
                return "史莱姆";
            case BLAZE:
                return "烈焰人";
            case WITCH:
                return "女巫";
            default:
                return type.name();
        }

    }

    public static final String COMBINE_LEVEL_MONSTER_NAME(String name, int level)
    {
        return "&2" + name + " &cLv." + level;
    }

    private void onNatureSpawn(CreatureSpawnEvent event)
    {
        int level = Random.RandomRange(100);
        if(level <= 10) level = Random.RandomRange(100);
        else if(level >10 && level <= 40) level = Random.RandomRange(80);
        else if(level > 40 && level <=80) level = Random.RandomRange(40);
        else if(level > 80 && level <= 100) level = Random.RandomRange(60);
        else level = Random.RandomRange(1000);

        EntityType type = event.getEntity().getType();
        LivingEntity mob = event.getEntity();
        mob.setCustomNameVisible(true);
        EnemyMetadata meta = null;
        switch (type)
        {
            case SKELETON:
            case PIG_ZOMBIE:
            case SPIDER:
            case CAVE_SPIDER:
            case GHAST:
            case SLIME:
            case BLAZE:
            case WITCH:
            case ENDER_DRAGON:
            case ZOMBIE:
                meta = new EnemyMetadata(level);
                mob.setMetadata(EnemyMetadata.ENEMY_META_FLAG, meta);
                mob.setMaxHealth(meta.getHealth());
                mob.setHealth(meta.getHealth());
                mob.setCustomName(Random.Colorilize(COMBINE_LEVEL_MONSTER_NAME(getChinese(mob.getType()),  meta.getLevel())));
                break;
            case CREEPER:
                meta  = new EnemyMetadata(level, 10, 10);
                Creeper cp = (Creeper) mob;
                cp.setMaxHealth(40);
                cp.setHealth(40);
                cp.setMetadata(EnemyMetadata.ENEMY_META_FLAG, meta);
                mob.setCustomName(COMBINE_LEVEL_MONSTER_NAME("苦力怕", meta.getLevel()));
                break;
            case ENDERMAN:
                meta  = new EnemyMetadata(level, 20,20);
                mob.setMetadata(EnemyMetadata.ENEMY_META_FLAG, meta);
                Enderman em = (Enderman) mob;
                if(Random.TestRandom(20))
                {
                    em.setMaxHealth(70);
                    em.setHealth(70);
                    em.getEquipment().setItemInHand(new ItemStack(Material.BLAZE_ROD));
                    em.setCustomName(COMBINE_LEVEL_MONSTER_NAME("烈焰精华末影人", meta.getLevel()));
                }
                break;
        }
    }

    private void onPlugin(CreatureSpawnEvent event)
    {
    }

}
