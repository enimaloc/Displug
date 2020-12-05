package ga.enimaloc.displug.internal;

import ga.enimaloc.displug.api.Displug;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class DisplugImpl implements Displug {

    private JDA jda;
    private Configuration configuration;

    public DisplugImpl() {
        configuration = new Configuration();
        if (!Configuration.DEFAULT_CONFIGURATION_FILE.exists()) {
            //noinspection ResultOfMethodCallIgnored
            Configuration.DEFAULT_CONFIGURATION_FILE.getParentFile().mkdirs();
            configuration.save();
            System.exit(0);
        }
        configuration.load();
    }

    public void start() {
        try {
            jda = JDABuilder.createDefault(configuration.getToken()).build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JDA getJDA() {
        return jda;
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}
