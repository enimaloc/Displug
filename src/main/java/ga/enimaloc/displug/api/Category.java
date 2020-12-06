package ga.enimaloc.displug.api;

import java.awt.*;
import java.util.function.Consumer;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

public class Category {

    private String name;
    private String description;
    private Color color;
    private Consumer<MessageReceivedEvent> onCommand;
    private Consumer<PrivateMessageReceivedEvent> onPrivateCommand;
    private Consumer<GuildMessageReceivedEvent> onGuildCommand;

    Category(
            String name,
            String description,
            Color color,
            Consumer<MessageReceivedEvent> onCommand,
            Consumer<PrivateMessageReceivedEvent> onPrivateCommand,
            Consumer<GuildMessageReceivedEvent> onGuildCommand
    ) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.onCommand = onCommand;
        this.onPrivateCommand = onPrivateCommand;
        this.onGuildCommand = onGuildCommand;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Color getColor() {
        return color;
    }

    public Consumer<MessageReceivedEvent> getOnCommand() {
        return onCommand;
    }

    public Consumer<PrivateMessageReceivedEvent> getOnPrivateCommand() {
        return onPrivateCommand;
    }

    public Consumer<GuildMessageReceivedEvent> getOnGuildCommand() {
        return onGuildCommand;
    }

    public class Builder {

        private String name;
        private String description;
        private Color color;
        private Consumer<MessageReceivedEvent> onCommand;
        private Consumer<PrivateMessageReceivedEvent> onPrivateCommand;
        private Consumer<GuildMessageReceivedEvent> onGuildCommand;

        public void setName(String name) {
            this.name = name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public void setOnCommand(Consumer<MessageReceivedEvent> onCommand) {
            this.onCommand = onCommand;
        }

        public void setOnPrivateCommand(Consumer<PrivateMessageReceivedEvent> onPrivateCommand) {
            this.onPrivateCommand = onPrivateCommand;
        }

        public void setOnGuildCommand(Consumer<GuildMessageReceivedEvent> onGuildCommand) {
            this.onGuildCommand = onGuildCommand;
        }

        public Category build() {
            return new Category(name, description, color, onCommand, onPrivateCommand, onGuildCommand);
        }
    }
}
