package bakacraft.EnemySpawn;

import bakacraft.WeaponSkills.Random;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

public class EnemyDrops {

    public class DropItem
    {
        int Chance;
        ItemStack item;
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

    public void loadDrops(String drops){
        // TODO:
    }
}
