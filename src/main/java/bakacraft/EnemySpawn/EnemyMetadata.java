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

    int level, health, weaponExp, playerExp;
    double damage;
    boolean boolhasSpecialExp;
    String dropName;
    Enemy belong;
    EnemyDrops drops;
    public EnemyMetadata(int Level)
    {
        level = Level;
        health = (int)(level * 0.7);
        if(health == 0) health = 1;
        health += Random.RandomRange(health);
        boolhasSpecialExp = false;
    }

    public EnemyMetadata(int Level, int SpecialWeaponExp, int SpecialPlayerExp, double BaseDamage)
    {
        level = Level;
        boolhasSpecialExp = true;
        weaponExp = SpecialWeaponExp;
        playerExp = SpecialPlayerExp;
        damage = BaseDamage;
    }

    public String getDropName() {
        return dropName;
    }

    public EnemyDrops getDrops() {
        return drops;
    }

    public void setDropName(String dropName) {
        this.dropName = dropName;
        drops = EnemyDrops.loadDrop(dropName);
    }

    public void setParent(Enemy parent)
    {
        belong = parent;
    }

    public boolean hasWeaponExp()
    {
        return boolhasSpecialExp;
    }

    public int getWeaponExp()
    {
        return weaponExp;
    }

    public int getPlayerExp() {
        return playerExp;
    }

    public double getDamage()
    {
        if(boolhasSpecialExp)
            return damage;
        else
            return (level * 0.3);
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
