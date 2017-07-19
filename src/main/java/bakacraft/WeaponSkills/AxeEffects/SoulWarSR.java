package bakacraft.WeaponSkills.AxeEffects;

import bakacraft.WeaponSkills.BaseSkill;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by admin on 2017/7/7.
 */
public class SoulWarSR extends BaseSkill {

    @Override
    public String getName() {
        return "战之炽魂SR";
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
        SoulWarN.SoulWar(getLevel(), event);
    }

    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "有30%的几率点燃目标4秒。\n" +
                "攻击点燃目标附加30%的伤害。\n" +
                "并有50%的几率触发10%的吸血。");

    }

    @Override
    public int getChance() {
        return 7;
    }
}
