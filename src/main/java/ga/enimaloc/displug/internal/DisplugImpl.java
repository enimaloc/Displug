package ga.enimaloc.displug.internal;

import ga.enimaloc.displug.api.Command;
import ga.enimaloc.displug.api.Displug;
import ga.enimaloc.displug.internal.command.CommandContext;
import ga.enimaloc.displug.internal.command.CommandResult;
import ga.enimaloc.displug.internal.managers.CommandManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class DisplugImpl implements Displug {

    private JDA jda;
    private Configuration configuration;
    private CommandManager commandManager;

    public DisplugImpl() {
        configuration = new Configuration();
        if (!Configuration.DEFAULT_CONFIGURATION_FILE.exists()) {
            //noinspection ResultOfMethodCallIgnored
            Configuration.DEFAULT_CONFIGURATION_FILE.getParentFile().mkdirs();
            configuration.save();
            System.exit(0);
        }
        configuration.load();

        commandManager = new CommandManager(this);
    }

    public void start() {
        try {
            jda = JDABuilder.createDefault(configuration.getToken()).addEventListeners(commandManager).build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addCommand(Command command) {
        commandManager.add(command.getName(), command);
        for (String alias : command.getAliases()) {
            commandManager.add(alias, command);
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
