package bakacraft.WeaponSkills.SwordEffects;

import bakacraft.WeaponSkills.BaseSkill;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by admin on 2017/7/8.
 */
public class OmnislashSR extends BaseSkill {

    @Override
    public String getName() {
        return "[无敌斩SR]";
    }

    @Override
    public int getLevel() {
        return 2;
    }

    @Override
    public boolean isOnly() {
        return true;
    }

    @Override
    public void applyEffect(EntityDamageByEntityEvent event) {
        OmnislashR.Omnislash(getLevel(), event);
    }

    @Override
    public void burnToWeapon(ItemStack weapon) {

    }

    @Override
    public int getChance() {
        return 4;
    }
}
