package bakacraft.WeaponSkills.SwordEffects;

import bakacraft.WeaponSkills.BaseSkill;
import bakacraft.WeaponSkills.Random;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by admin on 2017/7/8.
 */
public class ArdorArtistryN extends BaseSkill {

    public final static void setHelth(Player p, double h)
    {
        try {
            p.setHealth(h);
        }  catch (Exception e)
        {

        }
    }

    public final static void ArdorArtistry(int level, EntityDamageByEntityEvent event) {
        double lifesteal = 0.06 + level * 0.01;
        boolean ssr = level == 3;
        if (Random.TestRandom(50)) {
            Player p = (Player) event.getDamager();
            p.setFireTicks(80);
            event.setDamage(event.getDamage() * (1.6 + level * 0.1));
            setHelth(p, p.getHealth() + event.getDamage() * lifesteal);
        }
        if (ssr) {
            if (Random.TestRandom(50)) {
                Player p = (Player) event.getDamager();
                p.setFireTicks(80);
                event.setDamage(event.getDamage() * (1.6 + level * 0.1));
                setHelth(p, p.getHealth() + event.getDamage() * lifesteal);
            }
        }

    }

    @Override
    public String getName() {
        return "[灼热刀法N]";
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
        ArdorArtistry(getLevel(), event);
    }

    @Override
    public void burnToWeapon(ItemStack weapon) {
        BurnDescription(this, weapon, "50%的几率，自身着火4秒，并追加60%伤害");
    }

    @Override
    public int getChance() {
        return 60;
    }
}
