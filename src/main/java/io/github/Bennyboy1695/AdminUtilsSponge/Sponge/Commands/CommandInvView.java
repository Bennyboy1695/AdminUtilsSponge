package io.github.Bennyboy1695.AdminUtilsSponge.Sponge.Commands;

import io.github.Bennyboy1695.AdminUtilsSponge.AdminUtilsSponge;
import io.github.Bennyboy1695.AdminUtilsSponge.Forge.Modules.Inventory.Commands.CMDInventoryView;
import io.github.Bennyboy1695.AdminUtilsSponge.Forge.Modules.Inventory.Handlers.NormalViewInventory;
import io.github.Bennyboy1695.AdminUtilsSponge.Forge.Modules.Inventory.Handlers.OfflineInventory;
import io.github.Bennyboy1695.AdminUtilsSponge.Forge.Utils.ForgePlayerUtils;
import io.github.Bennyboy1695.AdminUtilsSponge.Utils.PermissionStrings;
import io.github.Bennyboy1695.AdminUtilsSponge.Utils.RefStrings;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.items.wrapper.PlayerInvWrapper;
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

import java.io.File;
import java.io.IOException;


/**
 * io.Bennyboy1695.AdminUtilsSponge.Commands was created by Bennyboy1695 on 11/03/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */
public class CommandInvView implements CommandExecutor{

    CMDInventoryView cmdInventoryView = new CMDInventoryView();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        User target1 = args.<User>getOne("player").get();
        String target = target1.getName().replace("EntityPlayerMP['", "").replaceAll("'*,", "").replaceAll("'.* ", "").replaceAll("z=.*]", "");

        if (src instanceof Player) {
            if (args.hasAny("0")) {
                Text.builder()
                        .color(TextColors.RED).append(Text.of("You forgot to add a playername!"))
                        .color(TextColors.GREEN).append(Text.of("Usage:")).color(TextColors.GOLD).append(Text.of(" /oi <player>"))
                        .color(TextColors.AQUA).append(Text.of("Or do"))
                        .color(TextColors.GOLD).append(Text.of(" /adminutils"))
                        .color(TextColors.AQUA).append(Text.of(" for more info!")).build();
            } else {
                if (src.toString().equals(target1.getName())) {
                    src.sendMessage(Text.of(RefStrings.prefixSponge, TextColors.BLUE, "Why are you opening your own inventory o.O"));
                } else if (target1.isOnline()) {
                    if (src.hasPermission(PermissionStrings.invothers)) {
                        if(!target1.hasPermission(PermissionStrings.invexempt) && !src.hasPermission(PermissionStrings.invoverride)) {
                            this.run(((Player) src).getPlayer().get(), cmdInventoryView.getCommandName(), target1.getName(), "");
                        } else for (Player player : Sponge.getServer().getOnlinePlayers()) {
                            if (player.getName().equals(target) && player.hasPermission(PermissionStrings.invexempt)) {
                                src.sendMessage(Text.of(RefStrings.prefixSponge, TextColors.RED, "This person is exempt from having their inventory opened!"));
                            } else if (player.getName().equals(target) && player.hasPermission(PermissionStrings.invexempt)) {
                                if (!src.hasPermission(PermissionStrings.invoverride)) {
                                    src.sendMessage(Text.of(RefStrings.prefixSponge, TextColors.RED, "You don't have override perms to open this inventory!"));
                                }
                            }else if (src.hasPermission(PermissionStrings.invoverride)){
                                this.run(((Player) src).getPlayer().get(), cmdInventoryView.getCommandName(), target1.getName(), " using an override");
                        }

                    }
                    }
                } else {
                    //src.sendMessage(Text.of(RefStrings.prefixSponge, TextColors.RED, "The player you specified is offline meaning this command won't work currently!"));
                    try {
                        src.sendMessage(Text.of(RefStrings.prefixSponge, "Attempting to read offline playerfile!"));
                        EntityPlayerMP playerMP = (EntityPlayerMP) ForgePlayerUtils.getPlayerFromUUID(((Player) src).getUniqueId());
                        EntityPlayerMP targetMP = (EntityPlayerMP) ForgePlayerUtils.getPlayerFromUUID(target1.getUniqueId());
                        playerMP.displayGUIChest(new OfflineInventory(playerMP, targetMP));
                        //CompressedStreamTools.read(new File(DimensionManager.getCurrentSaveRootDirectory(), "playerdata/" + target1.getUniqueId().toString() + ".dat"));
                    } catch (Exception e) {
                        src.sendMessage(Text.of(RefStrings.prefixSponge, "Failed to open offline inventory!"));
                        e.printStackTrace();
                    }
                    //((Player) src).openInventory(target1.getInventory(), AdminUtilsSponge.getInstance().getCause());
                }
            }
        }else{
            src.sendMessage(Text.of("You need to be a player to run this command!"));
        }return CommandResult.success();
    }


    public void notify(Player player, String argument, String extra) {
        for (Player player2 : Sponge.getServer().getOnlinePlayers()) {
            if (player2.hasPermission("adminutils.notify")) {
                player2.sendMessage(Text.of(TextStyles.ITALIC, TextColors.GRAY, player.getName(), " has opened ", argument, "'s inventory" + extra + "!"));
            }
        }
    }

    public void run(Player player, String command, String arguments, String extra) {
        Sponge.getCommandManager().process(player, command + " " + arguments);
        this.notify(player, arguments, extra);
    }

}
