package bakacraft.WeaponSkills.AxeEffects;

import bakacraft.WeaponSkills.BaseSkill;
import bakacraft.WeaponSkills.Random;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by admin on 2017/7/7.
 */
public class RedWarFogSSR extends BaseSkill {

    @Override
    public String getName() {
        return "战争红雾SSR";
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
        if (Random.TestRandom(50)) {
            double dmg = event.getDamage() * 0.5;
            event.setDamage(event.getDamage() + dmg);
            LivingEntity le = ((LivingEntity) event.getDamager());
        }
    }

    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "有50%的几率进行追加50%的伤害");

    }

    @Override
    public int getChance() {
        return 1;
    }
}
