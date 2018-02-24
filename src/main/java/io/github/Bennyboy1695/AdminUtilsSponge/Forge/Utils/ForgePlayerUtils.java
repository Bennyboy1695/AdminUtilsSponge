package io.github.Bennyboy1695.AdminUtilsSponge.Forge.Utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.util.UUID;

/**
 * io.github.Bennyboy1695.AdminUtilsSponge.Forge.Utils was created by Bennyboy1695 on 16/05/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */
public class ForgePlayerUtils {

    public static String getUsernameFromPlayer(EntityPlayer player)
    {
        return player.getEntityWorld().isRemote ? "" : UsernameCache.getLastKnownUsername(getUUIDFromPlayer(player));
    }

    public static EntityPlayer getPlayerFromUsername(String username)
    {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(username);
    }

    public static EntityPlayer getPlayerFromUUID(String uuid)
    {
        return getPlayerFromUsername(getUsernameFromUUID(uuid));
    }

    public static EntityPlayer getPlayerFromUUID(UUID uuid)
    {
        return getPlayerFromUsername(getUsernameFromUUID(uuid));
    }

    public static UUID getUUIDFromPlayer(EntityPlayer player)
    {
        return player.getGameProfile().getId();
    }

    public static String getUsernameFromUUID(String uuid)
    {
        return UsernameCache.getLastKnownUsername(UUID.fromString(uuid));
    }

    public static String getUsernameFromUUID(UUID uuid)
    {
        return UsernameCache.getLastKnownUsername(uuid);
    }

}
