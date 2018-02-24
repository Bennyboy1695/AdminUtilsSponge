package io.github.Bennyboy1695.AdminUtilsSponge.Forge.Modules.PlayerSaving;

import io.github.Bennyboy1695.AdminUtilsSponge.AdminUtilsSponge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * io.github.Bennyboy1695.AdminUtilsSponge.Forge.Modules.PlayerSaving was created by Bennyboy1695 on 28/03/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */
public class PlayerHandler {

    Date date = new Date();
    DateFormat dateFormat;

    String savepath = AdminUtilsSponge.getInstance().getSavePath();
    String prefix = AdminUtilsSponge.getInstance().getPrefix();


    @SubscribeEvent
    public void playerJoined(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerSave.loggedInPlayers.add(event.player.getPersistentID().toString());
        PlayerSave.loggedOffPlayers.remove(event.player.getPersistentID().toString());
        FMLLog.info(event.player.getDisplayName().getUnformattedText() + " logged in and has been added to the list of players to save");
        File savedFile = new File(DimensionManager.getCurrentSaveRootDirectory() + savepath + "/" + event.player.getPersistentID().toString() + ".dat");
        if(!event.player.getEntityData().getString("sessionID").equals(PlayerSave.sessionID)) {
            if(savedFile.exists()) {
                try {
                    FMLLog.info("Attempting to load " + event.player.getDisplayName().getUnformattedText());
                    event.player.readFromNBT(CompressedStreamTools.readCompressed(new FileInputStream(savedFile)));
                    FMLLog.info("Player: " + event.player.getDisplayName().getUnformattedText() + " loaded");
                } catch (IOException var4) {
                    var4.printStackTrace();
                }
            }

            event.player.getEntityData().setString("sessionID", PlayerSave.sessionID);
            FMLLog.info("Set sessionID of " + event.player.getDisplayName().getUnformattedText() + " to " + event.player.getEntityData().getString("sessionID") + ". Server\'s sessionID: " + PlayerSave.sessionID);
        }

    }

    @SubscribeEvent
    public void playerLeft(PlayerEvent.PlayerLoggedOutEvent event) {
        PlayerSave.loggedOffPlayers.add(event.player.getPersistentID().toString());
        PlayerSave.loggedInPlayers.remove(event.player.getPersistentID().toString());
        FMLLog.info(event.player.getDisplayName().getUnformattedText() + " left and will be saved one last time");
    }

   //@SubscribeEvent(priority = EventPriority.HIGHEST)
   //public void playerDied (LivingDeathEvent event){
   //    //if (ConfigHandler.saveDeaths == true) {
   //    EntityPlayer player = (EntityPlayer) event.getEntity();
   //    String uuid = event.getEntity().getPersistentID().toString();
   //    UUID uuidNoString = event.getEntity().getUniqueID();
   //    Player messenger = Sponge.getGame().getServer().getPlayer(uuidNoString).get();
   //        if (event.getEntity() instanceof EntityPlayer) {
   //            if (!player.inventoryContainer.getInventory().isEmpty()) {
   //                    try {
   //                        this.saveDeathOfPlayer(uuid);
   //                        messenger.sendMessage(
   //                                Text.builder().append(Text.of(TextColors.WHITE, "[", TextColors.AQUA, prefix, TextColors.WHITE, "] "
   //                                        , TextColors.RED, "You died, so we saved a copy of your inventory for use in emergencies! ", TextStyles.ITALIC, TextColors.GRAY, "Hover for more info!"))
   //                                        .onHover(TextActions.showText(Text.of(TextColors.GREEN, "Death occurred at : ", TextColors.GOLD,
   //                                                event.getEntity().getPosition().toString().replaceAll("B.*x", "x").replaceAll("}", ""), TextColors.GREEN, " in world : ", TextColors.GOLD, event.getEntity().getEntityWorld().toString().replaceAll("WorldServer.*L", "L").replaceAll(", DimensionType.*}", "")))).onClick(TextActions.suggestCommand("/back")).append(Text.NEW_LINE, Text.of(TextColors.BLUE, "Suggested Command: ", TextColors.WHITE, "/back")).build());
   //                        FMLLog.info(event.getEntity().getDisplayName().getUnformattedText() + " has died, saving their playerdata!");
   //                    } catch (Exception e) {
   //                        FMLLog.info(event.getEntity().getDisplayName().getUnformattedText() + " has died, but we failed to save their playerdata!");
   //                        FMLLog.warning(e.getMessage());
   //                    }
   //                } else {
   //                    messenger.sendMessage(Text.of(TextColors.RED, "You died, but your inventory was empty so we didn't save it!"));
   //            }
   //        }
   //}


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
