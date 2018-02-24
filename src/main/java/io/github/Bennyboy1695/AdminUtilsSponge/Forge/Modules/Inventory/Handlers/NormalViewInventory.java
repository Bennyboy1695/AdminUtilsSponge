package io.github.Bennyboy1695.AdminUtilsSponge.Forge.Modules.Inventory.Handlers;

import io.github.Bennyboy1695.AdminUtilsSponge.Utils.RefStrings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLLog;

import javax.annotation.Nullable;
import java.lang.reflect.Method;


/**
 * io.github.Bennyboy1695.AdminUtils.Handlers was created by Bennyboy1695 on 12/01/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */
public class NormalViewInventory implements IInventory {

    private static final int slotMapping[] =
            {9, 10, 11, 12, 13, 14, 15, 16, 17,
                    18, 19, 20, 21, 22, 23, 24, 25, 26,
                    27, 28, 29, 30, 31, 32, 33, 34, 35,
                    0, 1, 2, 3, 4, 5, 6, 7, 8,
                    39, 38, 37, 36, -1, -1,-1,-1,-1,
                    40, 41, 42, 43, 44, 45, 46, -1, -1};

    private final EntityPlayerMP player;
    private final IInventory invPlayer;
    private final IInventory baubles;
    //private final IInventory offhand;
    private static Method baublesMethod = null;
    private static ItemStack offhand;

    public NormalViewInventory(EntityPlayerMP ep)
    {
        player = ep;
        invPlayer = ep.inventory;
        baubles = getBaubles(ep);
        offhand = ep.getHeldItemOffhand();
    }

    public boolean isUsableByPlayer(EntityPlayer ep)
    {
        return true;
    }

    @Override
    public int getSizeInventory()
    {
        return 9 * 6;
    }

    @Nullable
    private IInventory getInv(int slot) {
        if (slot == -1) {
            return null;
        }
        if (slot >= 40) {
            return baubles;
        }
        //if (!(slot == -106)) {
        //return offhand;
        //}
        return invPlayer;

    }

    public int getSlot(int slot)
    {
        return (slot == -1) ? -1 : (slot % 40);
    }

    @Override
    public ItemStack getStackInSlot(int i)
    {
        int j = slotMapping[i];
        IInventory inv = getInv(j);
        return (inv == null) ? null : inv.getStackInSlot(getSlot(j));
    }

    @Override
    public ItemStack decrStackSize(int i, int k)
    {
        int j = slotMapping[i];
        IInventory inv = getInv(j);
        return (inv == null) ? null : inv.decrStackSize(getSlot(j), k);
    }

    @Override
    public ItemStack removeStackFromSlot(int i)
    {
        int j = slotMapping[i];
        IInventory inv = getInv(j);
        return (inv == null) ? null : inv.removeStackFromSlot(getSlot(j));
    }

    @Override
    public void setInventorySlotContents(int i, @Nullable ItemStack is)
    {
        int j = slotMapping[i];
        IInventory inv = getInv(j);

        if(inv != null)
        {
            inv.setInventorySlotContents(getSlot(j), is);
            inv.markDirty();
        }
    }

    @Override
    public String getName()
    {
        return player.getName();
    }

    @Override
    public boolean hasCustomName()
    {
        return player.hasCustomName();
    }

    @Override
    public ITextComponent getDisplayName()
    {
        if (baubles == null) {
            return player.getDisplayName().appendText("'s Inv");
        } else{
            return player.getDisplayName().appendText("'s Inv &" + TextFormatting.BLUE + " Baubles");
        }
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public void markDirty()
    {
        invPlayer.markDirty();
        player.openContainer.detectAndSendChanges();
        if(baubles != null)
        {
            baubles.markDirty();
        }
    }

    @Override
    public void openInventory(EntityPlayer ep)
    {
    }

    @Override
    public void closeInventory(EntityPlayer ep)
    {
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack is) {
        if (i != -1) {
            return false;
        }
        int j = slotMapping[i];
        IInventory inv = getInv(j);
        return inv != null && inv.isItemValidForSlot(getSlot(j), is);
    }

    @Override
    public int getField(int id)
    {
        return 0;
    }

    @Override
    public void setField(int id, int value)
    {
    }

    @Override
    public int getFieldCount()
    {
        return 0;
    }

    @Override
    public void clear()
    {
        invPlayer.clear();

        if(baubles != null)
        {
            baubles.clear();
        }
    }

    /**
     * Retrieves the baubles inventory for the supplied player
     *
     * @author Azanor
     */
    public static IInventory getBaubles(EntityPlayer player)
    {
        IInventory ot = null;

        try
        {
            if(baublesMethod == null)
            {
                Class<?> fake = Class.forName("baubles.api.BaublesApi");
                baublesMethod = fake.getMethod("getBaubles", EntityPlayer.class);
            }

            ot = (IInventory) baublesMethod.invoke(null, player);
        }
        catch(Exception ex)
        {
            FMLLog.warning(RefStrings.prefixForge + "Either Baubles isn't installed, or i'm invoking the wrong method below");
            FMLLog.warning(RefStrings.prefixForge + "[Baubles API] Could not invoke baubles.api.BaublesApi method getBaubles");
        }

        return ot;
    }

}
