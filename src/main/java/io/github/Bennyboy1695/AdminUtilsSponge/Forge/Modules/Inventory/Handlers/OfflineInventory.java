package io.github.Bennyboy1695.AdminUtilsSponge.Forge.Modules.Inventory.Handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

/**
 * io.github.Bennyboy1695.AdminUtilsSponge.Forge.Modules.Inventory.Handlers was created by Bennyboy1695 on 10/10/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */
public class OfflineInventory implements IInventory {

    private static final int slotMapping[] =
            {9, 10, 11, 12, 13, 14, 15, 16, 17,
                    18, 19, 20, 21, 22, 23, 24, 25, 26,
                    27, 28, 29, 30, 31, 32, 33, 34, 35,
                    0, 1, 2, 3, 4, 5, 6, 7, 8,
                    39, 38, 37, 36, -1, -1,-1,-1,-1,
                    40, 41, 42, 43, 44, 45, 46, -1, -1};

    private final EntityPlayerMP viewer;
    private final EntityPlayer player;
    private final IInventory invPlayer;

    public OfflineInventory(EntityPlayerMP viewer, EntityPlayer player) {
        this.viewer = viewer;
        this.player = player;
        invPlayer = player.inventory;
    }

    @Nullable
    private IInventory getInv(int slot) {
        if (slot == -1) {
            return null;
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
    public int getSizeInventory() {
        if (player == null || player.isDead) {
            viewer.closeScreen();
        }

        return 9 * 6;
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
    public void setInventorySlotContents(int var1, ItemStack var2) {
        if (player == null || player.isDead) {
            viewer.entityDropItem(var2, 0.5F);
            viewer.closeScreen();
            return;
        }

        if (var1 >= 0 && var1 < 27) {
            player.inventory.mainInventory[var1 + 9] = var2;
        } else if (var1 >= 27 && var1 < 36) {
            player.inventory.mainInventory[var1 - 27] = var2;
        } else if (var1 >= 36 && var1 < 40) {
            player.inventory.armorInventory[39 - var1] = var2;
        } else {
            viewer.entityDropItem(var2, 0.5F);
        }
    }

    @Override
    public int getInventoryStackLimit() {
        if (player == null || player.isDead) {
            viewer.closeScreen();
            return 64;
        }

        return player.inventory.getInventoryStackLimit();
    }

    @Override
    public void markDirty()
    {
        invPlayer.markDirty();
        player.openContainer.detectAndSendChanges();
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return false;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return true;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public boolean hasCustomName() {
        return player.hasCustomName();
    }

    @Override
    public ITextComponent getDisplayName() {
        return player.getDisplayName();
    }
}
