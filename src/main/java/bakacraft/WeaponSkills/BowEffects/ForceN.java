package bakacraft.WeaponSkills.BowEffects;

import bakacraft.WeaponSkills.BaseSkillBow;
import bakacraft.WeaponSkills.Random;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by admin on 2017/7/13.
 */
public class ForceN extends BaseSkillBow {
    public static final void Force(int level, EntityDamageEvent event, LivingEntity by) {
        double dmgMul = 1.3 + level * 0.03;
        boolean knockback = level > 1;

        if (Random.TestRandom(20 + level * 5)) {
            event.setDamage(event.getDamage() * dmgMul);
            if (event.getEntity() instanceof LivingEntity) {
                LivingEntity le = (LivingEntity) event.getEntity();
                le.damage(1, by);
                if (knockback) le.damage(1, by);
            }
        }
    }

    @Override
    public String getName() {
        return "强力击N";
    }

    @Override
    public int getLevel() {
        return 0;
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
        Force(getLevel(), event, by);
    }

    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "有20%的几率追加30%的伤害，并击退目标1个单位");
    }

    @Override
    public int getChance() {
        return 60;
    }
}
