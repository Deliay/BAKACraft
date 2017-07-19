package bakacraft.WeaponSkills.AxeEffects;

import bakacraft.WeaponSkills.BaseSkill;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by admin on 2017/7/7.
 */
public class CullingBladeR extends BaseSkill {

    @Override
    public String getName() {
        return "淘汰之刃R";
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
        CullingBladeN.Culling(getLevel(), event);
    }

    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "有30%的几率对4点生命值以下的生物进行秒杀。\n" +
                "如果大于5点，造成武器伤害35%伤害。");

    }

    @Override
    public int getChance() {
        return 25;
    }
}
