package bakacraft.WeaponSkills.SwordEffects;

import bakacraft.WeaponSkills.BaseSkill;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by admin on 2017/7/8.
 */
public class ArdorArtistrySSR extends BaseSkill {
    @Override
    public String getName() {
        return "[灼热刀法SSR]";
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
        ArdorArtistryN.ArdorArtistry(getLevel(), event);
    }

    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "50%的几率，自身着火4秒，并追加90%伤害。\n判定落空时，有50%的几率会追加一次判定");
    }

    @Override
    public int getChance() {
        return 1;
    }
}
