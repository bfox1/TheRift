package io.github.bfox1.TheRift.common.inventory;

import io.github.bfox1.TheRift.common.items.ItemRiftBag;
import io.github.bfox1.TheRift.common.items.RiftItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

public class InventoryRiftBag implements IInventory
{
    private NonNullList<ItemStack> itemStackSlot;

    private NonNullList<ItemStack> filteredStack;

    private final ItemStack riftStack;
    private final int code;

    private int tabIndex;

    public InventoryRiftBag(ItemStack stack)
    {
        this.itemStackSlot = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        this.filteredStack = NonNullList.withSize(9, ItemStack.EMPTY);
        this.riftStack = stack;
        this.tabIndex = ItemRiftBag.getTabIndex(stack).getIndex();
        this.code = itemStackSlot.hashCode();
    }

    public ItemStack getFilteredItem(int index)
    {
        return filteredStack.get(index);
    }

    public void setFilteredStack(int i, ItemStack stack)
    {
        filteredStack.set(i,stack);
    }

    @Override
    public int getSizeInventory()
    {
        return 32;
    }

    @Override
    public boolean isEmpty()
    {
        return code == itemStackSlot.hashCode();
    }

    @Override
    public ItemStack getStackInSlot(int index)
    {
        return itemStackSlot.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        return ItemStackHelper.getAndSplit(itemStackSlot, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(itemStackSlot, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        this.itemStackSlot.set(index, stack);
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public void markDirty()
    {

    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return false;
    }

    /**
     * These are for usage for contains(Server Side)
     * Getting a field is represented by anything this Class may store.
     * @param id
     * @return
     */
    @Override
    public int getField(int id) {
        return 0;
    }

    /**
     * To set field information that comes from the server.
     * @param id
     * @param value
     */
    @Override
    public void setField(int id, int value)
    {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear()
    {
        this.itemStackSlot = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
    }

    @Override
    public String getName()
    {
        return "Rift Pouch";
    }

    @Override
    public boolean hasCustomName()
    {
        return false;
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return null;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        NBTTagList inventory = new NBTTagList();

        int i = 0;

        for(ItemStack stack : itemStackSlot)
        {
            if(!stack.isEmpty())
            {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setByte("Slot", (byte)i);
                stack.writeToNBT(itemTag);
                inventory.appendTag(itemTag);
            }
            i++;
        }

        NBTTagList filteredItems = new NBTTagList();

        int f = 0;

        for(ItemStack stack : filteredStack)
        {
            if(!stack.isEmpty())
            {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setByte("Slot", (byte)f);
                stack.writeToNBT(itemTag);
                inventory.appendTag(itemTag);
            }
            f++;
        }
        compound.setTag("Item", inventory);
        compound.setTag("Filtered", filteredItems);

        return compound;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        NBTTagList list = compound.getTagList("Items", 10);

        NBTTagList fList = compound.getTagList("Filtered", 10);

        this.filteredStack = NonNullList.withSize(9, ItemStack.EMPTY);

        this.itemStackSlot = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);

        for(int i = 0; i < list.tagCount(); ++i)
        {
            NBTTagCompound item = list.getCompoundTagAt(i);

            byte b0 = item.getByte("Slot");

            if(b0 >= 0 && b0 < this.itemStackSlot.size())
            {
                this.itemStackSlot.set(b0, new ItemStack(item));
            }
        }

        for(int i = 0; i < fList.tagCount(); ++i)
        {
            NBTTagCompound item = list.getCompoundTagAt(i);

            byte b0 = item.getByte("Slot");

            if(b0 >= 0 && b0 < this.filteredStack.size())
            {
                this.filteredStack.set(b0, new ItemStack(item));
            }
        }
    }

    public ItemStack getRiftStack() {
        return riftStack;
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }
}
