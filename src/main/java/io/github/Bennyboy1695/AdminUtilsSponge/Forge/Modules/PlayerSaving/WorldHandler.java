package io.github.Bennyboy1695.AdminUtilsSponge.Forge.Modules.PlayerSaving;

import io.github.Bennyboy1695.AdminUtilsSponge.AdminUtilsSponge;
import io.github.Bennyboy1695.AdminUtilsSponge.Utils.RefStrings;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * io.github.Bennyboy1695.AdminUtilsSponge.Forge.Modules.PlayerSaving was created by Bennyboy1695 on 28/03/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */
public class WorldHandler {

    public static TextFormatting Color;
    public String broadcast = RefStrings.prefixForge + Color.RED + "Saving PlayerData, Expect Minor Lag . . .";

    String savepath = AdminUtilsSponge.getInstance().getSavePath();

    @SubscribeEvent
    public void worldSaved(WorldEvent.Save event) {
        if (event.getWorld().provider.getDimension() == 0) {
            for (Iterator loggedOffIterator = PlayerSave.loggedOffPlayers.iterator(); loggedOffIterator.hasNext(); loggedOffIterator.remove()) {
                String loggedInIterator = (String) loggedOffIterator.next();

                try {
                    this.savePlayer(loggedInIterator);
                } catch (IOException var7) {
                    var7.printStackTrace();
                }
            }

            Iterator loggedInIterator1 = PlayerSave.loggedInPlayers.iterator();
            //FMLCommonHandler.instance().getMinecraftServerInstance().sendMessage(new TextComponentTranslation(broadcast));
            FMLCommonHandler.instance().getMinecraftServerInstance().sendMessage(new TextComponentString(broadcast));

            while (loggedInIterator1.hasNext()) {
                String uuid = (String) loggedInIterator1.next();

                try {
                    this.savePlayer(uuid);
                } catch (IOException var6) {
                    var6.printStackTrace();
                }
            }
        }
    }

    private void savePlayer(String UUID) throws IOException {
        File currentFile = new File(DimensionManager.getCurrentSaveRootDirectory() + "/playerdata/" + UUID + ".dat");
        File saveFile = new File(DimensionManager.getCurrentSaveRootDirectory() + "/" + savepath + "/playerdata/" + UUID + ".dat");
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
