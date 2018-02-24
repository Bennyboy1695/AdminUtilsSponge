package io.github.Bennyboy1695.AdminUtilsSponge.Sponge.Commands;

import io.github.Bennyboy1695.AdminUtilsSponge.Utils.PermissionStrings;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

/**
 * io.github.Bennyboy1695.AdminUtilsSponge.Sponge.Commands was created by Bennyboy1695 on 30/03/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */
public class CommandAdminUtils implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!args.<String>hasAny("0")) {
            src.sendMessage(Text.of(TextColors.BLUE, "AdminUtils ", TextColors.GOLD, "is a custom plugin used to view inventories, save playerdata on deaths and on world save to reduce item loss."));
            if (args.<String>hasAny(Text.of("commands"))) {
                if (src.hasPermission(PermissionStrings.helpcommands)) {
                    src.sendMessage(Text.of(TextColors.BLUE, "Commands ", TextColors.WHITE, ": "));
                    src.sendMessage(Text.of(TextColors.AQUA, "/oi <player>"));
                    src.sendMessage(Text.of(TextColors.GREEN, "This command allows you to open up a players inventory while they are online!"));
                    src.sendMessage(Text.of(TextColors.WHITE, "~~~~~~~~~~~~~~~~~~~~~~~~~"));
                    src.sendMessage(Text.of(TextColors.AQUA, "/oe <player>"));
                    src.sendMessage(Text.of(TextColors.GREEN, "This command allows you to open up a players enderchest while they are online!"));
                    src.sendMessage(Text.of(TextColors.WHITE, "~~~~~~~~~~~~~~~~~~~~~~~~~"));
                    src.sendMessage(Text.of(TextColors.AQUA, "/restore <player>"));
                    src.sendMessage(Text.of(TextColors.GREEN, "This command allows you to restore a players inventory from death, NOTE: Only the latest death is saved!"));
                } else {
                    src.sendMessage(Text.of(TextColors.RED, "You don't have perms to use this argument!"));
                }
            }
            if (args.<String>hasAny(Text.of("perms"))) {
                if (src.hasPermission(PermissionStrings.helpperms)) {
                    src.sendMessage(Text.of(TextColors.BLUE, "Permissions ", TextColors.WHITE, ": "));
                    src.sendMessage(Text.of(TextColors.AQUA, "Oi Permissions :"));
                    src.sendMessage(Text.of(TextColors.GREEN, PermissionStrings.inv, " | ", PermissionStrings.invexempt, " | ", PermissionStrings.invothers, " | ", PermissionStrings.invoverride, " | ", " This following perm is needed to open invs : adminutilsforge.command.adminutilsopeninv12345678"));
                    src.sendMessage(Text.of(TextColors.WHITE, "~~~~~~~~~~~~~~~~~~~~~~~~~~"));
                    src.sendMessage(Text.of(TextColors.AQUA, "Oe Permissions :"));
                    src.sendMessage(Text.of(TextColors.GREEN, PermissionStrings.ender, " | ", PermissionStrings.endexempt, " | ", PermissionStrings.endothers, " | ", PermissionStrings.endoverride, " | ", " This following perm is needed to open invs : adminutilsforge.command.adminutilsopenend12345678"));
                    src.sendMessage(Text.of(TextColors.WHITE, "~~~~~~~~~~~~~~~~~~~~~~~~~~"));
                    src.sendMessage(Text.of(TextColors.AQUA, "Restore Permissions :"));
                    src.sendMessage(Text.of(TextColors.GREEN, PermissionStrings.restore));
                } else {
                    src.sendMessage(Text.of(TextColors.RED, "You don't have perms to use this argument!"));
                }
            }
        } else {
            src.sendMessage(Text.of(TextColors.RED, "Arguments : commands or perms"));
        }

        return CommandResult.success();
    }
}
