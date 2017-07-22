package bakacraft;

import bakacraft.WeaponSkills.Skill;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pluginMain.BAKAScoreboard;

import java.util.LinkedList;
import java.util.List;

public class BAKACraft extends JavaPlugin {

    public static DeadLimiter deadLimiter;
    public static Skill skill;
    public static WeaponLevel weaponLevel;
    public static EnemySpawner enemySpawner;
    public static PlayerLevel playerLevel;
    public static BAKACraft instance;
    public static BAKAScoreboard TIPS_BOARD = null;

    public final static String PERM_BASAKER = "BAKACraft.Basaker";
    public final static String PERM_SABER = "BAKACraft.Saber";
    public final static String PERM_ARCHER = "BAKACraft.Archer";

    private final static List<ICommand> commands = new LinkedList<>();

    @Override
    public void onEnable() {
        instance = this;
        //保存背包+死亡限制

        TIPS_BOARD = (BAKAScoreboard)getServer().getPluginManager().getPlugin("BAKAScoreboard");

        deadLimiter = new DeadLimiter(this);
        //物品技能
        skill = new Skill(this);
        //物品等级
        weaponLevel = new WeaponLevel(this);
        //怪物增强
        enemySpawner = new EnemySpawner(this);
        //玩家等级
        playerLevel = new PlayerLevel(this);

    }

    public final static void registerCommandDispatcher(ICommand cmd)
    {
        commands.add(cmd);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        for (ICommand cmd : commands)
        {
            if(label.equals(cmd.getName())) return cmd.onCommand(sender, label, args);
        }
        return false;
    }

    public static final boolean CHECK_PLAYER_PERM(Entity entity, String perm)
    {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            return player.hasPermission(perm);
        }else
        {
            return false;
        }
    }
}
