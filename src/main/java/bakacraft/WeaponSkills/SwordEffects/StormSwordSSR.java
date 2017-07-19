package bakacraft.WeaponSkills.SwordEffects;

import bakacraft.WeaponSkills.BaseSkill;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by admin on 2017/7/8.
 */
public class StormSwordSSR extends BaseSkill {
    @Override
    public String getName() {
        return "[剑刃风暴R]";
    }

    @Override
    public int getLevel() {
        return 3;
    }

    @Override
    public boolean isOnly() {
        return false;
    }

    @Override
    public void applyEffect(EntityDamageByEntityEvent event) {
        StormSwordN.StormSword(getLevel(), event);
    }

    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "35%的几率产生附加基础攻击30%的3X3范围攻击\n并附加晕眩效果1秒");
    }

    @Override
    public int getChance() {
        return 1;
    }
}
