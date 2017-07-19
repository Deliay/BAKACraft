package bakacraft.WeaponSkills.BowEffects;

import bakacraft.WeaponSkills.BaseSkillBow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by admin on 2017/7/14.
 */
public class PushArrowN extends BaseSkillBow {
    public final static void PushArrow(int level, EntityDamageEvent event, LivingEntity by) {
        double dest = by.getLocation().distance(event.getEntity().getLocation());
        double max_dest = 20 + level * 5;
        double dmgMul = 1;
        double src_dmg = event.getDamage();
        if(dest > max_dest)
        {
            dmgMul = 0.3;
            dest = 20;
        }

        by.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int) (4 - level * 0.5) * 20, 2));
        event.setDamage(dmgMul * (src_dmg + dest * src_dmg *(0.02 + level * 0.1)));
    }

    @Override
    public String getName() {
        return "推进之失N";
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
        PushArrowN.PushArrow(getLevel(), event, by);
    }

    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "按距离结算伤害，1个单位增加2%的伤害\n" +
                "自身增加缓慢3效果4秒\n" +
                "与目标距离超过20个单位时，不再计算单位，伤害减少70%");
    }

    @Override
    public int getChance() {
        return 60;
    }
}
