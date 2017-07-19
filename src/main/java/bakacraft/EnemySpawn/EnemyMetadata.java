package bakacraft.EnemySpawn;

import bakacraft.BAKACraft;
import bakacraft.WeaponSkills.Random;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

/**
 * Created by admin on 2017/7/16.
 */
public class EnemyMetadata implements MetadataValue {

    public final static String ENEMY_META_FLAG = "Enemy_Meta_data";

    int level, health, baseExp;
    boolean boolhasSpecialExp;
    public EnemyMetadata(int Level)
    {
        level = Level;
        health = (int)(level * 0.7);
        if(health == 0) health = 1;
        health += Random.RandomRange(health);
        boolhasSpecialExp = false;
    }

    public EnemyMetadata(int Level, int SpecialBaseExp)
    {
        level = Level;
        boolhasSpecialExp = true;
        baseExp = SpecialBaseExp;
    }

    public boolean hasSpecialExp()
    {
        return boolhasSpecialExp;
    }

    public int getSpecialExp()
    {
        return baseExp;
    }

    public int getDamage()
    {
        return (int)(level * 0.3);
    }

    public int getLevel()
    {
        return level;
    }

    public int getHealth()
    {
        return health;
    }

    @Override
    public Object value() {
        return null;
    }

    @Override
    public int asInt() {
        return 0;
    }

    @Override
    public float asFloat() {
        return 0;
    }

    @Override
    public double asDouble() {
        return 0;
    }

    @Override
    public long asLong() {
        return 0;
    }

    @Override
    public short asShort() {
        return 0;
    }

    @Override
    public byte asByte() {
        return 0;
    }

    @Override
    public boolean asBoolean() {
        return false;
    }

    @Override
    public String asString() {
        return null;
    }

    @Override
    public Plugin getOwningPlugin() {
        return BAKACraft.instance;
    }

    @Override
    public void invalidate() {

    }
}