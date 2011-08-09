package net.digiex.featurepack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.World;
import org.bukkit.util.config.Configuration;

public class FPUtils {

    private File config = new File("plugins" + File.separator + "FeaturePack",
            "config.yml");
    private File messages = new File(
            "plugins" + File.separator + "FeaturePack", "messages.yml");
    private File savedsettings = new File("plugins" + File.separator
            + "FeaturePack", "savedsettings.yml");
    private File configold = new File("plugins" + File.separator
            + "FeaturePack", "configold.yml");
    private File messagesold = new File("plugins" + File.separator
            + "FeaturePack", "messages.yml");

    public void copy(InputStream src, File dst) throws IOException {
        InputStream in = src;
        OutputStream out = new FileOutputStream(dst);

        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        out.close();
        try {
            in.close();
        } catch (Exception e) {
        }
    }

    public void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        copy(in, dst);
    }

    public File config() {
        return config;
    }

    public File messages() {
        return messages;
    }

    public File savedsettings() {
        return savedsettings;
    }

    public File configold() {
        return configold;
    }

    public File messagesold() {
        return messagesold;
    }

    public Configuration get(File file) {
        Configuration config = new Configuration(file);
        config.load();
        return config;
    }

    public Object getProperty(File file, String path) {
        Configuration config = get(file);
        return config.getProperty(path);
    }

    public void setProperty(File file, String path, Object value) {
        Configuration config = get(file);
        config.setProperty(path, value);
        config.save();
    }

    public void removeProperty(File file, String path) {
        Configuration config = get(file);
        config.removeProperty(path);
        config.save();
    }

    // remove the below options to where they belong
    public void saveTimeLock(World world, Long time) {
        setProperty(savedsettings(), "TimeLocked." + world.getName(), time);
    }

    public void saveWeatherLock(World world, String weather) {
        setProperty(savedsettings(), "WeatherLocked." + world.getName(),
                weather);
    }

    public void removeTimeLock(World world) {
        removeProperty(savedsettings(), "TimeLocked." + world.getName());
    }

    public void removeWeatherLock(World world) {
        removeProperty(savedsettings(), "WeatherLocked." + world.getName());
    }

    public void saveLotto(Integer code, Integer amount) {
        setProperty(savedsettings(), "Lotto.Code", code);
        setProperty(savedsettings(), "Lotto.Amount", amount);
    }
}