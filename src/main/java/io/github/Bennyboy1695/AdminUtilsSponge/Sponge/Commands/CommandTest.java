package io.github.Bennyboy1695.AdminUtilsSponge.Sponge.Commands;

import io.github.Bennyboy1695.AdminUtilsSponge.AdminUtilsSponge;
import io.github.Bennyboy1695.AdminUtilsSponge.Utils.Hastebin;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.text.Text;

import java.util.Date;

/**
 * io.github.Bennyboy1695.AdminUtilsSponge.Sponge.Commands was created by Bennyboy1695 on 13/04/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */
public class CommandTest implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Hastebin.post("this is a test post!");
        return CommandResult.success();
    }
}