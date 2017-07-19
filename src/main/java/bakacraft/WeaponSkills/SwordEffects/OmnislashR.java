package bakacraft.WeaponSkills.SwordEffects;

import bakacraft.WeaponSkills.BaseSkill;
import bakacraft.WeaponSkills.Sword;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by admin on 2017/7/8.
 */
public class OmnislashR extends BaseSkill {

    public static final void Omnislash(int level, EntityDamageByEntityEvent event) {
        Sword.injugdge.put((LivingEntity) event.getDamager(), null);

        double dmg = event.getDamage();
        for (int i = 0; i < 2 * level; i++) {
            dmg += 0.5;
            ((LivingEntity) event.getEntity()).damage(dmg, event.getDamager());
        }

        Sword.injugdge.remove(event.getDamager());

    }

    @Override
    public String getName() {
        return "[无敌斩R]";
    }

    @Override
    public int getLevel() {
        return 1;
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
        BurnDescription(this, weapon, "瞬间打出3次等效攻击的基础上每次附加0.5点伤害");
    }

    @Override
    public int getChance() {
        return 20;
    }
}
