package bakacraft.WeaponSkills.BowEffects;

import bakacraft.WeaponSkills.BaseSkillBow;
import bakacraft.WeaponSkills.Random;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by admin on 2017/7/14.
 */
public class ForzenN extends BaseSkillBow {

    public final static void Forzen(int level, EntityDamageEvent event, LivingEntity by) {
        if (Random.TestRandom(50)) {
            if (event.getEntity() instanceof LivingEntity) {
                LivingEntity entity = (LivingEntity) event.getEntity();
                entity.setHealth(entity.getHealth() * (0.9 - level * 0.02));
            }
        }
        if (Random.TestRandom(50)) {
            if (event.getEntity() instanceof LivingEntity) {
                LivingEntity entity = (LivingEntity) event.getEntity();
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 3));
            }
        }
        if (event.getEntity().getFireTicks() > 0) {
            event.setDamage(event.getDamage() * (0.6 + level * 0.05));
        }

    }

    @Override
    public String getName() {
        return "极寒之箭N";
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
        ForzenN.Forzen(getLevel(), event, by);
    }

    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "50%的几率移除目标10%当前生命\n" +
                "50%的几率目标缓慢2秒\n" +
                "攻击被点燃的目标减少40%的伤害");
    }

    @Override
    public int getChance() {
        return 60;
    }

}
