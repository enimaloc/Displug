package ga.enimaloc.displug.internal;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public class Configuration {

    public final static File DEFAULT_CONFIGURATION_FILE = new File("data", "displug.yml");

    private final File configurationFile;
    private Map<String, Object> extra;

    public Configuration() {
        this(DEFAULT_CONFIGURATION_FILE);
    }

    public Configuration(File configurationFile) {
        this.configurationFile = configurationFile;
        this.extra = new HashMap<>();
        setDefault();
    }

    public void load() {
        try (FileInputStream fileInputStream = new FileInputStream(configurationFile)) {
            extra = new Yaml().load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        Map<String, Object> saved = new HashMap<>();
        saved.putAll(extra);
        try (FileWriter writer = new FileWriter(configurationFile)) {
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            options.setPrettyFlow(true);
            options.setCanonical(false);
            new Yaml(options).dump(saved, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setDefault() {
        this.extra.clear();
        setExtra("token",  "YOUR_BOT_TOKEN_GOES_HERE");
        setExtra("prefix", new String[]{"!"});
    }

    public void reset() {
        setDefault();
        save();
    }

    public void setExtra(String key, Object value) {
        extra.put(key, value);
    }

    public <T> T getExtra(Class<T> asClazz, String key) {
        return asClazz.cast(extra.get(key));
    }

    public <T> T getExtra(Class<T> asClazz, String key, T defaultValue) {
        return asClazz.cast(extra.getOrDefault(key, defaultValue));
    }

    public Object getExtra(String key) {
        return getExtra(Object.class, key);
    }

    public Object getExtra(String key, Object defaultValue) {
        return getExtra(Object.class, key, defaultValue);
    }

    public String getToken() {
        return getExtra(String.class, "token");
    }

    public List<String> getPrefix() {
        return getExtra(List.class, "prefix");
    }
}
