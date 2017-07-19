package bakacraft;

import bakacraft.EnemySpawn.EnemyMetadata;
import bakacraft.WeaponSkills.Bow;
import bakacraft.WeaponSkills.Random;
import bakalibs.CConfiger;
import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.events.GMUserEvent;
import org.anjocaido.groupmanager.events.GroupManagerEventHandler;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by admin on 2017/7/17.
 */
public class PlayerLevel extends BAKAPlugin implements Listener {

    public final static int INITIAL_EXP = 14;
    public final static double LEVEL_UP_EXP_MULTIPLY = 1.5;
    public final static int MAX_LEVEL = 60;
    public final static double BASAKER_PER_LEVEL_HEALTH = 0.5;
    public final static float BASE_MOVE_SPEED = 0.2f;
    public final static float BASAKER_MOVE_SPEED = 0.13f;
    public final static float SABER_MAX_LEVEL_MOVE_SPEED = 0.306667f - BASE_MOVE_SPEED;
    public final static float SABER_PER_LEVEL_MOVE_SPEED = SABER_MAX_LEVEL_MOVE_SPEED / 50;
    public CConfiger CONFIG_FILE;
    public final static HashMap<UUID, Double> EXP_TABLE = new HashMap<>();
    public static GroupManager gm = null;

    public PlayerLevel(JavaPlugin instance) {
        super(instance);
        CONFIG_FILE = new CConfiger(instance, "PlayerLevel.yml");
        getInstance().getServer().getPluginManager().registerEvents(this, BAKACraft.instance);
        gm = (GroupManager) getInstance().getServer().getPluginManager().getPlugin("GroupManager");

    }
    @EventHandler
    public void onPlayerGroupChange(GMUserEvent event)
    {
        if(event.getAction().equals(GMUserEvent.Action.USER_GROUP_CHANGED))
        {
            groupChange(event.getUser().getBukkitPlayer());
        }
    }

    public void groupChange(Player p)
    {
        resetPlayer(p);
    }

    public int getLevel(Player player)
    {
        return getLevel(player.getUniqueId());
    }

    public int getLevel(UUID playerUUID) {

        int lvl;
        double exp = getExp(playerUUID);
        for (lvl = 0; exp > 0; lvl++)
            exp -= lvl * INITIAL_EXP * LEVEL_UP_EXP_MULTIPLY;
        lvl = lvl > 0 ? lvl - 1 : 0;
        return lvl > MAX_LEVEL ? MAX_LEVEL : lvl;
    }

    public enum Type
    {
        SABER,
        BASAKER,
        ARCHER,
    }

    public Type getPlayerType(Player p)
    {
        if(gm.getWorldsHolder().getWorldData(p).getUser(p.getName()).getGroupName().contains("Saber")) return Type.SABER;
        if(gm.getWorldsHolder().getWorldData(p).getUser(p.getName()).getGroupName().contains("Archer")) return Type.ARCHER;
        if(gm.getWorldsHolder().getWorldData(p).getUser(p.getName()).getGroupName().contains("Basaker")) return Type.BASAKER;
        return null;
    }

    public void resetPlayer(Player p)
    {
        Type t = getPlayerType(p);
        p.setMaxHealth(20);

        p.setWalkSpeed(BASE_MOVE_SPEED);

        if (t.equals(Type.BASAKER)) {
            p.setWalkSpeed(BASAKER_MOVE_SPEED);
            p.setMaxHealth(p.getMaxHealth() + getLevel(p) * BASAKER_PER_LEVEL_HEALTH);
        } else if (t.equals(Type.SABER)) {
            p.setWalkSpeed(BASE_MOVE_SPEED + getLevel(p) * SABER_PER_LEVEL_MOVE_SPEED);
        }

        p.resetMaxHealth();

    }

    public void resetPlayer(UUID playerUUID)
    {
        resetPlayer(getInstance().getServer().getPlayer(playerUUID));
    }

    public String getFormatedLevel(UUID playerUUID) {
        return "&f[&9Lv.&7" + getLevel(playerUUID) + "&f]";
    }

    public void WriteInGameExp(UUID playerUUID, double exp) {
        int level = getLevel(playerUUID);
        EXP_TABLE.put(playerUUID, exp);
        int putLevel = getLevel(playerUUID);
        if(level < putLevel)
        {
            int f = putLevel - level;
            Player p = getInstance().getServer().getPlayer(playerUUID);
            resetPlayer(p);
            p.sendRawMessage(Random.Colorilize("&2您已升级，&fLv.&7" + level + "&2->&fLv.&7" + putLevel));
        }
    }

    public double GetInGameExp(UUID playerUUID) {
        return EXP_TABLE.get(playerUUID).intValue();
    }

    public double getExp(UUID playerUUID) {
        return GetInGameExp(playerUUID);
    }

    public void addExp(UUID playerUUID, double exp) {
        Player p = getInstance().getServer().getPlayer(playerUUID);
        setExp(playerUUID, getExp(playerUUID) + exp);
        BAKACraft.TIPS_BOARD.showInfo(p, "人物等级", getLevel(playerUUID));
        BAKACraft.TIPS_BOARD.showInfo(p, "人物经验", (int) getExp(playerUUID));

        return;
    }

    public void setExp(UUID playerUUID, double exp) {

        WriteInGameExp(playerUUID, exp);
        return;
    }

    public void updatePlayerExp(UUID playerUUID, double exp) {
        CONFIG_FILE.set(playerUUID.toString(), exp);
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        UUID playerUid = event.getPlayer().getUniqueId();
        if (!EXP_TABLE.containsKey(playerUid)) {
            EXP_TABLE.put(playerUid, CONFIG_FILE.getDouble(playerUid.toString()));
        }
        CONFIG_FILE.saveConfig();

        resetPlayer(playerUid);

        BAKACraft.TIPS_BOARD.showInfo(event.getPlayer(), "人物等级", getLevel(playerUid));
        BAKACraft.TIPS_BOARD.showInfo(event.getPlayer(), "人物经验", (int) getExp(playerUid));

    }

    @EventHandler
    public void onPlayerDead(PlayerDeathEvent event)
    {
        event.getEntity().setMaxHealth(20);
        event.getEntity().setWalkSpeed(1);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event)
    {

        resetPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        UUID playerUUID = event.getPlayer().getUniqueId();
        updatePlayerExp(playerUUID, GetInGameExp(playerUUID));
        CONFIG_FILE.saveConfig();
    }

    @EventHandler
    public void onPlayerChatAsync(AsyncPlayerChatEvent event)
    {
        event.setFormat(getFormatedLevel(event.getPlayer().getUniqueId()) + event.getFormat());
    }

    @EventHandler
    public void onEntityDead(EntityDeathEvent event) {
        if (event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent cause = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();

            Entity src_damager = cause.getDamager();

            if(src_damager instanceof Projectile)
            {
                if(src_damager.hasMetadata("Shooter"))
                    src_damager = ((Bow.MetaDataPlayer)(src_damager.getMetadata("Shooter").get(0))).asPlayer();
            }

            if (src_damager instanceof Player &&
                    cause.getEntity() instanceof LivingEntity) {
                Player damager = (Player) src_damager;
                LivingEntity entity = (LivingEntity) cause.getEntity();

                if (entity.hasMetadata(EnemyMetadata.ENEMY_META_FLAG)) {
                    EnemyMetadata enemyMetadata = (EnemyMetadata) entity.getMetadata(EnemyMetadata.ENEMY_META_FLAG).get(0);
                    int baseExp;
                    if (enemyMetadata.hasSpecialExp()) {
                        baseExp = enemyMetadata.getSpecialExp();
                    } else {
                        baseExp = WeaponLevel.EXP_TABLE.getExp(entity.getType());
                    }

                    addExp(damager.getUniqueId(), baseExp);

                }
                else
                {
                    addExp(damager.getUniqueId(), 5);
                }
            }
        }
    }
}
