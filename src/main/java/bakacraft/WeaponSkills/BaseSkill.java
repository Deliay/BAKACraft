package bakacraft.WeaponSkills;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/7/7.
 */
public abstract class BaseSkill {
    //技能的基础

    public static final String GetColorByLevel(int level) {
        switch (level) {
            case 0:
                return "&5";
            case 1:
                return "&9";
            case 2:
                return "&3";
            case 3:
                return "&6";
        }
        return "";
    }

    public static final void BurnDescription(BaseSkill src, ItemStack item, String description) {
        ItemMeta meta = item.getItemMeta();
        List<String> lores = meta.getLore() == null ? new ArrayList<String>() : meta.getLore();
        lores.add(Random.Colorilize(BaseSkill.GetColorByLevel(src.getLevel()) + src.getName()));
        for (String s : description.split("\n")) {
            lores.add(Random.Colorilize("&8" + s));
        }

        meta.setLore(lores);
        item.setItemMeta(meta);
    }

    //技能名称
    public abstract String getName();

    //获得技能等级 N-0 R-1 SR-2 SSR-3
    public abstract int getLevel();

    //获得是失专属技能
    public abstract boolean isOnly();

    //根据伤害事件计算技能效果
    public abstract void applyEffect(EntityDamageByEntityEvent event);

    //将效果附加到武器上
    public abstract void burnToWeapon(ItemStack weapon);

    public abstract int getChance();
}
