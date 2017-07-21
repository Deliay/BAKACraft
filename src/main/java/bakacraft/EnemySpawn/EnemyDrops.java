package bakacraft.EnemySpawn;

import bakacraft.BAKACraft;
import bakacraft.WeaponSkills.Random;
import bakalibs.CConfiger;
import com.comphenix.protocol.utility.StreamSerializer;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

public class EnemyDrops {

    public final static List<EnemyDrops> LOADED_DROPS = new LinkedList<>();

    public EnemyDrops(List<DropItem> items)
    {
        drops = items;
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
                ItemStack item = StreamSerializer.getDefault().deserializeItemStack(config.getString(key + ".Item"));
                int chance = config.getInt(key + ".Chance");
                items.add(new DropItem(item, chance));
            }catch (Exception e)
            {

            }
        }
        return new EnemyDrops(items);
    }
}
