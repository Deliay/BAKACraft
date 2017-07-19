package bakalibs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class CConfiger extends YamlConfiguration {
	private File f;
	private Plugin p;

	public CConfiger(Plugin source, File file, FileConfiguration cfg) {
		p = source;
		f = file;
		try {
			cfg.save(f);
			this.load(f);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}

	}

	public CConfiger(Plugin source, String folderPath, String file) {
		p = source;
		folderProtector(p.getDataFolder());
		folderProtector(p.getDataFolder() + "\\" + folderPath);
		f = loadFile(p.getDataFolder() + "\\" + folderPath + "\\" + file);

		try {
			this.load(f);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public CConfiger(Plugin source, String file) {
		p = source;
		folderProtector(p.getDataFolder());
		f = loadFile(p.getDataFolder() + "\\" + file);
		try {
			this.load(f);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	private File folderProtector(File path) {
		if (!path.exists())
			path.mkdir();
		return path;
	}

	private File folderProtector(String path) {
		return folderProtector(new File(path));
	}

	private File loadFile(String file) {
		return loadFile(new File(file));
	}

	private File loadFile(File file) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	public boolean saveConfig() {
		try {
			this.save(f);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean forceReload() {
		try {
			this.load(f);
			return true;
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
			return false;
		}
	}

	public File getSpecifyFile() {
		return f;
	}

	public Plugin getPlugin() {
		return p;
	}

	public static boolean ReleaseFile(Plugin plugin, String _res, File _f) {
		try {
			if (!_f.exists())
				_f.createNewFile();
			InputStream in = plugin.getResource(_res);
			OutputStream out = new FileOutputStream(_f);
			byte[] buffer = new byte[1024];
			int len;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			in.close();
			out.close();
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean ReleaseFileIfNotExist(Plugin plugin, String _file) {
		File nf = new File(plugin.getDataFolder() + "\\" + _file);
		if (nf.exists())
			return false;
		else
			return ReleaseFile(plugin, _file, nf);
	}

	public static void CreateDataFoler(Plugin plugin) {
		if (plugin.getDataFolder().exists())
			return;
		plugin.getDataFolder().mkdirs();
	}
}
