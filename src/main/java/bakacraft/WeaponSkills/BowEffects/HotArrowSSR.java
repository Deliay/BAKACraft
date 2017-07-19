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
public class HotArrowSSR extends BaseSkillBow {
    public final static void HotArrow(int level, EntityDamageEvent event, LivingEntity by) {
        if (Random.TestRandom(20)) {
            event.setDamage(event.getDamage() * 1.75);
            if (event.getEntity() instanceof LivingEntity) {
                LivingEntity entity = (LivingEntity) event.getEntity();
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 10));
            }
        }
    }

    @Override
    public String getName() {
        return "灼热之箭SSR";
    }

    @Override
    public int getLevel() {
        return 3;
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
        HotArrow(getLevel(), event, by);
    }

    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "有20%的几率进行追加75%的伤害，并禁锢目标1秒");
    }

    @Override
    public int getChance() {
        return 1;
    }
}
