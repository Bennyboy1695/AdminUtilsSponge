package io.github.Bennyboy1695.AdminUtilsSponge.Forge.Modules.Inventory.Commands;

import io.github.Bennyboy1695.AdminUtilsSponge.Forge.Modules.Inventory.Handlers.EnderChestViewInventory;
import io.github.Bennyboy1695.AdminUtilsSponge.Utils.RefStrings;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLLog;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;
import java.util.List;

/**
 * io.github.Bennyboy1695.AdminUtilsSponge.Forge.Modules.Inventory.Commands was created by Bennyboy1695 on 22/03/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */

public class CMDEnderView extends CommandBase implements ICommand {

    @Override
    public String getName() {
        return "adminutilsopenend12345678";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/adminutilsopenend12345678 <player>";
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        return super.getTabCompletions(server, sender, args, pos);
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender ics, String[] args) throws CommandException {
        ItemStack itemStack;
            FMLLog.info(RefStrings.prefixForge + ics + "Just opened " + args + " enderchest!");
            EntityPlayerMP ep0 = getCommandSenderAsPlayer(ics);
            EntityPlayerMP ep = getPlayer(server, ics, args[0]);
        Sponge.getServer().getPlayer(ep.getUniqueID()).get().sendMessage(Text.of(ep.inventoryContainer.getInventory().equals(null)));
            ep0.displayGUIChest(new EnderChestViewInventory(ep));
        }

}
