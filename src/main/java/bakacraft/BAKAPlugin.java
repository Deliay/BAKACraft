package bakacraft;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class BAKAPlugin {
    private JavaPlugin instance;

    public BAKAPlugin(JavaPlugin instance) {
        this.instance = instance;
    }

    public JavaPlugin getInstance() {
        return instance;
    }

    public void onDisable() {

    }
}
