package bakacraft.EnemySpawn;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/*
/**
 * Created by admin on 2017/7/16.
 */
public interface Enemy {

    public int getRequirPlayerLevel();

    public String getCustomName();

    public int getMaxHealt();

    public List<ItemStack> getDrops();

    public void putSettings(LivingEntity entity);

    public EntityType getModel();

    public int getRecoverSpeed();

    public Creature getSpawnCreature();

    public Biome getSpawnBiome();

    public World.Environment getSpawnEnviroment();

    public boolean checkSpawnCond(World world, Player player);

    public boolean isSpawnTime(long time);

    public int getSpawnChance();

    public int getSpawnLightLevel();

    public double getBaseDamage();

    public double getKillExp();

    public boolean isSpawnThisTick();

}