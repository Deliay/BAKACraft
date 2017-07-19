package bakacraft.WeaponSkills.SwordEffects;

import bakacraft.WeaponSkills.BaseSkill;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by admin on 2017/7/8.
 */
public class ArdorArtistryR extends BaseSkill {
    @Override
    public String getName() {
        return "[灼热刀法R]";
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
        ArdorArtistryN.ArdorArtistry(getLevel(), event);
    }

    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "50%的几率，自身着火4秒，并追加70%伤害");
    }

    @Override
    public int getChance() {
        return 20;
    }
}
