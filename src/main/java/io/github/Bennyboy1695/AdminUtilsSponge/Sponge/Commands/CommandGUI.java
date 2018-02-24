package io.github.Bennyboy1695.AdminUtilsSponge.Sponge.Commands;

import com.codehusky.huskyui.StateContainer;
import com.codehusky.huskyui.states.Page;
import io.github.Bennyboy1695.AdminUtilsSponge.AdminUtilsSponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

/**
 * io.github.Bennyboy1695.AdminUtilsSponge.Sponge.Commands was created by Bennyboy1695 on 07/11/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */
public class CommandGUI implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        StateContainer container = new StateContainer();
        Page.builder().setTitle(Text.of("Test")).build(AdminUtilsSponge.getInstance().toString());
        return CommandResult.success();
    }
}
