package bakacraft.WeaponSkills.AxeEffects;

import bakacraft.WeaponSkills.BaseSkill;
import bakacraft.WeaponSkills.Random;
import org.bukkit.EntityEffect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by admin on 2017/7/7.
 */
public class RotatedAxeN extends BaseSkill {
    public final static void RotateDamage(int level, EntityDamageByEntityEvent event) {
        if (Random.TestRandom(30)) {
            Player p = (Player) event.getDamager();
            List<Entity> nears = event.getEntity().getNearbyEntities(3, 3, 3);
            double damage = event.getDamage() * 0.1;
            switch (level) {
                case 3:
                    damage += damage * 0.05;
                case 2:
                    damage += damage * 0.05;
                case 1:
                    damage += damage * 0.05;
            }

            damage += 1;
            for (Entity t : nears) {
                if (t == p) break;
                else if (t instanceof LivingEntity) {
                    LivingEntity e = (LivingEntity) t;
                    e.damage(damage, p);
                    e.playEffect(EntityEffect.HURT);
                    if (level > 1 && e instanceof Player) {
                        Player dmp = (Player) e;
                        dmp.setFoodLevel(dmp.getFoodLevel() - level + 1);
                    }
                }
            }
        }
    }

    @Override
    public String getName() {
        return "旋风飞斧N";
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public boolean isOnly() {
        return false;
    }

    @Override
    public void applyEffect(EntityDamageByEntityEvent event) {

        RotateDamage(getLevel(), event);

    }

    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "有30%的几率造成1+本次攻击伤害10%范围群体伤害 (3*3)");
    }

    @Override
    public int getChance() {
        return 50;
    }
}
