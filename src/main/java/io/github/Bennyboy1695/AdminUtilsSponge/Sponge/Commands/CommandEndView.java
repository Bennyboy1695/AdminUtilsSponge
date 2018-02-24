package io.github.Bennyboy1695.AdminUtilsSponge.Sponge.Commands;

import io.github.Bennyboy1695.AdminUtilsSponge.AdminUtilsSponge;
import io.github.Bennyboy1695.AdminUtilsSponge.Utils.PermissionStrings;
import io.github.Bennyboy1695.AdminUtilsSponge.Utils.RefStrings;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

/**
 * io.github.Bennyboy1695.AdminUtilsSponge.Sponge.Commands was created by Bennyboy1695 on 22/03/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */

public class CommandEndView implements CommandExecutor {

    AdminUtilsSponge adminUtilsSponge;

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        User target = args.<User>getOne("player").get();
        Player target1 = target.getPlayer().get();

        if (src instanceof Player) {
            if (args.hasAny("0")) {
                Text.builder()
                        .color(TextColors.RED).append(Text.of("You forgot to add a playername!"))
                        .color(TextColors.GREEN).append(Text.of("Usage:")).color(TextColors.GOLD).append(Text.of(" /oe <player>"))
                        .color(TextColors.AQUA).append(Text.of("Or do"))
                        .color(TextColors.GOLD).append(Text.of(" /adminutils"))
                        .color(TextColors.AQUA).append(Text.of(" for more info!")).build();
            } else {
                if (src.hasPermission(PermissionStrings.endothers)) {
                    if (target.isOnline()) {
                        ((Player) src).openInventory(target1.getEnderChestInventory(), AdminUtilsSponge.getInstance().getCause());
                        this.notify(((Player) src).getPlayer().get(), target.getName());
                    } else {
                        src.sendMessage(Text.of(RefStrings.prefixSponge, TextColors.RED, "Offline Inventory opening is not yet implemented!"));
                    }
                }

            }
        }else{
            src.sendMessage(Text.of("You need to be a player to run this command!"));
        }
        return CommandResult.success();
    }


    public void notify(Player player, String argument) {
        for (Player player2 : Sponge.getServer().getOnlinePlayers()) {
            if (player2.hasPermission("adminutils.notify")) {
                player2.sendMessage(Text.of(TextStyles.ITALIC, TextColors.GRAY, player.getName(), " has opened ", argument, "'s enderchest!"));
            }
        }
    }

}
