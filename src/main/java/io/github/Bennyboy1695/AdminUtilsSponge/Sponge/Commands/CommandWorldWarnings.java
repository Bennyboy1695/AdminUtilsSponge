package io.github.Bennyboy1695.AdminUtilsSponge.Sponge.Commands;

import io.github.Bennyboy1695.AdminUtilsSponge.AdminUtilsSponge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import java.util.UUID;

/**
 * io.github.Bennyboy1695.AdminUtilsSponge.Sponge.Commands was created by Bennyboy1695 on 07/05/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */
public class CommandWorldWarnings implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        EntityPlayer player = (EntityPlayer) src;
        String name = player.getName();
        UUID uuid = player.getPersistentID();
        EntityPlayer forgeplayer = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(uuid);
        NBTTagCompound data = forgeplayer.getEntityData();
        if (data.getTag("AdminUtils") == null) {
            data.setTag("AdminUtils", new NBTTagCompound());
        }


        NBTTagCompound adminnbt = data.getCompoundTag("AdminUtils");
        if (args.<String>hasAny("off")) {
            try {
                adminnbt.setBoolean("Warnings", false);
                src.sendMessage(Text.builder().append(Text.of(TextColors.GOLD, "You have turned your world warnings off, meaning they will no longer appear for you until you use ", TextColors.GREEN, "/worldwarnings on")).onHover(TextActions.showText(Text.of(TextColors.BLUE,"Suggested Command: ", TextColors.GREEN, "/worldwarnings on"))).onClick(TextActions.suggestCommand("/worldwarnings on")).build());
                AdminUtilsSponge.getInstance().getLogger().info("Turned " + name + "'s worldwarnings off!");
            } catch (Exception e) {
                src.sendMessage(Text.of(TextColors.RED, "Failed to turn your world warnings off, please contact an admin if this is an issue!"));
                AdminUtilsSponge.getInstance().getLogger().info("Failed turning " + name + "'s worldwarnings off!");
                e.printStackTrace();
            }

        }else if (args.<String>hasAny("on")) {
                try {
                    adminnbt.setBoolean("Warnings", true);
                    src.sendMessage(Text.builder().append(Text.of(TextColors.GOLD, "You have turned your world warnings on, meaning they will appear on first block place in mining worlds until you do ", TextColors.GREEN, "/worldwarnings off")).onHover(TextActions.showText(Text.of(TextColors.BLUE,"Suggested Command: ", TextColors.GREEN, "/worldwarnings off"))).onClick(TextActions.suggestCommand("/worldwarnings off")).build());
                    AdminUtilsSponge.getInstance().getLogger().info("Turned " + name + "'s worldwarnings on!");
                } catch (Exception e) {
                    src.sendMessage(Text.of(TextColors.RED, "Failed to turn your world warnings on, please contact an admin if this is an issue!"));
                    AdminUtilsSponge.getInstance().getLogger().info("Failed turning " + name + "'s worldwarnings on!");
                    e.printStackTrace();
                }

        }
        return CommandResult.success();
    }
}
