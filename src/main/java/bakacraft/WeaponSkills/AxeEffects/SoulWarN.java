package bakacraft.WeaponSkills.AxeEffects;

import bakacraft.WeaponSkills.BaseSkill;
import bakacraft.WeaponSkills.Random;
import org.bukkit.EntityEffect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by admin on 2017/7/7.
 */
public class    SoulWarN extends BaseSkill {

    public static final void SoulWar(int level, EntityDamageByEntityEvent event) {
        if (Random.TestRandom(10 + level * 5)) {
            if (event.getEntity() instanceof LivingEntity) {
                LivingEntity entity = (LivingEntity) event.getEntity();
                entity.setFireTicks((2 + level) * 20);
            }
        }

        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            if (entity.getFireTicks() > 0) {
                event.setDamage(event.getDamage() + event.getDamage() * (0.1 + (level * 0.5)));
                event.getEntity().playEffect(EntityEffect.WOLF_HEARTS);
                if (level >= 2) {
                    if (Random.TestRandom(25)) {
                        LivingEntity ent = (LivingEntity) event.getDamager();
                        ent.setHealth(ent.getHealth() + 0.1 * event.getDamage());
                    }
                }
            }
        }
    }

    @Override
    public String getName() {
        return "战之炽魂N";
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
        SoulWarN.SoulWar(getLevel(), event);
    }

    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "有20%的几率点燃目标2秒。\n" +
                "攻击点燃目标附加20%的伤害。");

    }

    @Override
    public int getChance() {
        return 50;
    }
}
