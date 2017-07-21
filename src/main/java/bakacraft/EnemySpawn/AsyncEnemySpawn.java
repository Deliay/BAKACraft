package bakacraft.EnemySpawn;

import bakacraft.BAKACraft;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class AsyncEnemySpawn extends BukkitRunnable{

    HashMap<UUID, LivingEntity> spawnedEnemy = new HashMap<>();

    Enemy enemy;
    public AsyncEnemySpawn(Enemy enemy)
    {
        this.enemy = enemy;
    }

    public boolean existLivingEntity(final UUID uid)
    {
        return spawnedEnemy.containsKey(uid);
    }

    public LivingEntity getLivingEntity(final UUID uid)
    {
        return spawnedEnemy.get(uid);
    }

    public void remove(UUID uid)
    {
        spawnedEnemy.get(uid).remove();
        spawnedEnemy.remove(uid);
    }

    public void registerEntity(LivingEntity entity)
    {
        spawnedEnemy.put(entity.getUniqueId(), entity);
    }

    @Override
    public void run() {
        for (Player p : BAKACraft.instance.getServer().getOnlinePlayers())
        {
            LivingEntity e = enemy.trySpawnToPlayer(p);
            if(e != null)
            {
                spawnedEnemy.put(e.getUniqueId(), e);
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        for (UUID u : spawnedEnemy.keySet())
        {
            spawnedEnemy.get(u).remove();
            spawnedEnemy.remove(u);
        }
        super.finalize();
    }
}
