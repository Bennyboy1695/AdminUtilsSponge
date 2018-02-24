package io.github.Bennyboy1695.AdminUtilsSponge.Sponge.Commands;

import io.github.Bennyboy1695.AdminUtilsSponge.AdminUtilsSponge;
import io.github.Bennyboy1695.AdminUtilsSponge.Utils.PlayerUtils;
import io.github.Bennyboy1695.AdminUtilsSponge.Utils.RefStrings;
import net.minecraftforge.common.DimensionManager;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * io.github.Bennyboy1695.AdminUtilsSponge.Sponge.Commands was created by Bennyboy1695 on 28/03/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */
public class CommandRestore implements CommandExecutor {

    String savepath = AdminUtilsSponge.getInstance().getSavePath();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        User target = args.<User>getOne("player").get();


        if (src instanceof Player) {
            if (args.hasAny("0")) {
                Text.builder()
                        .color(TextColors.RED).append(Text.of("You forgot to add a playername!"))
                        .color(TextColors.GREEN).append(Text.of("Usage:")).color(TextColors.GOLD).append(Text.of(" /restore <player>"))
                        .color(TextColors.AQUA).append(Text.of("Or do"))
                        .color(TextColors.GOLD).append(Text.of(" /adminutils"))
                        .color(TextColors.AQUA).append(Text.of(" for more info!")).build();
            } else {
                if (target.isOnline()) {
                    src.sendMessage(Text.of(RefStrings.prefixSponge, TextColors.GREEN, "Restoring ", target.getName(), "'s death file, this will only work by kicking the player currently!"));
                    try {
                        target.getPlayer().get().kick(Text.of("Restoring your file from death, please rejoin in 5 seconds!"));
                        restoreDeathOfPlayer(target.getUniqueId().toString());
                        src.sendMessage(Text.of(RefStrings.prefixSponge, TextColors.GOLD, "Successfully restored ", target.getName(), "'s death file!"));
                        notify(((Player) src).getPlayer().get(), target.getName(), "");
                    } catch (Exception e) {
                        e.printStackTrace();
                        src.sendMessage(Text.of(RefStrings.prefixSponge, TextColors.RED, "Failed to restore ", target.getName(), "'s death file!"));
                    }
                } else {
                    src.sendMessage(Text.of(RefStrings.prefixSponge, TextColors.GREEN, "Restoring ", target.getName(), "'s death file, they are offline, but this will still work!"));
                    try {
                        //target.getPlayer().get().kick(Text.of("Restoring your file from death, please rejoin in 5 seconds!"));
                        restoreDeathOfPlayer(target.getUniqueId().toString());
                        src.sendMessage(Text.of(RefStrings.prefixSponge, TextColors.GOLD, "Successfully restored ", target.getName(), "'s death file!"));
                        notify(((Player) src).getPlayer().get(), target.getName(), "");
                    } catch (Exception e) {
                        e.printStackTrace();
                        src.sendMessage(Text.of(RefStrings.prefixSponge, TextColors.RED, "Failed to restore ", target.getName(), "'s death file!"));
                    }
                }
            }

        }else{
            src.sendMessage(Text.of("You need to be a player to run this command!"));
        }return CommandResult.success();
    }

    public void notify(Player player, String argument, String extra) {
        for (Player player2 : Sponge.getServer().getOnlinePlayers()) {
            if (player2.hasPermission("adminutils.notify")) {
                player2.sendMessage(Text.of(TextStyles.ITALIC, TextColors.GRAY, player.getName(), " has restored ", argument, "'s death file" + extra + "!"));
            }
        }
    }

    private void restoreDeathOfPlayer(String UUID) throws IOException {
        File saveFile = new File(DimensionManager.getCurrentSaveRootDirectory() + "/playerdata/" + UUID + ".dat");
        File currentFile = new File(DimensionManager.getCurrentSaveRootDirectory() + "/" + savepath + "/Deaths/playerdata/" + UUID + ".dat");
        if (currentFile.exists()) {
            if (saveFile.exists()) {
                saveFile.delete();
            }

            saveFile.getParentFile().mkdirs();
            FileInputStream fis = new FileInputStream(currentFile);
            FileOutputStream fos = new FileOutputStream(saveFile);
            byte[] currentFileData = new byte[(int) currentFile.length()];
            fis.read(currentFileData);
            fis.close();
            fos.write(currentFileData);
            fos.flush();
            fos.close();
        }
    }


}
