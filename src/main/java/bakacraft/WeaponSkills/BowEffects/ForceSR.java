package bakacraft.WeaponSkills.BowEffects;

import bakacraft.WeaponSkills.BaseSkillBow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by admin on 2017/7/13.
 */
public class ForceSR extends BaseSkillBow {
    @Override
    public String getName() {
        return "强力击SR";
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

    }

    @Override
    public void applyEffect(EntityDamageEvent event, LivingEntity by) {
        ForceN.Force(getLevel(), event, by);
    }


    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "有30%的几率追加36%的伤害，并击退目标2个单位");
    }

    @Override
    public int getChance() {
        return 4;
    }
}
