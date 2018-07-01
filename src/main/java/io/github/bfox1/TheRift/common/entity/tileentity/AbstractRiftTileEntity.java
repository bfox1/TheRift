package io.github.bfox1.TheRift.common.entity.tileentity;

import io.github.bfox1.TheRift.riftessence.RiftLinkedSide;
import io.github.bfox1.TheRift.common.util.LogHelper;
import io.github.bfox1.TheRift.api.riftessence.IRiftLinkableContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by bfox1 on 11/10/2016.
 */
public abstract class AbstractRiftTileEntity extends TileEntityLockable implements IRiftLinkableContainer
{
    public RiftLinkedSide[] linkedSides;
    //public ItemStack[] slots;
    public NonNullList<ItemStack> slots;

    public AbstractRiftTileEntity()
    {
        this.linkedSides = new RiftLinkedSide[6];
        //this.slots = new LinkedList<ItemStack>();

        this.init();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);


        NBTTagList sidedList = new NBTTagList();

        int i = 0;
        for(RiftLinkedSide side : linkedSides)
        {
            if(side != null)
            {
                NBTTagCompound sideTag = new NBTTagCompound();
                System.out.println(side.isMasterLink());
                sideTag.setString("Side", side.getX() + ":" + side.getY() + ":" + side.getZ() + ":" + side.getFace().name() + ":" + side.getAction().getId() + ":" + side.isMasterLink());
                sidedList.appendTag(sideTag);
            }
            i++;
        }
        compound.setTag("Sides", sidedList);


        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);


        NBTTagList sList = compound.getTagList("Sides", 10);
        this.linkedSides = new RiftLinkedSide[6];
        for(int i = 0; i < sList.tagCount(); ++i)
        {
            NBTTagCompound slot = sList.getCompoundTagAt(i);

            String s = slot.getString("Side");
            if(!s.equalsIgnoreCase(""))
            {
                String[] splitBlocks = s.split(":");


                RiftLinkedSide side = new RiftLinkedSide(Integer.parseInt(splitBlocks[0]),
                        Integer.parseInt(splitBlocks[1]), Integer.parseInt(splitBlocks[2]), EnumFacing.byName(splitBlocks[3]),
                         RiftLinkedSide.EnumRiftAction.getByID(Integer.parseInt(splitBlocks[4])), Boolean.valueOf(splitBlocks[5]));

                System.out.println(splitBlocks[5] + " : " + Boolean.valueOf(splitBlocks[5]));
                this.linkedSides[i] = side;
            }
        }

    }

    //###############Rift Implementations#################//
    //#####################Beginning######################//

    /**
     * Used to get the Array of RiftLinkedSide object
     * @return
     */
    public RiftLinkedSide[] getLinkedSides()
    {
        return linkedSides;
    }

    /**
     * Checks to see if This Tile Entity has any connected Rift Links
     * @return
     */
    public boolean hasLinkedTileEntities()
    {

        if(this.linkedSides != null)
            for(RiftLinkedSide side : this.linkedSides)
            {
                if(side != null)
                {
                    return true;
                }
            }
        return false;
    }

    /**
     * Get a Linked Side by index.
     * @param id
     * @return
     */
    public RiftLinkedSide getLinkedSide(int id)
    {
        RiftLinkedSide side = null;
        try
        {
            return linkedSides[id];
        }
        catch (Exception e)
        {

            LogHelper.error("Error has occured. The Linked Side your looking is out of bounds. limit is between 0-5");
        }

        return null;
    }

    /**
     * Removes a linked side from blockpos and enum face.
     * @param pos
     * @param face
     */
    @Override
    public void removeLinkedSide(BlockPos pos, EnumFacing face)
    {

        TileEntity entity = getWorld().getTileEntity(pos);

        int i = 0;
        for(RiftLinkedSide side : linkedSides)
        {
            if(side != null)
            {
                TileEntity sidedEntity = getWorld().getTileEntity(new BlockPos(side.getX(), side.getY(), side.getZ()));

                if (entity == sidedEntity)
                {
                    if (face != null)
                    {
                        if (face == side.getFace())
                        {
                            linkedSides[i] = null;
                        }
                    }
                    else
                    {
                        linkedSides[i] = null;
                    }

                }
                i++;
            }
            this.markDirty();
        }
    }

    /**
     * Deliberately Clears the tile entity of linked sides
     */
    @Override
    public void clearLinkedSides()
    {
        this.linkedSides = new RiftLinkedSide[this.linkedSides.length];
    }

    /**
     * Checks if the Linked Side can be added to Tile Entity,
     * Returns true if possible, no if cannot.
     * @param eSide
     * @return
     */
    @Override
    public boolean canAddLinkedSide(RiftLinkedSide eSide)
    {
        boolean doesContainSpot = false;
        for(RiftLinkedSide side : linkedSides)
        {
            if(side == null)
            {
                doesContainSpot = true;
            }

            if(side != null)
            {
                if(eSide.getX() == side.getX() && eSide.getY() == side.getY() && side.getZ() == side.getZ())
                {
                    if(side.getFace() == eSide.getFace())
                    {
                        return false;
                    }
                }
            }
        }
        return doesContainSpot;
    }

    /**
     * Will add Linked Side to Tile Entity. This Method DOES NOT perform a check
     * @param side
     */
    @Override
    public void addLinkedSide(RiftLinkedSide side)
    {
        for(int i = 0; i< linkedSides.length; i++)
        {

            if(linkedSides[i] == null)
            {
                linkedSides[i] = side;
                break;
            }
        }
    }

    public RiftLinkedSide generateHashCode(RiftLinkedSide side)
    {
        IInventory inventory = (IInventory)world.getTileEntity(new BlockPos(side.getX(), side.getY(), side.getZ()));

        int hashCode = 0;

        if(inventory.isEmpty())
        {
            hashCode = toHashCode(inventory);
        }
        else
        {
            NonNullList<ItemStack> tempList = NonNullList.withSize(inventory.getSizeInventory(), ItemStack.EMPTY);

            for(int i = 0; i < inventory.getSizeInventory(); i++)
            {
                tempList.set(i, inventory.getStackInSlot(i));
                inventory.setInventorySlotContents(i, ItemStack.EMPTY);
            }

            hashCode =  toHashCode(inventory);

            for(int i = 0; i < inventory.getSizeInventory(); i++)
            {
                inventory.setInventorySlotContents(i, tempList.get(i));
            }
            tempList.clear();
        }

        side.setHashCode(hashCode);
        return side;
    }

    private int toHashCode(IInventory iInventory)
    {
        Object object = (Object)iInventory;

        return object.hashCode();
    }

    /**
     * Extract an Item from a side.
     * This is an exclusive method for the Rift Chest only and to soon be implemented into other Machines.
     * @param inventory The Inventory
     * @param stack The ItemStack we are checking
     * @param facing The Face of the Inventory
     * @param slot The Slot in which the Item is located
     * @param isMasterLink This is a very important boolean, this checks if the Rift Tile Entity is Home or guest.
     *                     If boolean is false, this method will not fire in the connected TE, HOWEVER, if True, the
     *                     TE will then fire when called.
     * @return
     */
    @Override
    public boolean canExtractFromSide(IInventory inventory, ItemStack stack, EnumFacing facing, int slot, boolean isMasterLink)
    {
        if(isMasterLink)
        {
            return false;
        }

        if(inventory instanceof ISidedInventory)
        {
            ISidedInventory sidedInventory = (ISidedInventory) inventory;

            return sidedInventory.canExtractItem(slot, stack, facing);
        }
        else
        {
            return true;
        }
    }

    @Override
    public boolean canInsertFromSide(IInventory inventory, ItemStack stack, EnumFacing facing, int slot, boolean isMasterLink)
    {
        if(isMasterLink)
        {
            return false;
        }

        if(inventory instanceof ISidedInventory)
        {
            ISidedInventory sidedInventory = (ISidedInventory) inventory;

            return sidedInventory.canInsertItem(slot, stack, facing);
        }
        else
        {
            return true;
        }
    }
    //###############Rift Implementations#################//
    //#####################End######################//

    @Override
    public boolean isInventorySided() {
        return this instanceof ISidedInventory;
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

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack)
    {
        this.slots.set(index, stack);
        if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());        }
    }
    public abstract void init();
}
