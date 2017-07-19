package bakacraft.WeaponSkills.BowEffects;

import bakacraft.WeaponSkills.BaseSkillBow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by admin on 2017/7/14.
 */
public class ForzenSR extends BaseSkillBow {

    @Override
    public String getName() {
        return "极寒之箭N";
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
        ForzenN.Forzen(getLevel(), event, by);
    }

    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "50%的几率移除目标14%当前生命\n" +
                "50%的几率目标缓慢3秒\n" +
                "攻击被点燃的目标减少30%的伤害");
    }

    @Override
    public int getChance() {
        return 4;
    }
}
