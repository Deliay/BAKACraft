package bakacraft.WeaponSkills.BowEffects;

import bakacraft.WeaponSkills.BaseSkillBow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by admin on 2017/7/14.
 */
public class ForzenR extends BaseSkillBow {

    @Override
    public String getName() {
        return "极寒之箭R";
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
    }

    @Override
    public void applyEffect(EntityDamageEvent event, LivingEntity by) {
        ForzenN.Forzen(getLevel(), event, by);
    }


    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "50%的几率移除目标12%当前生命\n" +
                "50%的几率目标缓慢2秒\n" +
                "攻击被点燃的目标减少35%的伤害");
    }

    @Override
    public int getChance() {
        return 20;
    }
}
