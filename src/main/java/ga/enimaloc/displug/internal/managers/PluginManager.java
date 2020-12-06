package ga.enimaloc.displug.internal.managers;

import com.google.gson.Gson;
import ga.enimaloc.displug.api.Displug;
import ga.enimaloc.displug.internal.exception.PluginException;
import ga.enimaloc.displug.plugin.Displugin;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.impl.Logger;
import org.slf4j.impl.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;
import org.yaml.snakeyaml.Yaml;

public class PluginManager extends SManager<Displugin> {

    public static final File DEFAULT_PLUGINS_FOLDER = new File("data", "plugins");

    private final Displug displug;
    private final List<Displugin> plugins;
    private final File pluginsFolder;

    private final Logger logger = StaticLoggerBinder.getSingleton().getLoggerFactory().getLogger(PluginManager.class);

    public PluginManager(Displug displug) {
        this(displug, PluginManager.DEFAULT_PLUGINS_FOLDER);
    }

    public PluginManager(Displug displug, File pluginsFolder) {
        this.plugins = new ArrayList<>();
        this.pluginsFolder = pluginsFolder;
        //noinspection ResultOfMethodCallIgnored
        this.pluginsFolder.mkdirs();
        this.displug = displug;
    }

    public void loadPlugins() {
        for (File file : pluginsFolder.listFiles()) {
            loadPlugin(file);
        }
    }

    public void loadPlugin(File file) {
        try {
            try {
                if (file.isDirectory() || !file.getName().endsWith(".jar")) {
                    return;
                }
                logger.trace("Loading {}...", file.getName());
                URLClassLoader classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});
                InputStream pluginInfoInputStream = classLoader.getResourceAsStream("plugin.yml");
                if (pluginInfoInputStream == null) {
                    throw new PluginException("Can't find plugin.yml in jar");
                }
                Map<String, Object> pluginInfo = new Yaml().load(pluginInfoInputStream);
                String name = (String) pluginInfo.get("name");
                String main = (String) pluginInfo.get("main");
                String author = (String) pluginInfo.get("author");
                String version = (String) pluginInfo.get("version");
                Objects.requireNonNull(name);
                Objects.requireNonNull(main);
                Objects.requireNonNull(author);
                Objects.requireNonNull(version);
                Class<?> mainClass = classLoader.loadClass(main);
                if (!Displugin.class.isAssignableFrom(mainClass)) {
                    throw new PluginException("Main class of a plugin not extend Displugin class");
                }
                Displugin plugin = (Displugin) mainClass.getConstructor(Displug.class).newInstance(displug);
                plugin.onLoad();
                add(plugin);
            } catch (MalformedURLException e) {
                throw new PluginException(e);
            } catch (NullPointerException e) {
                throw new PluginException("Maybe some attributes are missing in plugin.yml", e);
            } catch (ClassNotFoundException e) {
                throw new PluginException("Cannot find main class", e);
            } catch (NoSuchMethodException e) {
                throw new PluginException("Can't find constructor", e);
            } catch (InvocationTargetException e) {
                throw new PluginException("A error as been thrown in main constructor", e);
            } catch (InstantiationException e) {
                throw new PluginException("Can't create plugin main class object", e);
            } catch (IllegalAccessException e) {
                throw new PluginException("Can't access to plugin constructor", e);
            } catch (Exception e) {
                throw new PluginException("???", e);
            }
        } catch (PluginException pluginException) {
            logger.error("Error while loading "+file.getName(), pluginException);
        }
    }
}
