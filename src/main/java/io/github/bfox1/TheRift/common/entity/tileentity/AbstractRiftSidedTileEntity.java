package io.github.bfox1.TheRift.common.entity.tileentity;

import io.github.bfox1.TheRift.riftessence.RiftLinkedSide;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bfox1 on 11/10/2016.
 */
public abstract class AbstractRiftSidedTileEntity extends AbstractRiftTileEntity implements ISidedInventory, ITickable
{

    //public ItemStack[] slots;

    public NonNullList<ItemStack> slots;


    public AbstractRiftSidedTileEntity()
    {
        //this.slots = new ItemStack[getSizeInventory()];
        this.slots =  NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
    }
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        NBTTagList list = new NBTTagList();

        int i = 0;
        for(ItemStack stack : slots)
        {
            if(!stack.isEmpty())
            {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setByte("Slot", (byte)i);
                stack.writeToNBT(itemTag);
                list.appendTag(itemTag);
            }
            i++;
        }

        compound.setTag("Items", list);

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        NBTTagList list = compound.getTagList("Items", 10);
        this.slots = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);

        for(int i = 0; i < list.tagCount(); ++i)
        {
            NBTTagCompound item = list.getCompoundTagAt(i);
            byte b0 = item.getByte("Slot");




            if(b0 >= 0 && b0 < this.slots.size())
            {
                this.slots.set(b0, new ItemStack(item));
            }
        }
    }

    @Override
    public boolean isInventorySided() {
        return true;
    }

    @Override
    public int getSizeInventory() {
        return slots.size();
    }

    @Nullable
    @Override
    public ItemStack getStackInSlot(int index) {
        return slots.get(index);
    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.slots, index, count);

    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(this.slots, index);
    }


    public void init()
    {

    }

}
