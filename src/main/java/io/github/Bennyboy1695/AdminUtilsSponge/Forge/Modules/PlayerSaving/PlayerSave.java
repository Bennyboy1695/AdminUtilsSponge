package io.github.Bennyboy1695.AdminUtilsSponge.Forge.Modules.PlayerSaving;

import io.github.Bennyboy1695.AdminUtilsSponge.Forge.Modules.SpongeIsAnnoying.EventTrackersForge;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.HashSet;
import java.util.logging.Logger;

/**
 * io.github.Bennyboy1695.AdminUtilsSponge.Forge.Modules.PlayerSaving was created by Bennyboy1695 on 28/03/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */
public class PlayerSave {

    public static final String sessionID = Long.toString(System.currentTimeMillis());
    public static HashSet loggedInPlayers = new HashSet();
    public static HashSet loggedOffPlayers = new HashSet();
    public static Logger logger;

    public static void preinit() {
        MinecraftForge.EVENT_BUS.register(new WorldHandler());
        FMLCommonHandler.instance().bus().register(new PlayerHandler());

    }
}
