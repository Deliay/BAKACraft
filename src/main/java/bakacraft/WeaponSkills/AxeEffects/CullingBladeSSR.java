package bakacraft.WeaponSkills.AxeEffects;

import bakacraft.WeaponSkills.BaseSkill;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by admin on 2017/7/7.
 */
public class CullingBladeSSR extends BaseSkill {

    @Override
    public String getName() {
        return "淘汰之刃SSR";
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
        CullingBladeN.Culling(getLevel(), event);
    }

    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "有30%的几率对5点生命值以下的生物进行秒杀。\n" +
                "如果大于5点，造成武器伤害45%伤害。");

    }

    @Override
    public int getChance() {
        return 2;
    }
}
