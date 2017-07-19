package bakacraft.WeaponSkills.BowEffects;

import bakacraft.WeaponSkills.BaseSkillBow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by admin on 2017/7/13.
 */
public class SplitArrowSR extends BaseSkillBow {

    @Override
    public String getName() {
        return "分裂箭SR";
    }

    @Override
    public int getLevel() {
        return 2;
    }

    @Override
    public boolean isOnly() {
        return true;
    }


    @Override
    public void applyEffect(EntityDamageByEntityEvent event) {
    }

    @Override
    public void applyEffect(EntityDamageEvent event, LivingEntity by) {
        SplitArrowR.SplitArrow(getLevel(), event, by);
    }

    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "对目标连续射出3只箭矢，伤害降低13%");
    }

    @Override
    public int getChance() {
        return 4;
    }
}
