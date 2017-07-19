package bakacraft.WeaponSkills.SwordEffects;

import bakacraft.WeaponSkills.BaseSkill;
import bakacraft.WeaponSkills.Random;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

/**
 * Created by admin on 2017/7/8.
 */
public class StormSwordN extends BaseSkill {
    public final static void StormSword(int level, EntityDamageByEntityEvent event) {
        if (!Random.TestRandom(20 + level * 5)) return;

        List<Entity> nears = event.getDamager().getNearbyEntities(3, 3, 3);
        double dmg = event.getDamage() * (0.3 + (0.1 * level));
        for (Entity entity : nears) {
            if (entity == event.getDamager()) break;

            if (entity instanceof LivingEntity) {
                LivingEntity le = (LivingEntity) entity;
                le.damage(dmg);
            }
        }

        if (level == 3) {
            ((LivingEntity) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20, 1), true);
        }

    }

    @Override
    public String getName() {
        return "[剑刃风暴N]";
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
        StormSwordN.StormSword(getLevel(), event);
    }

    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "20%的几率产生附加基础攻击30%的3X3范围攻击");
    }

    @Override
    public int getChance() {
        return 60;
    }
}
