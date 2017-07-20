package bakacraft.EnemySpawn;

import bakacraft.BAKACraft;
import bakacraft.EnemySpawner;
import bakacraft.WeaponSkills.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/*
/**
 * Created by admin on 2017/7/16.
 */
public class Enemy {
    public int RecoverSpeed;
    public int Level;
    public String CustomName;
    public double MaxHealth;
    public EntityType Model;
    public double BaseDamage;
    public double EnhancementExp;
    public int WeaponExp;
    public int PlayerExp;
    public EnemySpawnCondition Condition;
    public EnemyMetadata Metadata;
    public int SpawnMaxDistance;
    public int SpawnMinDistance;
    public int DamageTick;
    public LivingEntity monster;
    public String DropTable;

    public boolean isMonsterSpawned()
    {
        return monster != null;
    }

    public LivingEntity getMonster() {
        return monster;
    }

    public Enemy(ConfigurationSection section, EnemyMetadata metadata)
    {
        loadFormSettingSection(section, metadata);
    }

    public Enemy(Enemy old)
    {
        this.Model = old.Model;
        this.Level = old.Level;
        this.MaxHealth = old.MaxHealth;
        this.CustomName = old.CustomName;
        this.BaseDamage = old.BaseDamage;
        this.EnhancementExp = old.EnhancementExp;
        this.Metadata = old.Metadata;
        this.Condition = old.Condition;
        this.WeaponExp = old.WeaponExp;
        this.PlayerExp = old.PlayerExp;
        this.SpawnMaxDistance = old.SpawnMaxDistance;
        this.SpawnMinDistance = old.SpawnMinDistance;
        this.DamageTick = old.DamageTick;
        this.DropTable = old.DropTable;

    }

    public void loadFormSettingSection(ConfigurationSection section, EnemyMetadata meta)
    {
        Level = section.getInt("Level");
        CustomName = section.getString("CustomName");
        MaxHealth = section.getDouble("MaxHealth");
        Model = EntityType.valueOf(section.getString("Model"));
        BaseDamage = section.getDouble("BaseDamage");
        EnhancementExp = section.getDouble("EnhancementExp");
        Condition = new EnemySpawnCondition(section.getConfigurationSection("Condition"));
        RecoverSpeed = section.getInt("RecoverSpeed");
        Metadata = meta;
        SpawnMaxDistance = section.getInt("SpawnMaxDistance");
        SpawnMinDistance = section.getInt("SpawnMinDistance");
        DamageTick = section.getInt("DamageTick");
        DropTable = section.getString("DropTable");
        Metadata.setParent(this);
        Metadata.setDropName(DropTable);
    }

    public void applyTo(LivingEntity entity)
    {
        monster = entity;
        Metadata.level = Level;
        monster.setMetadata(EnemyMetadata.ENEMY_META_FLAG, Metadata);
        monster.setCustomName(EnemySpawner.COMBINE_LEVEL_MONSTER_NAME(CustomName, Level));
        monster.setCustomNameVisible(true);
        monster.setMaxHealth(MaxHealth);
        monster.setHealth(MaxHealth);
        monster.setRemoveWhenFarAway(true);
        monster.setMaximumNoDamageTicks(DamageTick);


    }

    public Enemy copy()
    {
        return new Enemy(this);
    }

    public boolean trySpawnToPlayer(Player player)
    {
        if(Condition.checkSpawnCond(player))
        {
            Location player_loc = player.getLocation();
            int destX = SpawnMinDistance + Random.RandomRange(SpawnMaxDistance - SpawnMinDistance);
            int destZ = SpawnMinDistance + Random.RandomRange(SpawnMaxDistance - SpawnMinDistance);
            Location monster_loc = player_loc.add(destX, 0, destZ);
            monster_loc.setY(player.getWorld().getHumidity(monster_loc.getBlockX(), monster_loc.getBlockZ()));

            applyTo((LivingEntity) player.getWorld().spawnEntity(monster_loc, Model));
            return true;
        }
        return false;
    }

}