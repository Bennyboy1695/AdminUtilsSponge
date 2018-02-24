package io.github.Bennyboy1695.AdminUtilsSponge.Sponge.Commands;

import io.github.Bennyboy1695.AdminUtilsSponge.Utils.PlayerUtils;
import io.github.Bennyboy1695.AdminUtilsSponge.Utils.RefStrings;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.profile.GameProfileManager;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;

/**
 * io.github.Bennyboy1695.AdminUtilsSponge.Sponge.Commands was created by Bennyboy1695 on 23/03/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */
public class CommandUUID implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        User target = args.<User>getOne("player").get();

        src.sendMessage(Text.of(RefStrings.prefixSponge, TextColors.GREEN, "Username", TextColors.WHITE, ": ", TextColors.AQUA, PlayerUtils.getNameFromUUID(target.getUniqueId())));
        src.sendMessage(Text.builder().append(Text.of(TextColors.GREEN, "UUID", TextColors.WHITE, ": ", PlayerUtils.getUUIDFromName(target.getName())))
                .onHover(TextActions.showText(Text.of(TextColors.GOLD, "Click me to get the UUID copied to the chat bar!")))
                .onClick(TextActions.suggestCommand(PlayerUtils.getUUIDFromName(target.getName()).toString())).build());
        if (target.isOnline()) {
            src.sendMessage(Text.of(TextColors.GOLD, "Player is online currently!"));
        } else {
            src.sendMessage(Text.of(TextColors.GOLD, "Player is offline currently!"));
        }

        return CommandResult.success();
    }
}
