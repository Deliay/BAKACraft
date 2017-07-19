package bakacraft.WeaponSkills.AxeEffects;

import bakacraft.WeaponSkills.BaseSkill;
import bakacraft.WeaponSkills.Random;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by admin on 2017/7/7.
 */
public class CullingBladeN extends BaseSkill {

    public static final void Culling(int level, EntityDamageByEntityEvent event) {

        if (Random.TestRandom(30)) {
            if (event.getEntity() instanceof LivingEntity) {
                LivingEntity entity = (LivingEntity) event.getEntity();
                if (entity.getHealth() < level + 3) {
                    entity.damage(99999, event.getDamager());
                } else {
                    entity.damage(event.getDamage() * (0.3 + level * 0.05), event.getDamager());
                }
            }
        }
    }

    @Override
    public String getName() {
        return "淘汰之刃N";
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
        CullingBladeN.Culling(getLevel(), event);
    }

    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "有30%的几率对3点生命值以下的生物进行秒杀。\n" +
                "如果大于5点，造成武器伤害30%的伤害。");

    }

    @Override
    public int getChance() {
        return 50;
    }
}
