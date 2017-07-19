package bakacraft.WeaponSkills;

import bakacraft.BAKACraft;
import bakacraft.WeaponSkills.AxeEffects.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Axe implements Listener {
    //斧头类技能的实现
    public final static BeatHeavenR BEAT_H_R = new BeatHeavenR();                       //20%
    public final static BeatHeavenSR BEAT_H_SR = new BeatHeavenSR();                    //4%
    public final static BeatHeavenSSR BEAT_H_SSR = new BeatHeavenSSR();                 //1%
    public final static CullingBladeN CULLING_BLADE_N = new CullingBladeN();            //65%
    public final static CullingBladeR CULLING_BLADE_R = new CullingBladeR();            //20%
    public final static CullingBladeSR CULLING_BLADE_SR = new CullingBladeSR();          //4%
    public final static CullingBladeSSR CULLING_BLADE_SSR = new CullingBladeSSR();      //1%
    public final static RedWarFogSSR RED_WAR_FOG_SSR = new RedWarFogSSR();              //1%
    public final static RotatedAxeN ROTATED_AXE_N = new RotatedAxeN();                  //65%
    public final static RotatedAxeR ROTATED_AXE_R = new RotatedAxeR();                  //20%
    public final static RotatedAxeSR ROTATED_AXE_SR = new RotatedAxeSR();               //4%
    public final static RotatedAxeSSR ROTATED_AXE_SSR = new RotatedAxeSSR();            //1%
    public final static SoulWarN SOUL_WAR_N = new SoulWarN();                           //65%
    public final static SoulWarR SOUL_WAR_R = new SoulWarR();                           //20%
    public final static SoulWarSR SOUL_WAR_SR = new SoulWarSR();                        //4%
    public final static SoulWarSSR SOUL_WAR_SSR = new SoulWarSSR();                     //1%

    public final static BaseSkill[] 开山之斧N = new BaseSkill[]{CULLING_BLADE_N};
    public final static BaseSkill[] 开山之斧R = new BaseSkill[]{CULLING_BLADE_R, ROTATED_AXE_N};
    public final static BaseSkill[] 开山之斧SR = new BaseSkill[]{CULLING_BLADE_SR, ROTATED_AXE_R, BEAT_H_R};
    public final static BaseSkill[] 开山之斧SSR = new BaseSkill[]{CULLING_BLADE_SSR, ROTATED_AXE_SR, BEAT_H_SR};
    public final static BaseSkill[][] 开山之斧 = new BaseSkill[][]{开山之斧N, 开山之斧R, 开山之斧SR, 开山之斧SSR};

    public final static BaseSkill[] 魔龙战斧N = new BaseSkill[]{SOUL_WAR_N, ROTATED_AXE_N};
    public final static BaseSkill[] 魔龙战斧R = new BaseSkill[]{SOUL_WAR_R, ROTATED_AXE_N, BEAT_H_R};
    public final static BaseSkill[] 魔龙战斧SR = new BaseSkill[]{SOUL_WAR_SR, ROTATED_AXE_R, BEAT_H_R};
    public final static BaseSkill[] 魔龙战斧SSR = new BaseSkill[]{SOUL_WAR_SSR, ROTATED_AXE_SR, BEAT_H_SR};
    public final static BaseSkill[][] 魔龙战斧 = new BaseSkill[][]{魔龙战斧N, 魔龙战斧R, 魔龙战斧SR, 魔龙战斧SSR};

    public final static BaseSkill[] 无上战魁之斧SR = new BaseSkill[]{CULLING_BLADE_SR, RED_WAR_FOG_SSR};
    public final static BaseSkill[] 无上战魁之斧SSR = new BaseSkill[]{CULLING_BLADE_SR, RED_WAR_FOG_SSR, SOUL_WAR_SR};
    public final static BaseSkill[][] 无上战魁之斧 = new BaseSkill[][]{无上战魁之斧SR, 无上战魁之斧SSR, 无上战魁之斧SR, 无上战魁之斧SSR};

    //斧头的技能池
    public final static List<BaseSkill> skills = new ArrayList<>();

    public Axe() {
        for (Field k : this.getClass().getFields()) {
            try {
                if (k.get(this) instanceof BaseSkill)
                    skills.add((BaseSkill) k.get(this));
            } catch (Exception e) {

            }
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if(!BAKACraft.CHECK_PLAYER_PERM(event.getDamager(), BAKACraft.PERM_BASAKER))
        {
            return;
        }
        if (event.getDamager() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getDamager();
            ItemStack weapon = entity.getEquipment().getItemInHand();
            if (weapon != null) {
                if (weapon.hasItemMeta() && weapon.getItemMeta().hasLore()) {
                    List<String> lores = weapon.getItemMeta().getLore();

                    for (String lore : lores) {
                        for (BaseSkill skill : skills) {
                            if (lore.contains(skill.getName())) {
                                skill.applyEffect(event);
                            }
                        }
                    }
                }
            }

            if(entity instanceof Player)
            {
                BAKACraft.TIPS_BOARD.showInfo((Player)event.getDamager(), "伤害", (int)event.getDamage());
            }
        }
    }

    //随机抽取技能
    public BaseSkill RandomGet(int maxLevel) {
        int max = 1;
        for (BaseSkill skill : skills) {
            if (maxLevel >= skill.getLevel() && !skill.isOnly()) max += skill.getChance();
        }
        int result = Random.RandomRange(max);
        max = 1;
        for (BaseSkill skill : skills) {
            if (maxLevel >= skill.getLevel() && !skill.isOnly()) max += skill.getChance();
            if (max >= result) {
                return skill;
            }
        }
        return null;
    }

    public BaseSkill RandomGetSpec(int specLevel) {
        int max = 1;
        for (BaseSkill skill : skills) {
            if (specLevel == skill.getLevel() && !skill.isOnly()) max += skill.getChance();
        }
        int result = Random.RandomRange(max);
        max = 1;
        for (BaseSkill skill : skills) {
            if (specLevel == skill.getLevel() && !skill.isOnly()) max += skill.getChance();
            if (max >= result) {
                return skill;
            }
        }
        return null;
    }


    public BaseSkill RandomGetSpec(int maxLevel, int minLevel) {
        int max = 1;
        for (BaseSkill skill : skills) {
            if (maxLevel >= skill.getLevel() && minLevel <= skill.getLevel() && !skill.isOnly())
                max += skill.getChance();
        }
        int result = Random.RandomRange(max);
        max = 1;
        for (BaseSkill skill : skills) {
            if (maxLevel >= skill.getLevel() && minLevel <= skill.getLevel() && !skill.isOnly())
                max += skill.getChance();
            if (max >= result) {
                return skill;
            }
        }
        return null;
    }


    private int getLevelByPercent(int percent) {
        if (percent == 1) return 3;
        else if (percent > 1 && percent <= 5) return 2;
        else if (percent > 5 && percent <= 25) return 1;
        else if (percent > 25 && percent <= 40) return -1;
        else return 0;
    }

    private String getColorByLevel(int level) {
        switch (level) {
            case 0:
                return Random.Colorilize("&5");
            case 1:
                return Random.Colorilize("&9");
            case 2:
                return Random.Colorilize("&B");
            case 3:
                return Random.Colorilize("&6");
            default:
                return "";
        }
    }

    private String getLevelFlagByLevel(int level) {
        switch (level) {
            case 0:
                return Random.Colorilize("&5N");
            case 1:
                return Random.Colorilize("&9R");
            case 2:
                return Random.Colorilize("&BSR");
            case 3:
                return Random.Colorilize("&6SSR");
            default:
                return "";
        }
    }

    public void applyLores(BaseSkill[][] skills, int level, ItemStack item) {
        applyLores(skills[level], item);

    }

    public void applyLores(BaseSkill[] skills, ItemStack item) {
        for (BaseSkill skill : skills) {
            skill.burnToWeapon(item);
        }

    }

    public ItemStack UnlockLore(ItemStack stack) {
        //先抽一发，判断品级
        //SSR   1%
        // SR   4%
        //  R   20%
        //FAIL  15%     75%装备耐久消耗一半，25%装备消耗剩余至1点
        //  U   65%
        int level = getLevelByPercent(Random.RandomRange(100));
        //再抽一发，判断武器
        int item = Random.RandomRange(100);
        boolean isCustom = false;
        BaseSkill[][] target = null;
        List<BaseSkill> skills = null;
        int itemId = 4996;
        String name = null;
        if (item <= 14) {
            target = 开山之斧;
            name = "&B开山之斧";
            itemId = 4995;
        } else if (item > 14 && item <= 28) {
            target = 魔龙战斧;
            name = "&B魔龙战斧";
            itemId = 5001;
        } else if (item == 29) {
            if(level == 0) level = 3;
            else if(level == 1) level = 4;
            target = 无上战魁之斧;
            name = "&E无上战魁之斧";
            itemId = 5002;
        } else {
            name = "&5红雾战斧:";
            isCustom = true;
            skills = new ArrayList<>();
            //U
            if (level == 0) {
                //保底N技能
                skills.add(RandomGet(0));
                if (Random.TestRandom(50)) {
                    skills.add(RandomGet(0));
                }
            } else if (level == 1) {
                //R+N |  50% R+N
                skills.add(RandomGet(1));
                if (Random.TestRandom(50)) {
                    skills.add(RandomGet(1));
                }
            } else if (level == 2) {
                //SR+R |  50% SR+R+N
                skills.add(RandomGetSpec(2, 1));
                if (Random.TestRandom(50)) {
                    skills.add(RandomGet(2));
                }
            } else if (level == 3) {
                //SR+R |  50% SR+R+N
                skills.add(RandomGetSpec(3, 2));
                if (Random.TestRandom(50)) {
                    skills.add(RandomGet(3));
                }
            }
            name += skills.get(0).getName();
        }

        if (level != -1) {
            stack = new ItemStack(itemId, 1);
            name += getLevelFlagByLevel(level);
            if (isCustom) {
                applyLores(skills.toArray(new BaseSkill[0]), stack);
            } else applyLores(target, level, stack);

            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(Random.Colorilize(name));
            stack.setItemMeta(meta);
            stack.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        }
        //炸了
        else {
            if (Random.TestRandom(75)) {
                stack.setDurability((short) (stack.getDurability() / 2));
            } else {
                stack.setDurability((short) 1);
            }
        }

        return stack;
    }
}
