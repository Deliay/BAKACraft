package bakacraft.EnemySpawn;


import org.bukkit.inventory.ItemStack;

public class DropItem
{
    String key;
    int Chance;
    ItemStack item;
    public DropItem(String key, ItemStack itemStack, int chance)
    {
        this.key = key;
        Chance = chance; item = itemStack;
    }

    public String getKey() {
        return key;
    }

    public int getChance() {
        return Chance;
    }

    public ItemStack getItem() {
        return item;
    }
}