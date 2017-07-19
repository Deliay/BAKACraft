package bakacraft.WeaponSkills.AxeEffects;

import bakacraft.WeaponSkills.BaseSkill;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by admin on 2017/7/7.
 */
public class BeatHeavenSR extends BaseSkill {

    @Override
    public String getName() {
        return "灵魂-重击SR";
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
        BeatHeavenR.BeatHeaven(getLevel(), event);
    }

    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "有25%的几率造成20%追加伤害，有5%的几率造成5%的生命移除效果");

    }

    @Override
    public int getChance() {
        return 7;
    }
}
