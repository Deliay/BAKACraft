package bakacraft.EnemySpawn;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemySpawnCondition {
    public List<Biome> SpawnBiome;
    public List<World.Environment> SpawnEnvironment;
    public int SpawnChance;
    public int SpawnMaxLightLevel;
    public int SpawnMinLightLevel;
    public long StartSpawnTime;
    public long EndSpawnTime;
    public int SpawnMaxHeight;
    public int SpawnMinHeight;
    public int RequirePlayerLevel;

    public EnemySpawnCondition(final ConfigurationSection section)
    {
        RequirePlayerLevel = section.getInt("RequirePlayerLevel");
        SpawnBiome = readBiomeList(section.getStringList("SpawnBiome"));
        SpawnEnvironment = readEnvironmentList(section.getStringList("SpawnEnvironment"));
        SpawnChance = section.getInt("SpawnChance");
        SpawnMaxLightLevel = section.getInt("SpawnMaxLightLevel");
        SpawnMinLightLevel = section.getInt("SpawnMinLightLevel");
        StartSpawnTime = section.getLong("StartSpawnTime");
        EndSpawnTime = section.getLong("EndSpawnTime");
        SpawnMinHeight = section.getInt("SpawnMinHeight");
        SpawnMaxHeight = section.getInt("SpawnMaxHeight");
    }

    public List<Biome> readBiomeList(final List<String> list)
    {
        List<Biome> lst = new ArrayList<>();
        for (String i : list) lst.add(Biome.valueOf(i));
        return lst;
    }

    public List<World.Environment> readEnvironmentList(final List<String> list)
    {
        List<World.Environment> lst = new ArrayList<>();
        for (String i : list) lst.add(World.Environment.valueOf(i));
        return lst;
    }

    public EnemySpawnCondition(final EnemySpawnCondition old)
    {
        this.SpawnBiome = old.SpawnBiome;
        this.SpawnChance = old.SpawnChance;
        this.SpawnEnvironment = old.SpawnEnvironment;
        this.SpawnMaxLightLevel = old.SpawnMaxLightLevel;
        this.SpawnMinLightLevel = old.SpawnMinLightLevel;
        this.StartSpawnTime = old.StartSpawnTime;
        this.EndSpawnTime = old.EndSpawnTime;
        this.SpawnMinHeight = old.SpawnMinHeight;
        this.SpawnMaxHeight = old.SpawnMaxHeight;
        this.RequirePlayerLevel = old.RequirePlayerLevel;
    }

    public boolean checkSpawnCond(final Player player)
    {
        World world = player.getWorld();
        Location loc = player.getLocation();

        Biome b = world.getBiome(loc.getBlockX(), loc.getBlockZ());
        World.Environment env = world.getEnvironment();
        int curLight = world.getBlockAt(loc).getLightLevel();
        int curHeight = loc.getBlockY();
        long curTime = world.getTime();

        if(!SpawnBiome.contains(b)) return false;
        if(!SpawnEnvironment.contains(env)) return false;
        if(!(SpawnMaxLightLevel < curLight && curLight < SpawnMinLightLevel)) return false;
        if(!(SpawnMaxHeight < curHeight && curHeight < SpawnMinHeight)) return false;

        return isSpawnTime(curTime);

    }

    public boolean isSpawnTime(final long time)
    {
        return time >= StartSpawnTime && time <= EndSpawnTime;
    }

    public boolean isSpawnThisTick()
    {
        if(bakacraft.WeaponSkills.Random.TestRandom(SpawnChance))
        {
            return true;
        }
        return false;
    }

}
