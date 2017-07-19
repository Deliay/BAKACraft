package bakacraft.WeaponSkills;

import bakacraft.BAKACraft;
import bakacraft.WeaponSkills.SwordEffects.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sword implements Listener {
    //斧头类技能的实现
    public final static ArdorArtistryN ARDOR_ARTISTRY_N = new ArdorArtistryN();
    public final static ArdorArtistryR ARDOR_ARTISTRY_R = new ArdorArtistryR();
    public final static ArdorArtistrySR ARDOR_ARTISTRY_SR = new ArdorArtistrySR();
    public final static ArdorArtistrySSR ARDOR_ARTISTRY_SSR = new ArdorArtistrySSR();
    public final static DanceSwordSSR DANCE_SWORD_SSR = new DanceSwordSSR();

    public final static OmnislashR OMNISLASH_R = new OmnislashR();
    public final static OmnislashSR OMNISLASH_SR = new OmnislashSR();
    public final static OmnislashSSR OMNISLASH_SSR = new OmnislashSSR();

    public final static StormSwordN STORM_SWORD_N = new StormSwordN();
    public final static StormSwordR STORM_SWORD_R = new StormSwordR();
    public final static StormSwordSR STORM_SWORD_SR = new StormSwordSR();
    public final static StormSwordSSR STORM_SWORD_SSR = new StormSwordSSR();

    public final static SwordDanceN SWORD_DANCE_N = new SwordDanceN();
    public final static SwordDanceR SWORD_DANCE_R = new SwordDanceR();
    public final static SwordDanceSR SWORD_DANCE_SR = new SwordDanceSR();
    public final static SwordDanceSSR SWORD_DANCE_SSR = new SwordDanceSSR();

    public final static BaseSkill[] 剑道雅客长剑N = new BaseSkill[]{ARDOR_ARTISTRY_N};
    public final static BaseSkill[] 剑道雅客长剑R = new BaseSkill[]{ARDOR_ARTISTRY_R, STORM_SWORD_N};
    public final static BaseSkill[] 剑道雅客长剑SR = new BaseSkill[]{ARDOR_ARTISTRY_SR, STORM_SWORD_R, SWORD_DANCE_N};
    public final static BaseSkill[] 剑道雅客长剑SSR = new BaseSkill[]{ARDOR_ARTISTRY_SSR, STORM_SWORD_SR, SWORD_DANCE_R};
    public final static BaseSkill[][] 剑道雅客长剑 = new BaseSkill[][]{剑道雅客长剑N, 剑道雅客长剑R, 剑道雅客长剑SR, 剑道雅客长剑SSR};

    public final static BaseSkill[] 古卷之剑坎图沙N = new BaseSkill[]{OMNISLASH_R};
    public final static BaseSkill[] 古卷之剑坎图沙R = new BaseSkill[]{OMNISLASH_R, SWORD_DANCE_R};
    public final static BaseSkill[] 古卷之剑坎图沙SR = new BaseSkill[]{OMNISLASH_SR, SWORD_DANCE_R};
    public final static BaseSkill[] 古卷之剑坎图沙SSR = new BaseSkill[]{OMNISLASH_SSR, SWORD_DANCE_SR};
    public final static BaseSkill[][] 古卷之剑坎图沙 = new BaseSkill[][]{古卷之剑坎图沙N, 古卷之剑坎图沙R, 古卷之剑坎图沙SR, 古卷之剑坎图沙SSR};

    public final static BaseSkill[] 无穷神力之刃SR = new BaseSkill[]{DANCE_SWORD_SSR, SWORD_DANCE_R};
    public final static BaseSkill[] 无穷神力之刃SSR = new BaseSkill[]{DANCE_SWORD_SSR, SWORD_DANCE_SR};
    public final static BaseSkill[][] 无穷神力之刃 = new BaseSkill[][]{无穷神力之刃SR, 无穷神力之刃SSR, 无穷神力之刃SR, 无穷神力之刃SSR};


    //的技能池
    public final static List<BaseSkill> skills = new ArrayList<>();

    public final static Map<LivingEntity, ItemStack> injugdge = new HashMap<>();

    public Sword() {
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
        if(!BAKACraft.CHECK_PLAYER_PERM(event.getDamager(), BAKACraft.PERM_SABER))
        {
            return;
        }
        if (event.getDamager() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getDamager();
            if (injugdge.containsKey(entity)) return;

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
                    BAKACraft.TIPS_BOARD.showInfo((Player)event.getDamager(), "伤害", (int)event.getDamage());
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
        int itemId = 5084;
        String name = null;
        if (item <= 14) {
            target = 剑道雅客长剑;
            name = "&B剑道雅客长剑";
            itemId = 4917;
        } else if (item > 14 && item <= 28) {
            target = 古卷之剑坎图沙;
            name = "&B古卷之剑坎图沙";
            itemId = 4932;
        } else if (item == 29) {
            if(level == 0) level = 3;
            else if(level == 1) level = 4;
            target = 无穷神力之刃;
            name = "&E无穷神力之刃";
            itemId = 4942;
        } else {
            name = "&5 炎铸大太刀:";
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
