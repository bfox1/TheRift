package io.github.bfox1.TheRift.common.entity.tileentity;

import io.github.bfox1.TheRift.api.riftessence.IRiftLinkableContainer;
import io.github.bfox1.TheRift.common.blocks.RiftChest;
import io.github.bfox1.TheRift.riftessence.RiftLinkedSide;
import io.github.bfox1.TheRift.server.container.ContainerTestBlock;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;

/**
 * Created by bfox1 on 11/5/2016.
 *
 * ===NOTES===
 * Itemstack.stackSize is no longer public access, It is now a method. Method for getting Stack size is ItemStack#getCount()
 */
public class TileEntityRiftChest extends AbstractRiftIventoryTileEntity
{


    private RiftLinkedSide[] connectedTEntities = new RiftLinkedSide[6];

    private  HashMap<EntityPlayer, RiftLinkedSide> sideHashMap = new HashMap<>();
    private int tick =0 ;

    private boolean flag = false;


    @Override
    public int getSizeInventory()
    {
        return 32;
    }

    @Override
    public boolean isEmpty()
    {
        for(ItemStack stack : slots)
        {
            if(!stack.isEmpty())
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index)
    {
        return slots.get(index);

    }


    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        if(!this.slots.get(index).isEmpty())
        {
            ItemStack stack;

            if(this.slots.get(index).getCount() <= count)
            {
                stack = this.slots.get(index);
                this.slots.set(index, ItemStack.EMPTY);
                return stack;
            }
            else
            {
                stack = this.slots.get(index).splitStack(count);

                if(this.slots.get(index).getCount() == 0)
                {
                    this.slots.set(index, ItemStack.EMPTY);
                }
            }

            return stack;
        }
        return null;
    }

    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        ItemStack stack = this.slots.get(index);
        this.slots.set(index, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        this.slots.set(index, stack);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return false;
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
        return "RiftChest";
    }

    @Override
    public boolean hasCustomName()
    {
        return true;
    }



    private boolean addToSlot(ItemStack stack)
    {
        int i = 0;
        //Start Iteration of slots
        if(stack.isEmpty())
        {
            return false;
        }
        for(ItemStack iStack : this.slots)
        {
            if(!iStack.isEmpty())  //If stack is not null; will begin to identify stack
            {
                if (iStack.isItemEqual(stack))  //If Stack equals comparedStack; will begin to calculate slot placement
                {
                    if (iStack.getCount() <= iStack.getMaxStackSize())
                    {
                        int result = iStack.getCount() + stack.getCount();

                        if(result <= stack.getMaxStackSize())
                        {
                            iStack.setCount(iStack.getCount() + stack.getCount());
                            //iStack.stackSize += stack.stackSize; //No longer in 1.11+ | Works in 1.10.X
                            this.slots.set(i, iStack);
                            return true;
                        }
                        else if(result >= stack.getMaxStackSize())
                        {
                            int remainder = result - iStack.getMaxStackSize();
                            //iStack.stackSize = iStack.getMaxStackSize(); //No longer in 1.11+ | Works in 1.10.X
                            iStack.setCount(iStack.getMaxStackSize());
                            this.slots.set(i, iStack);

                            if(remainder == 0)
                            {
                                stack = ItemStack.EMPTY;
                                return true;
                            }
                            else
                            {
                                stack.setCount(remainder);
                                //stack.stackSize = remainder // No longer in 1.11+ | Works in 1.10.X
                            }
                        }
                    }
                }
            }
            else
            {
                this.slots.set(i, stack.copy());
                return true;
            }
            i++;
        }

        return false;
    }

    private boolean addStackToInventory(ItemStack stack, IInventory inventory)
    {
        int i = 0;
        //Start Iteration of slots
        if(stack.isEmpty())
        {
            return false;
        }
        for(i = 0; i< inventory.getSizeInventory(); i++)
        {
            ItemStack iStack = inventory.getStackInSlot(i);
            if(!iStack.isEmpty())  //If stack is not null; will begin to identify stack
            {
                if (iStack.isItemEqual(stack))  //If Stack equals comparedStack; will begin to calculate slot placement
                {
                    if (iStack.getCount() <= iStack.getMaxStackSize())
                    {
                        int result = iStack.getCount() + stack.getCount();

                        if(result <= stack.getMaxStackSize())
                        {
                            //iStack.stackSize += stack.stackSize; // No longer in 1.11+ | Works in 1.10.X
                            iStack.setCount(iStack.getCount() + stack.getCount());
                            inventory.setInventorySlotContents(i, iStack);
                            return true;
                        }
                        else if(result >= stack.getMaxStackSize())
                        {
                            int remainder = result - iStack.getMaxStackSize();
                            //iStack.stackSize = iStack.getMaxStackSize(); // No longer in 1.11+ | Works in 1.10.X
                            iStack.setCount(iStack.getMaxStackSize());
                            inventory.setInventorySlotContents(i, iStack);
                            if(remainder == 0)
                            {
                                stack.setCount(0);
                                return true;
                            }
                            stack.setCount(remainder);
                        }
                    }
                }
            }
            else
            {
                inventory.setInventorySlotContents(i, stack.copy());
                stack.setCount(0);
                return true;
            }

        }

        return false;
    }

    /**
     * This Method is to check all Linked Inventories to see if
     */
    public void checkLinkedInventorySides()
    {
      //  int z = 0;
        if(this.linkedSides != null)
        {
            for (RiftLinkedSide side : this.linkedSides)
            {
                if (side != null)
                {
                    TileEntity entity = this.getWorld().getTileEntity(new BlockPos(side.getX(), side.getY(), side.getZ()));

                    if (entity != null)
                    {
                        if (!checkInventory(entity, side) || (side.getAction() == RiftLinkedSide.EnumRiftAction.INSERT && this.hasItems()))
                        {
                            processItems((IInventory) entity, side);
                        }
                    }

                }
            }
        }
    }

    /**
     * Method checks for Tile Entities next to the Rift block, this will be removed in favor of Rift links Pushing/Pulling items from Rift Chest
     */
    @Deprecated
    public void updateTEntities()
    {
        int i = 0;
        for(EnumFacing face : EnumFacing.VALUES)
        {
            BlockPos currentPos = this.getPos();

            if(this.getWorld().getTileEntity(currentPos.offset(face)) != null)
            {

                BlockPos pos = currentPos.offset(face);
                this.connectedTEntities[i] = new RiftLinkedSide(pos.getX(), pos.getY(), pos.getZ(), EnumFacing.NORTH, RiftLinkedSide.EnumRiftAction.INSERT, false);
            }
            i++;
        }
    }

    /**
     * Processes the Rift Links attached to this Block.
     * @param inventory
     * @param side
     */
    private void processItems(IInventory inventory, RiftLinkedSide side)
    {
        if(inventory instanceof ISidedInventory)
        {
            ISidedInventory sI = (ISidedInventory) inventory;


            int[] slots = sI.getSlotsForFace(side.getFace());

            for (int i : slots)
            {

                if (!sI.getStackInSlot(i).isEmpty())
                {
                    if(side.getAction() == RiftLinkedSide.EnumRiftAction.EJECT)
                    {
                        if (this.canExtractFromSide(sI, sI.getStackInSlot(i), side.getFace(), i, side.isMasterLink()))
                        {
                            ItemStack stack = sI.getStackInSlot(i);

                            if (addToSlot(stack))
                            {
                                sI.setInventorySlotContents(i, ItemStack.EMPTY);
                                this.markDirty();

                            }
                        }
                    }
                    else if(side.getAction() == RiftLinkedSide.EnumRiftAction.INSERT)
                    {
                        if(this.canInsertFromSide(sI, sI.getStackInSlot(i), side.getFace(), i, side.isMasterLink()))
                        {
                            ItemStack stack = sI.getStackInSlot(i);

                            if (this.addStackToInventory(stack, sI))
                            {
                                this.slots.set(i, ItemStack.EMPTY);
                                this.markDirty();
                            }
                        }
                    }
                }
            }
        }
        else
        {

            if(side.getAction() == RiftLinkedSide.EnumRiftAction.EJECT)
            {
                for(int i = 0; i < inventory.getSizeInventory(); i++)
                {
                    ItemStack stack = inventory.getStackInSlot(i);

                    if(addToSlot(stack))
                    {
                        inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                        this.markDirty();
                    }
                }
            }
            else if(side.getAction() == RiftLinkedSide.EnumRiftAction.INSERT)
            {
                int i = 0;
                for (ItemStack s : this.slots)
                {
                    if(!s.isEmpty())
                    {

                        if (this.addStackToInventory(s, inventory))
                        {
                            this.slots.set(i, ItemStack.EMPTY);
                            this.markDirty();
                        }

                        i++;
                    }
                }
            }
        }
    }


    private boolean hasItems()
    {
        for(ItemStack stack : slots)
        {
            if(!stack.isEmpty())
            {
                return true;
            }
        }
        return false;
    }



    @Override
    public void update()
    {

        if(!flag)
        {
            createThread();
            flag = true;
        }
        if(this.getWorld().isBlockPowered(pos))
        {
            int counter = 0;
            if(tick == 80)
            {
                RiftChest chest = (RiftChest)this.getWorld().getBlockState(pos).getBlock();
                chest.check = hasLinkedTileEntities();
                System.out.println("Main Thread made it to " + counter);
                counter++;
                tick = 0;
            }

            checkLinkedInventorySides();
            //updateTEntities();
            //ejectItems();
            tick++;
        }
        else
        {
            RiftChest chest = (RiftChest)this.getWorld().getBlockState(pos).getBlock();
            chest.check = false;
        }

    }

    public void createThread()
    {
        Thread thread = new Thread()
        {
            private int tick = 0;
            private int counter = 0;
            @Override
            public void run()
            {


                if(tick == 80)
                {
                    System.out.println("I made it to " + counter);
                    counter++;
                    tick = 0;
                }
                tick++;
            }
        };

        thread.start();

    }


    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerTestBlock(playerInventory, this);
    }

    @Override
    public String getGuiID() {
        return "rift:riftchest";
    }

    /**
     * Checks to verify Inventory is Empty
     * @param entity
     * @param side
     * @return
     */
    private boolean checkInventory(TileEntity entity, RiftLinkedSide side)
    {
        IInventory inventory = (IInventory)entity;
        NonNullList list = NonNullList.withSize(inventory.getSizeInventory(), ItemStack.EMPTY);

        for(int i = 0; i < inventory.getSizeInventory(); i++)
        {
            list.set(i, inventory.getStackInSlot(i));
        }

        return side.checkHashCode(((Object) list).hashCode(), this);

    }


    private double determineVelocity(double coord, double blockCoord)
    {
        if(coord < blockCoord)
        {
            return ThreadLocalRandom.current().nextDouble(0.5, 1);
        }
        else
        {
            return ThreadLocalRandom.current().nextDouble(-1, -0.5);
        }

    }
}
