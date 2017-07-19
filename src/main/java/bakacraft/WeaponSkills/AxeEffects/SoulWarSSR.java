package bakacraft.WeaponSkills.AxeEffects;

import bakacraft.WeaponSkills.BaseSkill;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by admin on 2017/7/7.
 */
public class SoulWarSSR extends BaseSkill {

    @Override
    public String getName() {
        return "战之炽魂SSR";
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
        SoulWarN.SoulWar(getLevel(), event);
    }

    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "有35%的几率点燃目标5秒。\n" +
                "攻击点燃目标附加35%的伤害。\n" +
                "在攻击点燃目标时，总是触发10%的吸血。");

    }

    @Override
    public int getChance() {
        return 2;
    }
}
