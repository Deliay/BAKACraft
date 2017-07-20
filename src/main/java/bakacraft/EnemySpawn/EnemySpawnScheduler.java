package bakacraft.EnemySpawn;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EnemySpawnScheduler extends BukkitRunnable {

    EnemyManager manager;
    List<Enemy> spawnEnemies = new LinkedList<>();

    public EnemySpawnScheduler(EnemyManager parent)
    {
        manager = parent;
    }

    public void registerEnemyToSpawnScheduler(Enemy enemy)
    {
        spawnEnemies.add(enemy);
    }

    @Override
    public void run() {

    }
}
