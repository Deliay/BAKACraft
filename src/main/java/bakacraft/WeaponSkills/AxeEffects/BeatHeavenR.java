package bakacraft.WeaponSkills.AxeEffects;

import bakacraft.WeaponSkills.BaseSkill;
import bakacraft.WeaponSkills.Random;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by admin on 2017/7/7.
 */
public class BeatHeavenR extends BaseSkill {

    public static final void BeatHeaven(int level, EntityDamageByEntityEvent event) {
        if (Random.TestRandom(25)) {
            double dmg = event.getDamage();

            if (level > 2) dmg *= 1.3;
            else dmg *= 1.2;

            event.setDamage(dmg);

            if (level > 1) {
                if (event.getEntity() instanceof LivingEntity && Random.TestRandom(5 * (level - 1))) {
                    LivingEntity le = (LivingEntity) event.getEntity();
                    double puredmg = event.getDamage();
                    if (level == 2) puredmg *= .5;
                    else if (level == 3) puredmg *= 0.7;
                    le.setHealth(le.getHealth() - puredmg);
                }
            }
        }
    }

    @Override
    public String getName() {
        return "重击R";
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
        BeatHeavenR.BeatHeaven(getLevel(), event);
    }

    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "有25%的几率造成20%追加伤害");

    }

    @Override
    public int getChance() {
        return 25;
    }
}
