package bakacraft.EnemySpawn;

import java.util.LinkedList;
import java.util.List;

public class EnemyStorage {
    private static EnemyStorage ourInstance = new EnemyStorage();

    public static EnemyStorage getInstance() {
        return ourInstance;
    }

    private EnemyStorage() {
    }

    private List<ISave> storage = new LinkedList<>();

    public void registerStorage(ISave savable)
    {
        storage.add(savable);
    }

    public void saveAll()
    {
        for (ISave i : storage)
        {
            i.save();
        }
    }
}
