package bakacraft.EnemySpawn;


import org.bukkit.inventory.ItemStack;

public class DropItem
{
    int Chance;
    ItemStack item;
    public DropItem(ItemStack itemStack, int chance)
    {
        Chance = chance; item = itemStack;
    }

    public int getChance() {
        return Chance;
    }

    public ItemStack getItem() {
        return item;
    }
}