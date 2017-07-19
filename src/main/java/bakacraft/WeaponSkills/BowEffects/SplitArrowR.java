package bakacraft.WeaponSkills.BowEffects;

import bakacraft.BAKACraft;
import bakacraft.WeaponSkills.BaseSkillBow;
import bakacraft.WeaponSkills.Bow;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * Created by admin on 2017/7/13.
 */
public class SplitArrowR extends BaseSkillBow {

    public static final void SplitArrow(int level, EntityDamageEvent event, LivingEntity by) {

        int arrownum = 3 + level * 2;
        double dmgMul = 0.75 + level * 0.02;
        Bow.injugdge.put(by, by.getEquipment().getItemInHand());
        Vector vec = new Vector();
        if(event instanceof EntityDamageByEntityEvent)
        {
            EntityDamageByEntityEvent evt = (EntityDamageByEntityEvent) event;
            vec.copy(evt.getDamager().getVelocity());
        }

        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();

            vec.add(new Vector(0,0,1));
            final LivingEntity src = by;
            for (int i = 0; i < arrownum; i++) {
                (new BukkitRunnable(){
                    @Override
                    public void run() {
                        Arrow a = src.launchProjectile(Arrow.class);

                        try {wait(1);}
                        catch (Exception e){}

                        a.remove();
                    }
                }).runTask(BAKACraft.instance);

            }
            event.setDamage((event.getDamage() * (0.85 + 0.02 * level)) * 3);

        }
        Bow.injugdge.remove(by);
    }

    @Override
    public String getName() {
        return "分裂箭R";
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
    }

    @Override
    public void applyEffect(EntityDamageEvent event, LivingEntity by) {
        SplitArrowR.SplitArrow(getLevel(), event, by);
    }

    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "对目标连续射出3只箭矢，伤害降低15%");
    }

    @Override
    public int getChance() {
        return 20;
    }
}
