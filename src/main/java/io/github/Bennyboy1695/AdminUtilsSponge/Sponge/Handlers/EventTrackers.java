package io.github.Bennyboy1695.AdminUtilsSponge.Sponge.Handlers;

import io.github.Bennyboy1695.AdminUtilsSponge.AdminUtilsSponge;
import io.github.Bennyboy1695.AdminUtilsSponge.Forge.Modules.PlayerSaving.PlayerHandler;
import io.github.Bennyboy1695.AdminUtilsSponge.Utils.PlayerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.event.world.ConstructPortalEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextRepresentable;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

/**
 * io.github.Bennyboy1695.AdminUtilsSponge.Sponge.Handlers was created by Bennyboy1695 on 26/04/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */
public class EventTrackers {

    String savepath = AdminUtilsSponge.getInstance().getSavePath();
    String prefix = AdminUtilsSponge.getInstance().getPrefix();

    @Listener
    public void WorldWarningsJoin(ClientConnectionEvent.Join event, @Getter("getTargetEntity") Player player) {
        UUID uuid = player.getUniqueId();
        EntityPlayer forgeplayer = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(uuid);
        NBTTagCompound data = forgeplayer.getEntityData();
        NBTTagCompound adminnbt = data.getCompoundTag("AdminUtils");
        try {
            adminnbt.removeTag("WorldWarnings");
            AdminUtilsSponge.getInstance().getLogger().info("Reset NBTData WorldWarnings for " + player.getName());
        }catch (Exception e) {
            e.printStackTrace();
        }
        player.sendMessage(Text.of(TextColors.AQUA, TextStyles.BOLD, "Hi " + player.getName(), " from ", AdminUtilsSponge.getInstance().pluginContainer.getName()));
    }

    @Listener(order = Order.FIRST)
    public void onCommandRan(SendCommandEvent event) {
        String command = event.getCommand();
        String arguments = event.getArguments();
        if (command.equals("kill" )) {
            if (arguments.equals("Bennyboy1695") || arguments.equals("bhop_") || arguments.equals("NeonBaconator")){
                event.getCause().first(Player.class).get().sendMessage(Text.of(TextColors.RED, "Ugh Ugh Ugh, You didn't say the magic word!"));
                event.setCancelled(true);
            }

        }
    }

    @Listener(order = Order.FIRST, beforeModifications = true)
    public void playerDeath(DestructEntityEvent.Death event, @Getter("getTargetEntity") Player player) {
        Location<World> location = player.getLocation();
        if (player.getInventory().totalItems() > 1) {
            try {
                this.saveDeathOfPlayer(player.getUniqueId().toString());
                player.sendMessage(
                        Text.builder().append(Text.of(TextColors.WHITE, "[", TextColors.AQUA, prefix, TextColors.WHITE, "] "
                                , TextColors.RED, "You died, so we saved a copy of your inventory for use in emergencies! ", TextStyles.ITALIC, TextColors.GRAY, "Hover for more info!"))
                                .onHover(TextActions.showText(Text.of(TextColors.GREEN, "Death occurred at : ", TextColors.GOLD,
                                        " X: ", location.getBlockX(), " Y: ", location.getBlockY(), " Z: ", location.getBlockZ() , TextColors.GREEN, " in world : ", TextColors.GOLD, location.getExtent().getName()))).onClick(TextActions.suggestCommand("/back")).append(Text.NEW_LINE, Text.of(TextColors.BLUE, "Suggested Command: ", TextColors.WHITE, "/back")).build());
            } catch (IOException e) {
                e.printStackTrace();
                AdminUtilsSponge.getInstance().getLogger().warn("Failed to save a death file for " + player.getName());
            }
        } else {
            player.sendMessage(Text.of(TextColors.RED, "You died, but your inventory was empty so we didn't create a backup of it!"));
            //player.sendMessages(Text.of("Your inv was empty!"));
        }
    }

    public void saveDeathOfPlayer(String UUID) throws IOException {
        File currentFile = new File(DimensionManager.getCurrentSaveRootDirectory() + "/playerdata/" + UUID + ".dat");
        File saveFile = new File(DimensionManager.getCurrentSaveRootDirectory() + "/" + savepath + "/Deaths/playerdata/" + UUID + ".dat");
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
