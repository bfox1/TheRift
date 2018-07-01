package io.github.bfox1.TheRift.common.entity.tileentity;

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
                                stack = ItemStack.EMPTY;
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
        //for(RiftLinkedSide side : this.linkedSides)
        {
            for(int f = 0; f < this.linkedSides.length; f++)
            {
                RiftLinkedSide side = linkedSides[f];
                if(linkedSides[f] == null)
                {
                    //
                }
                else
                {
                    TileEntity entity = this.getWorld().getTileEntity(new BlockPos(side.getX(), side.getY(), side.getZ()));

                    if(entity != null)
                    {
                        //Testing HashCode//
                        IInventory inventory = (IInventory)entity;
                        NonNullList list = NonNullList.withSize(inventory.getSizeInventory(), ItemStack.EMPTY);
                        for(int i = 0; i < inventory.getSizeInventory(); i++)
                        {
                            list.set(i, inventory.getStackInSlot(i));
                        }

                        //END of HashCode Testing//
                        if(!side.checkHashCode(((Object)list).hashCode()))
                        {
                            if (entity instanceof ISidedInventory)
                            {
                                ISidedInventory sI = (ISidedInventory) entity;


                                int[] slots = sI.getSlotsForFace(side.getFace());

                                for (int i : slots)
                                {

                                    if (!sI.getStackInSlot(i).isEmpty())
                                        if (this.canExtractFromSide(sI, sI.getStackInSlot(i), side.getFace(), i, side.isMasterLink()))
                                        {
                                            ItemStack stack = sI.getStackInSlot(i);

                                            if (addToSlot(stack))
                                            {
                                                System.out.println("IM WORKING and adding items to extract!? ");
                                                addItemTracer(stack, side);
                                                sI.setInventorySlotContents(i, ItemStack.EMPTY);
                                                this.markDirty();

                                            } else
                                            {
                                                //
                                            }
                                        }
                                }
                            }
                            else if(entity instanceof IInventory)
                            {
                                IInventory inv = (IInventory)entity;

                                for(int i = 0; i < inv.getSizeInventory(); i++)
                                {
                                    ItemStack stack = inv.getStackInSlot(i);

                                    if(addToSlot(stack))
                                    {
                                        addItemTracer(stack,side);
                                        System.out.println("IM WORKING and adding items to inventory");
                                        inv.setInventorySlotContents(i, ItemStack.EMPTY);
                                        this.markDirty();
                                    }
                                    else
                                    {
                                        //
                                    }
                                }
                            }
                        }

                    }

                }
            }
            /*if(side == null)
            {
               // System.out.println("I appear to be null " + z);
              //  z++;
                continue;
            }

            TileEntity entity = this.getWorld().getTileEntity(new BlockPos(side.getX(), side.getY(), side.getZ()));

            if(entity != null)
            {
            //Testing HashCode//
            IInventory inventory = (IInventory)entity;
            NonNullList list = NonNullList.withSize(inventory.getSizeInventory(), ItemStack.EMPTY);
            for(int i = 0; i < inventory.getSizeInventory(); i++)
            {
                list.set(i, inventory.getStackInSlot(i));
            }

            //END of HashCode Testing//
            if(!side.checkHashCode(((Object)list).hashCode()))
            {
                if (entity instanceof ISidedInventory)
                {
                    ISidedInventory sI = (ISidedInventory) entity;


                    int[] slots = sI.getSlotsForFace(side.getFace());

                    for (int i : slots)
                    {

                        if (!sI.getStackInSlot(i).isEmpty())
                            if (this.canExtractFromSide(sI, sI.getStackInSlot(i), side.getFace(), i, side.isMasterLink()))
                            {
                                ItemStack stack = sI.getStackInSlot(i);

                                if (addToSlot(stack))
                                {
                                    System.out.println("IM WORKING and adding items to extract!? ");
                                    addItemTracer(stack, side);
                                    sI.setInventorySlotContents(i, ItemStack.EMPTY);
                                    this.markDirty();

                                } else
                                    {
                                    //
                                }
                            }
                    }
                }
            }

            }
            else if(entity instanceof IInventory)
            {
                IInventory inv = (IInventory)entity;

                for(int i = 0; i < inv.getSizeInventory(); i++)
                {
                    ItemStack stack = inv.getStackInSlot(i);

                    if(addToSlot(stack))
                    {
                        addItemTracer(stack,side);
                        System.out.println("IM WORKING and adding items to inventory");
                        inv.setInventorySlotContents(i, ItemStack.EMPTY);
                        this.markDirty();
                    }
                    else
                    {
                        //
                    }
                }
            }
           // z++;*/
        }
    }

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


    public void ejectItems()
    {
        if (!hasItems())
        {
            //Does nothing
        }
        else
        {


            BlockPos currentPos = this.getPos();
            for(RiftLinkedSide side : this.connectedTEntities) {

                if(side != null)
                if (this.getWorld().getTileEntity(new BlockPos(side.getX(), side.getY(), side.getZ())) != null)
                {
                    TileEntity entity = this.getWorld().getTileEntity(new BlockPos(side.getX(), side.getY(), side.getZ()));
                    //System.out.println("Entity is indeed not null");

                    if (entity instanceof ISidedInventory)
                    {
                        ISidedInventory iSidedInventory = (ISidedInventory) entity;

                       // for (EnumFacing face : EnumFacing.VALUES)
                        {
                            int i = 0;
                            for (ItemStack s : this.slots)
                            {
                                if(!s.isEmpty())
                                {
                                    if (this.canInsertFromSide(iSidedInventory, s, side.getFace(), i, side.isMasterLink()))
                                    {
                                        if (this.addStackToInventory(s, iSidedInventory))
                                        {
                                            System.out.println("What am I doing? Inserting? ");
                                            this.markDirty();
                                            this.slots.set(i, ItemStack.EMPTY);
                                        }
                                    }
                                    i++;
                                }
                            }


                        }
                    }
                    else if (entity instanceof IInventory)
                    {
                        IInventory inventory = (IInventory) entity;

                        int i = 0;
                        for (ItemStack s : this.slots)
                        {
                            if(!s.isEmpty())
                            {
                                System.out.println("What am I doing? Inserting? something else");
                                if (this.addStackToInventory(s, inventory))
                                    this.slots.set(i, ItemStack.EMPTY);
                                this.markDirty();
                                i++;
                            }
                        }
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
        if(this.getWorld().isBlockPowered(pos))
        {
            if(tick == 80)
            {
                RiftChest chest = (RiftChest)this.getWorld().getBlockState(pos).getBlock();
                chest.check = hasLinkedTileEntities();
                tick = 0;
            }

            checkLinkedInventorySides();
            updateTEntities();
            ejectItems();
            tick++;
        }
        else
        {
            RiftChest chest = (RiftChest)this.getWorld().getBlockState(pos).getBlock();
            chest.check = false;
        }

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
     * This method was to allow Visible sight of the items being transported through the "Air"
     * For a more anesthetic look.
     * @param stack
     * @param side
     */
    public void addItemTracer(ItemStack stack, RiftLinkedSide side)
    {
        /*double dirX = this.pos.getX() - side.getX();
        double dirY = this.pos.getY() - side.getY();
        double dirZ = this.pos.getZ() - side.getZ();

        double s = Math.pow(dirX, 2)+Math.pow(dirY, 2)+Math.pow(dirZ, 2);
        System.out.println(Math.sqrt(s));

        System.out.println(dirX/Math.sqrt(s));

        TransportEntity item = new TransportEntity(this.worldObj, side.getX(), side.getY(), side.getZ(), stack);
        item.setNoDespawn();
        item.setVelocity(dirX/Math.sqrt(s)/2, dirY/Math.sqrt(s)/2, dirZ/Math.sqrt(s)/2);
        item.setInfinitePickupDelay();
        item.isCollided = false;
        worldObj.spawnEntityInWorld(item);*/
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
