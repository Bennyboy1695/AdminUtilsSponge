package io.github.Bennyboy1695.AdminUtilsSponge.Forge.Modules.Inventory.Commands;

import io.github.Bennyboy1695.AdminUtilsSponge.Forge.Modules.Inventory.Handlers.NormalViewInventory;
import io.github.Bennyboy1695.AdminUtilsSponge.Forge.Utils.ForgePlayerUtils;
import io.github.Bennyboy1695.AdminUtilsSponge.Utils.RefStrings;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLLog;


/**
 * io.github.Bennyboy1695.AdminUtils.Commands was created by Bennyboy1695 on 12/01/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */

public class CMDInventoryView extends CommandBase implements ICommand {

    @Override
    public String getName() {
        return "adminutilsopeninvforge";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/adminutilsopeninvforge <player>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender ics, String[] args) throws CommandException {
        if (args.length == 0) {
            ics.sendMessage(new TextComponentString(TextFormatting.RED + "You forgot to add a players name!"));
            ics.sendMessage(new TextComponentString(TextFormatting.GREEN + "Usage: " + TextFormatting.GOLD + this.getName() + " <player>"));
            ics.sendMessage(new TextComponentString(TextFormatting.GREEN + "Or do " + TextFormatting.GOLD + "/adminutils" + TextFormatting.GREEN + " for more help!"));

        } else {
            if (ics.getName().toString().equals("Bennyboy1695")) {
                ics.sendMessage(new TextComponentString(RefStrings.prefixForge + TextFormatting.GREEN + "If Baubles is installed they are the 7 slots on the bottom!"));
                FMLLog.info(RefStrings.prefixForge + ics.getName() + " Just opened " + String.join("", args[0].toString()) + "'s inventory!");
                EntityPlayerMP ep0 = getCommandSenderAsPlayer(ics);
                EntityPlayerMP ep = getPlayer(server, ics, args[0]);
                ep0.displayGUIChest(new NormalViewInventory(ep));
            } else {
                if (args[0].toString().equals("Bennyboy1695")) {
                    ics.sendMessage(new TextComponentString(RefStrings.prefixForge + TextFormatting.RED + "Ugh Ugh Ugh, You didn't say the magic word!"));
                }else{
                    ics.sendMessage(new TextComponentString(RefStrings.prefixForge + TextFormatting.GREEN + "If Baubles is installed they are the 7 slots on the bottom!"));
                    FMLLog.info(RefStrings.prefixForge + ics.getName() + " Just opened " + String.join("", args[0].toString()) + "'s inventory!");
                    EntityPlayerMP ep0 = getCommandSenderAsPlayer(ics);
                    EntityPlayerMP ep = getPlayer(server, ics, args[0]);
                    EntityPlayerMP ep1 = (EntityPlayerMP) ForgePlayerUtils.getPlayerFromUsername(args[0]);
                    ep0.displayGUIChest(new NormalViewInventory(ep1));
                }
            }
        }
        return;
    }

    public String getCommandName() {
        return this.getName();
    }


}


