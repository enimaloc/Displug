package ga.enimaloc.displug.internal.managers;

import ga.enimaloc.displug.api.Command;
import ga.enimaloc.displug.api.Displug;
import ga.enimaloc.displug.internal.command.CommandContext;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

public class CommandManager extends DManager<String, Command> implements EventListener {

    private Displug displug;

    public CommandManager(Displug displug) {
        this.displug = displug;
    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof MessageReceivedEvent) {
            Message message = ((MessageReceivedEvent) event).getMessage();
            if (message.getAuthor().isBot()) {
                return;
            }
            for (String prefix : displug.getConfiguration().getPrefix()) {
                String raw = message.getContentRaw();
                if (!raw.startsWith(prefix)) {
                    continue;
                }
                raw = raw.replaceFirst(prefix, "");
                String[] splitRaw = raw.split(" ");
                raw = raw.replaceFirst(splitRaw[0]+" ", "");
                Command command = get(splitRaw[0]).orElse(null);
                if (command == null) {
                    continue;
                }
                try {
                    command.execute(new CommandContext((MessageReceivedEvent) event, raw)).execute(((MessageReceivedEvent) event).getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
