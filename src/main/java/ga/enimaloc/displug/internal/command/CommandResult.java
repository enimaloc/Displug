package ga.enimaloc.displug.internal.command;

import java.util.function.BiConsumer;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;

public enum CommandResult {

    REPLY           ((message, input) -> message.getChannel().sendMessage(input.toString()).queue()),
    REACT_UNICODE   ((message, input) -> message.addReaction((String) input).queue()),
    REACT_EMOTE     ((message, input) -> message.addReaction((Emote) input).queue());

    private BiConsumer<Message, Object> action;
    private Object input;
    private CommandResult next;

    CommandResult(BiConsumer<Message, Object> action) {
        this.action = action;
    }

    public CommandResult setInput(Object input) {
        this.input = input;
        return this;
    }

    public void execute(Message message) {
        action.accept(message, input);
        if (next != null) {
            next.execute(message);
        }
    }

    public CommandResult next(CommandResult commandResult) {
        next = commandResult;
        return this;
    }
}
