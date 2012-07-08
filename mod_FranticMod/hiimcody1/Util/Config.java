package hiimcody1.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ModLoader;

public class Config {

	static Minecraft mc = ModLoader.getMinecraftInstance();
	static boolean loggedIn = false;

	private static final File cfgdir = new File(Minecraft.getMinecraftDir(), "/config/");
	private static final File cfgfile = new File(cfgdir, "mod_FranticMod.cfg");
	public static Level cfgLoggingLevel;
	public static final Properties props = new Properties();

	public static void saveConfig() throws IOException {
		cfgdir.mkdir();
		if (!cfgfile.exists() && !cfgfile.createNewFile()) { return; }
		if (cfgfile.canWrite()) {
			FileOutputStream fileoutputstream = new FileOutputStream(cfgfile);
			props.store(fileoutputstream, "FranticMod Config");
			fileoutputstream.close();
		}
	}

	public static void loadConfig() throws IOException {
		cfgdir.mkdir();
		if (!cfgfile.exists() && !cfgfile.createNewFile()) { return; }
		if (cfgfile.canRead()) {
			FileInputStream fileinputstream = new FileInputStream(cfgfile);
			props.load(fileinputstream);
			fileinputstream.close();
		}
	}

	static {
		cfgLoggingLevel = Level.FINER;
	}
}
