package bakacraft.WeaponSkills.AxeEffects;

import bakacraft.WeaponSkills.BaseSkill;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by admin on 2017/7/7.
 */
public class RotatedAxeSR extends BaseSkill {
    @Override
    public String getName() {
        return "战争饥渴-旋风飞斧SR";
    }

    @Override
    public int getLevel() {
        return 2;
    }

    @Override
    public boolean isOnly() {
        return false;
    }

    @Override
    public void applyEffect(EntityDamageByEntityEvent event) {
        RotatedAxeN.RotateDamage(getLevel(), event);
    }

    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "有30%的几率造成1+本次攻击伤害20%范围群体伤害，并减少1点饱食度(3*3)");
    }

    @Override
    public int getChance() {
        return 7;
    }
}
