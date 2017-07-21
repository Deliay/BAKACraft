package bakacraft.WeaponSkills.SwordEffects;

import bakacraft.WeaponSkills.BaseSkill;
import bakacraft.WeaponSkills.Random;
import bakacraft.WeaponSkills.Sword;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by admin on 2017/7/8.
 */
public class SwordDanceN extends BaseSkill {
    public final static void SwordDance(int level, EntityDamageByEntityEvent event) {
        int count = 0;
        //有20%/25%/30%/35%的几率追加10%伤害，判定成功后，会再次执行追加判定
        Sword.injugdge.put((LivingEntity) event.getDamager(), null);
        boolean ssr = level == 3;
        int chance = 20 + level * 5;
        double dmg = event.getDamage();
        while (Random.TestRandom(chance) && count <= (level + 4)) {
            count++;
            dmg += (dmg * 0.1 * count) - 1;
            ((LivingEntity) event.getEntity()).damage(1, null);
            ((Player) event.getDamager()).sendRawMessage("幻影剑舞！附加伤害：" + String.format("%.2f", dmg));

        }
        if (ssr) {
            count = 0;
            while (Random.TestRandom(chance) && count <= (level + 4)) {
                count++;
                dmg += (dmg * 0.1 * count) - 1;
                ((LivingEntity) event.getEntity()).damage(1, null);
                ((Player) event.getDamager()).sendRawMessage("幻影剑舞！附加伤害：" + String.format("%.2f", dmg));
            }
        }
        event.setDamage(dmg);
        Sword.injugdge.remove(event.getDamager());
    }

    @Override
    public String getName() {
        return "[幻影剑舞N]";
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
        SwordDanceN.SwordDance(getLevel(), event);
    }

    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "有20%的几率追加10%伤害，判定成功后，会再次执行追加判定");
    }

    @Override
    public int getChance() {
        return 60;
    }
}
