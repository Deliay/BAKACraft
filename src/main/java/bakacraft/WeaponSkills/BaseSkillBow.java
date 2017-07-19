package bakacraft.WeaponSkills;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by admin on 2017/7/7.
 */
public abstract class BaseSkillBow extends BaseSkill {
    public abstract void applyEffect(EntityDamageEvent event, LivingEntity by);

}
