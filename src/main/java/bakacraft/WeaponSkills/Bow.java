package bakacraft.WeaponSkills;

import bakacraft.BAKACraft;
import bakacraft.WeaponSkills.BowEffects.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.*;

public class Bow implements Listener {
    //斧头类技能的实现
    public final static ForceN FORCE_N = new ForceN();
    public final static ForceR FORCE_R = new ForceR();
    public final static ForceSR FORCE_SR = new ForceSR();
    public final static ForceSSR FORCE_SSR = new ForceSSR();
    public final static ForzenN FORZEN_N = new ForzenN();
    public final static ForzenR FORZEN_R = new ForzenR();
    public final static ForzenSR FORZEN_SR = new ForzenSR();
    public final static ForzenSSR FORZEN_SSR = new ForzenSSR();
    public final static HotArrowSSR HOT_ARROW_SSR = new HotArrowSSR();
    public final static PushArrowN PUSH_ARROW_N = new PushArrowN();
    public final static PushArrowR PUSH_ARROW_R = new PushArrowR();
    public final static PushArrowSR PUSH_ARROW_SR = new PushArrowSR();
    public final static PushArrowSSR PUSH_ARROW_SSR = new PushArrowSSR();
    public final static SplitArrowR SPLIT_ARROW_R = new SplitArrowR();
    public final static SplitArrowSR SPLIT_ARROW_SR = new SplitArrowSR();
    public final static SplitArrowSSR SPLIT_ARROW_SSR = new SplitArrowSSR();

    public final static BaseSkillBow[] 霜之獠牙长弓N = new BaseSkillBow[]{FORZEN_N};
    public final static BaseSkillBow[] 霜之獠牙长弓R = new BaseSkillBow[]{SPLIT_ARROW_R, FORZEN_R};
    public final static BaseSkillBow[] 霜之獠牙长弓SR = new BaseSkillBow[]{SPLIT_ARROW_R, FORZEN_SR};
    public final static BaseSkillBow[] 霜之獠牙长弓SSR = new BaseSkillBow[]{SPLIT_ARROW_R, FORZEN_SSR};
    public final static BaseSkillBow[][] 霜之獠牙长弓 = new BaseSkillBow[][]{霜之獠牙长弓N, 霜之獠牙长弓R, 霜之獠牙长弓SR, 霜之獠牙长弓SSR};

    public final static BaseSkillBow[] 呼啸狂风之弓N = new BaseSkillBow[]{FORCE_N};
    public final static BaseSkillBow[] 呼啸狂风之弓R = new BaseSkillBow[]{FORCE_N, FORZEN_N};
    public final static BaseSkillBow[] 呼啸狂风之弓SR = new BaseSkillBow[]{FORCE_R, FORZEN_R};
    public final static BaseSkillBow[] 呼啸狂风之弓SSR = new BaseSkillBow[]{FORCE_SSR, FORZEN_R};
    public final static BaseSkillBow[][] 呼啸狂风之弓 = new BaseSkillBow[][]{呼啸狂风之弓N, 呼啸狂风之弓R, 呼啸狂风之弓SR, 呼啸狂风之弓SSR};

    public final static BaseSkillBow[] 霸王蝶SR = new BaseSkillBow[]{HOT_ARROW_SSR, FORCE_SR};
    public final static BaseSkillBow[] 霸王蝶SSR = new BaseSkillBow[]{HOT_ARROW_SSR, FORCE_SSR};
    public final static BaseSkillBow[][] 霸王蝶 = new BaseSkillBow[][]{霸王蝶SR, 霸王蝶SSR, 霸王蝶SR, 霸王蝶SSR};


    //的技能池
    public final static List<BaseSkillBow> skills = new ArrayList<>();

    public final static Map<LivingEntity, ItemStack> injugdge = new HashMap<>();

    public Bow() {
        for (Field k : this.getClass().getFields()) {
            try {
                if (k.get(this) instanceof BaseSkillBow)
                    skills.add((BaseSkillBow) k.get(this));
            } catch (Exception e) {

            }
        }
    }


    public class MetaDataPlayer implements MetadataValue {

        LivingEntity p;
        Plugin pg;
        public MetaDataPlayer(Plugin plugin, LivingEntity player)
        {
            p = player;
            pg = plugin;
        }

        public LivingEntity asPlayer()
        {
            return p;
        }

        @Override
        public Object value() {
            return p;
        }

        @Override
        public int asInt() {
            return 0;
        }

        @Override
        public float asFloat() {
            return 0;
        }

        @Override
        public double asDouble() {
            return 0;
        }

        @Override
        public long asLong() {
            return 0;
        }

        @Override
        public short asShort() {
            return 0;
        }

        @Override
        public byte asByte() {
            return 0;
        }

        @Override
        public boolean asBoolean() {
            return false;
        }

        @Override
        public String asString() {
            return null;
        }

        @Override
        public Plugin getOwningPlugin() {
            return pg;
        }

        @Override
        public void invalidate() {

        }
    }

    @EventHandler
    public void onShoot(ProjectileHitEvent event) {

        if (event.getEntity().getShooter() instanceof LivingEntity) {
            LivingEntity le = (LivingEntity) event.getEntity().getShooter();
            event.getEntity().setMetadata("Shooter", new MetaDataPlayer(BAKACraft.instance, le));
        }
        if (event.getEntity() instanceof Arrow) {
            Arrow entity = (Arrow) event.getEntity();
            entity.setBounce(false);
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            Entity entityProj = event.getDamager();
            if (!entityProj.hasMetadata("Shooter")) return;
            LivingEntity entity = ((MetaDataPlayer)(entityProj.getMetadata("Shooter")).get(0)).asPlayer();
            if (injugdge.containsKey(entity)) return;

            ItemStack weapon = null;
            if(entity instanceof Player) {
                if(!BAKACraft.CHECK_PLAYER_PERM(entity, BAKACraft.PERM_ARCHER))
                {
                    return;
                }
                weapon= ((Player)entity).getItemInHand();
            }else
            {
                weapon = entity.getEquipment().getItemInHand();
            }


            if (weapon != null) {
                if (weapon.hasItemMeta() && weapon.getItemMeta().hasLore()) {
                    List<String> lores = weapon.getItemMeta().getLore();
                    for (String lore : lores) {
                        for (BaseSkillBow skill : skills) {
                            if (lore.contains(skill.getName())) {
                                skill.applyEffect(event, entity);
                            }
                        }
                    }
                }

            }

            if(entity instanceof Player)
            {
                BAKACraft.TIPS_BOARD.showInfo((Player)entity, "伤害", (int)event.getDamage());
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
        int itemId = 261;
        String name = null;
        if (item <= 14) {
            target = 霜之獠牙长弓;
            name = "&B霜之獠牙长弓";
            itemId = 261;
        } else if (item > 14 && item <= 28) {
            target = 呼啸狂风之弓;
            name = "&B呼啸狂风之弓";
            itemId = 261;
        } else if (item == 29) {
            if(level == 0) level = 3;
            else if(level == 1) level = 4;
            target = 霸王蝶;
            name = "&E霸王蝶";
            itemId = 261;
        } else {
            name = "&5 倾城之弓:";
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
