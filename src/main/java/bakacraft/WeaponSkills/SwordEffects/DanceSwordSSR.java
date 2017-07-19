package bakacraft.WeaponSkills.SwordEffects;

import bakacraft.WeaponSkills.BaseSkill;
import bakacraft.WeaponSkills.Random;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by admin on 2017/7/8.
 */
public class DanceSwordSSR extends BaseSkill {
    public final static void DanceSword(int level, EntityDamageByEntityEvent event) {
        if (Random.TestRandom(25)) {
            event.setDamage(event.getDamage() * 2);
        }
        if (Random.TestRandom(30)) {
            event.setDamage(event.getDamage() * 1.6);
        }
        if (Random.TestRandom(35)) {
            event.setDamage(event.getDamage() * 1.3);
        }
        if (Random.TestRandom(40)) {
            event.setDamage(event.getDamage() * 1.2);
        }
        if (Random.TestRandom(45)) {
            if (event.getEntity() instanceof LivingEntity) {
                LivingEntity entity = (LivingEntity) event.getEntity();
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40,2));
            }
        }
    }

    @Override
    public String getName() {
        return "[神剑之舞SSR]";
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public boolean isOnly() {
        return true;
    }

    @Override
    public void applyEffect(EntityDamageByEntityEvent event) {
        DanceSword(getLevel(), event);
    }

    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "有25%的几率进行追加100%的伤害");
    }

    @Override
    public int getChance() {
        return 1;
    }
}
