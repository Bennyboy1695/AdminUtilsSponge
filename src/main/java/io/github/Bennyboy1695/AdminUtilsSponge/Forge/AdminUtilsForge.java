package io.github.Bennyboy1695.AdminUtilsSponge.Forge;

import io.github.Bennyboy1695.AdminUtilsSponge.Forge.Modules.Inventory.Commands.CMDEnderView;
import io.github.Bennyboy1695.AdminUtilsSponge.Forge.Modules.Inventory.Commands.CMDInventoryView;
import io.github.Bennyboy1695.AdminUtilsSponge.Forge.Modules.PlayerSaving.PlayerSave;
import io.github.Bennyboy1695.AdminUtilsSponge.Forge.Modules.SpongeIsAnnoying.EventTrackersForge;
import io.github.Bennyboy1695.AdminUtilsSponge.Utils.RefStrings;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

/**
 * io.github.Bennyboy1695.AdminUtilsSponge.Forge was created by Bennyboy1695 on 21/03/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */

@Mod(modid = "adminutilsforge", name = "AdminUtilsForge", acceptableRemoteVersions = "*")
public class AdminUtilsForge {

    @EventHandler
    public void serverStart(FMLServerStartingEvent event) {
        try {
            event.registerServerCommand(new CMDInventoryView());
            FMLLog.info(RefStrings.prefixForge + "Successfully added CMDInventoryView");
        } catch (Exception e) {
            e.printStackTrace();
            FMLLog.warning(RefStrings.prefixForge + "Failed to add CMDInventoryView");
        }
        try {
            event.registerServerCommand(new CMDEnderView());
            FMLLog.info(RefStrings.prefixForge + "Successfully added CMDEnderView");
        } catch (Exception e) {
            e.printStackTrace();
            FMLLog.warning(RefStrings.prefixForge + "Failed to add CMDEnderView");
        }
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        PlayerSave.preinit();
        MinecraftForge.EVENT_BUS.register(new EventTrackersForge());
    }

}
