package ga.enimaloc.displug.internal.command;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandContext {

    private final String arguments;
    private final MessageReceivedEvent event;

    public CommandContext(MessageReceivedEvent event, String arguments) {
        this.event = event;
        this.arguments = arguments;
    }

    public MessageReceivedEvent getEvent() {
        return event;
    }

    public ChannelType getType() {
        return event.getChannelType();
    }

    public User getAuthor() {
        return event.getAuthor();
    }

    public Message getMessage() {
        return event.getMessage();
    }

    public String getArguments() {
        return arguments;
    }

    public String[] getSplitArguments() {
        return arguments.split(" ");
    }
}
