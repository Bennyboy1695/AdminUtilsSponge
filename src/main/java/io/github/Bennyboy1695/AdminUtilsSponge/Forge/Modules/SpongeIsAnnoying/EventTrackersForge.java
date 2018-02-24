package io.github.Bennyboy1695.AdminUtilsSponge.Forge.Modules.SpongeIsAnnoying;

import com.google.common.eventbus.Subscribe;
import io.github.Bennyboy1695.AdminUtilsSponge.AdminUtilsSponge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.event.world.ConstructPortalEvent;

import java.util.UUID;

/**
 * io.github.Bennyboy1695.AdminUtilsSponge.Forge.Modules.SpongeIsAnnoying was created by Bennyboy1695 on 06/05/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */
public class EventTrackersForge {

    @SubscribeEvent
    public void onBlockPlace(BlockEvent.PlaceEvent event) {
        if (AdminUtilsSponge.getInstance().getWorldWarningBoolean()) {
            String world = event.getWorld().getWorldInfo().getWorldName();
            String worldslist = String.join(", ", AdminUtilsSponge.getInstance().getWorldList());
            EntityPlayer player = event.getPlayer();
            String name = player.getName();
            UUID uuid = player.getPersistentID();
            EntityPlayer forgeplayer = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(uuid);
            String block = event.getPlacedBlock().getBlock().toString();
            NBTTagCompound data = forgeplayer.getEntityData();
            if (data.getTag("AdminUtils") == null) {
                data.setTag("AdminUtils", new NBTTagCompound());
            }
            NBTTagCompound adminnbt = data.getCompoundTag("AdminUtils");
            if (data.getCompoundTag("AdminUtils").hasNoTags()) {
                adminnbt.setTag("WorldWarnings", new NBTTagCompound());
            }
            Boolean playerboolean = adminnbt.getBoolean("Warnings");
            NBTTagCompound worldwarnings = adminnbt.getCompoundTag("WorldWarnings");
            Boolean warning = worldwarnings.getBoolean(world);
                if (!(worldslist.contains(world))) {
                    if (!playerboolean) {
                        if (!warning) {
                            AdminUtilsSponge.getInstance().getLogger().info(name + " has placed a " + block + " in a mining dimension - " + world);
                            player.sendMessage(new TextComponentString(TextFormatting.RED + "This world is considered to be a mining world and gets reset occasionally, please don't build a base here!"));
                            worldwarnings.setBoolean(world, true);
                        }
                    }
                }
            }
        }


}
