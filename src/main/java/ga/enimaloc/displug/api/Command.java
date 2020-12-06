package ga.enimaloc.displug.api;

import ga.enimaloc.displug.internal.command.CommandContext;
import ga.enimaloc.displug.internal.command.CommandResult;

public interface Command {

    String getName();
    default String[] getAliases() {return new String[0];}
    default String getDescription() {return "";}
    default Category[] getCategory() {return null;}

    CommandResult execute(CommandContext context) throws Exception;

}
