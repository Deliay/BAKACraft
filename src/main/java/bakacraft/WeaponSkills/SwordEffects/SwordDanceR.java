package bakacraft.WeaponSkills.SwordEffects;

import bakacraft.WeaponSkills.BaseSkill;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by admin on 2017/7/8.
 */
public class SwordDanceR extends BaseSkill {
    @Override
    public String getName() {
        return "[幻影剑舞R]";
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public boolean isOnly() {
        return false;
    }

    @Override
    public void applyEffect(EntityDamageByEntityEvent event) {
        SwordDanceN.SwordDance(getLevel(), event);
    }

    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "有25%的几率追加10%伤害，判定成功后，会再次执行追加判定");
    }


    @Override
    public int getChance() {
        return 20;
    }
}
