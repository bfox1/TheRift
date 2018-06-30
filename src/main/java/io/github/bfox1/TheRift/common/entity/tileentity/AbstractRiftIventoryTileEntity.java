package io.github.bfox1.TheRift.common.entity.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

/**
 * Created by bfox1 on 11/10/2016.
 */
public abstract class AbstractRiftIventoryTileEntity extends AbstractRiftTileEntity implements IInventory, ITickable
{

    //public NonNullList<ItemStack> slots;


    public AbstractRiftIventoryTileEntity()
    {
        this.slots = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
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
                //===NOTES==//
                /*
                ItemStack.loadItemStackFromNBT(item) has changed to
                new ItemStack(item)
                 */
                this.slots.set(b0, ItemStack.EMPTY);
            }
        }

    }

    @Deprecated
    public boolean checkSlotNull(int id)
    {
        try
        {
            return slots.get(id) != null;
        }
        catch (Exception e)
        {
            System.out.println("An Error has Occurred. This is Debug information and dont worry.");
         return false;
        }
    }

    public boolean isInventorySided()
    {
        return false;
    }

    @Override
    public void openInventory(EntityPlayer player)
    {
    }

    @Override
    public void closeInventory(EntityPlayer player)
    {
    }

    public void init()
    {

    }

}
