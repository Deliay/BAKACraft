package bakacraft.EnemySpawn;

import bakacraft.BAKACraft;
import bakacraft.WeaponSkills.Random;
import bakalibs.CConfiger;
import com.comphenix.protocol.utility.StreamSerializer;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

public class EnemyDrops implements ISave
{

    public final static List<EnemyDrops> LOADED_DROPS = new LinkedList<>();

    String DropName;
    public EnemyDrops(String dropName, List<DropItem> items)
    {
        DropName = dropName;
        drops = items;
        EnemyStorage.getInstance().registerStorage(this);
    }

    List<DropItem> drops;

    public List<ItemStack> testDrop()
    {
        List<ItemStack> itemList = new LinkedList<>();
        for (DropItem i : getDrops())
        {
            if(Random.TestRandom(i.Chance)) itemList.add(new ItemStack(i.item));
        }
        return itemList;
    }

    public void addItem(String key, ItemStack item, int chance)
    {
        drops.add(new DropItem(key, item, chance));
    }

    public void removeAllItem()
    {
        drops.clear();
    }

    public void removeLast()
    {
        drops.remove(drops.size() - 1);
    }

    public List<DropItem> getDrops() {
        return drops;
    }

    public void setDrops(List<DropItem> drops) {
        this.drops = drops;
    }

    public final static EnemyDrops loadDrop(String drops){
        List<DropItem> items = new LinkedList<>();
        CConfiger config = new CConfiger(BAKACraft.instance, "DropTables",  drops.concat(".yml"));
        for (String key : config.getKeys(false))
        {
            try {
                ItemStack item = config.getItemStack(key + ".Item");
                int chance = config.getInt(key + ".Chance");
                items.add(new DropItem(key, item, chance));
            }catch (Exception e)
            {

            }
        }
        return new EnemyDrops(drops, items);
    }

    @Override
    public void save() {
        CConfiger config = new CConfiger(BAKACraft.instance, "DropTables", DropName.concat(".yml"));
        for (DropItem item : drops)
        {
            try {
                config.set(item.getKey().concat(".Item"), item.getItem());
                config.set(item.getKey().concat(".Chance"), item.getChance());
            }
            catch (Exception e)
            {

            }
        }
        config.saveConfig();
    }
}
