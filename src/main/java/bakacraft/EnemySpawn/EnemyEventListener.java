package bakacraft.EnemySpawn;

import bakacraft.BAKACraft;
import bakacraft.PlayerLevel;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class EnemyEventListener implements Listener {
    EnemyManager manager;
    public EnemyEventListener(EnemyManager manager)
    {
        this.manager = manager;
    }

    public void onEntityDead(EntityDeathEvent event)
    {
        if (event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent cause = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();
            LivingEntity entity = EnemyManager.SPAWN_SCHEDULER.getLivingEntity(cause.getEntity().getUniqueId());
            if(entity != null && entity.hasMetadata(EnemyMetadata.ENEMY_META_FLAG) && cause.getDamager() instanceof Player)
            {
                //计算掉落、经验等
                EnemyMetadata meta = (EnemyMetadata)entity.getMetadata(EnemyMetadata.ENEMY_META_FLAG);
                Enemy enemy = meta.belong;
                Player player = (Player) cause.getDamager();
                event.setDroppedExp((int)enemy.EnhancementExp);
                //人物等级也交给meta
                //BAKACraft.playerLevel.addExp(playerUUID, enemy.PlayerExp);
                //武器等级交给meta
                List<ItemStack> dropItems = meta.getDrops().testDrop();
                for (ItemStack item : dropItems)
                {
                    player.getWorld().dropItem(entity.getLocation(), item);
                }
            }
        }
    }
}
