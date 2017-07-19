package bakacraft.WeaponSkills.BowEffects;

import bakacraft.WeaponSkills.BaseSkillBow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by admin on 2017/7/14.
 */
public class PushArrowSSR extends BaseSkillBow {
    @Override
    public String getName() {
        return "推进之失SSR";
    }

    @Override
    public int getLevel() {
        return 3;
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
        BurnDescription(this, weapon, "按距离结算伤害，1个单位增加4%的伤害\n" +
                "自身增加缓慢2效果4秒\n" +
                "与目标距离超过35个单位时，不再计算单位，伤害减少70%");
    }

    @Override
    public int getChance() {
        return 1;
    }
}
