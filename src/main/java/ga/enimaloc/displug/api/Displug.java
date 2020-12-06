package ga.enimaloc.displug.api;

import ga.enimaloc.displug.internal.Configuration;
import net.dv8tion.jda.api.JDA;

public interface Displug {

    JDA getJDA();
    Configuration getConfiguration();

    void addCommand(Command command);

}
