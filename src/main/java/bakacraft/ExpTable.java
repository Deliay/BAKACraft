package bakacraft;

import org.bukkit.entity.EntityType;

import java.util.HashMap;

/**
 * Created by admin on 2017/7/8.
 */
public class ExpTable
{
    public final HashMap<EntityType, Integer> expMap_axe = new HashMap<>();


    public final void putExp(EntityType ENT, Integer AXE)
    {
        expMap_axe.put(ENT,AXE);

    }

    public Integer getExp_axe(EntityType entity)
    {
        return expMap_axe.get(entity);
    }

    public Integer getExp(EntityType entity)
    {

        return getExp_axe(entity);

    }
}
