package io.github.bfox1.TheRift.common.entity.tileentity;

import io.github.bfox1.TheRift.server.container.ContainerDematerializer;
import io.github.bfox1.TheRift.common.items.ContainmentValve;
import io.github.bfox1.TheRift.api.riftessence.essencecontainer.IRiftEssenceContainer;
import io.github.bfox1.TheRift.init.ItemInit;
import io.github.bfox1.TheRift.riftessence.essence.AbstractRiftEssence;
import io.github.bfox1.TheRift.api.riftessence.IRiftEssence;
import io.github.bfox1.TheRift.riftessence.essence.JoryuEssence;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by bfox1 on 11/7/2016.
 */
public class TileEntityDematerializer extends AbstractRiftSidedTileEntity implements IRiftEssenceContainer
{
    private static final int[] SLOTS_TOP = new int[]{0};
    private static final int[] SLOTS_SIDE = new int[] {3,1};
    private static final int[] SLOTS_BOTTOM = new int[] {2};


    private AbstractRiftEssence essence;

    private int DTotalBurnTime = 0; //How long the enderpearl will last for
    private int dTime = 250; //The point where when the itemCookTime Reaches
    private int totalItemDTime; //matched with DTotalBurnTime
    private int itemCookTime; //The amount the current item is cooking for.

    private boolean isDemat = false;

    private boolean hasValve;

    public TileEntityDematerializer()
    {
        this.essence = new JoryuEssence();
        essence.setMaxRe(5000);

    }


    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        return side == EnumFacing.DOWN ? SLOTS_BOTTOM : (side == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDE);
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        if(itemStackIn.getItem() instanceof ItemEnderPearl && direction == EnumFacing.UP)
        {
                return true;

        }
        else if(itemStackIn.getItem() instanceof ContainmentValve && (direction == EnumFacing.NORTH))
        {
            if(index == 3)
            {
                return true;
            }
        }
        else if(direction == EnumFacing.EAST || direction == EnumFacing.WEST || EnumFacing.SOUTH == direction)
        {
            if(index == 1)
            return true;
        }
        else
        {
            return true;
        }
        return false;
    }

    public static boolean isItemEPearl(ItemStack stack)
    {
        return stack.getItem() instanceof ItemEnderPearl;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        return (direction == EnumFacing.DOWN && index == 2);

    }

    @SideOnly(Side.CLIENT)
    public static boolean isDematerializing(IInventory inventory)
    {
        return inventory.getField(1) > 0;
    }

    @Override
    public int getSizeInventory() {
        return 4;
    }

    @Override
    public boolean isEmpty() {
        return false;
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
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.slots, index);
    }

    @Override
    public void setInventorySlotContents(int index,  ItemStack stack)
    {
        boolean flag = !stack.isEmpty() && stack.isItemEqual(this.slots.get(index)) && ItemStack.areItemStackTagsEqual(stack, this.slots.get(index));

        this.slots.set(index, stack);
        if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (index == 1 && !flag)
        {
            this.dTime = 0;
            this.markDirty();
        }

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("re", this.getEssence().getRiftEssence());
       // compound.setInteger("maxRe", (int) this.getEssence().getMaxRiftEssence());
        compound.setInteger("dTime", this.dTime);
        compound.setInteger("dTotal", this.totalItemDTime);
        compound.setInteger("itemC", this.itemCookTime);
        compound.setInteger("dematTime", this.DTotalBurnTime);

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        if(compound.hasKey("re"))
        {
            this.getEssence().setEssence(compound.getInteger("re"));
            //this.getEssence().setMaxRe(compound.getInteger("maxRe"));
        }
        this.dTime = compound.getInteger("dTime");
        this.totalItemDTime = compound.getInteger("dTotal");
        this.itemCookTime = compound.getInteger("itemC");
        this.DTotalBurnTime = compound.getInteger("dematTime");
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        return this.getWorld().getTileEntity(this.pos) == this && player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory(EntityPlayer player)
    {
        System.out.println("Am I working? ");
    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return true;
    }

    @Override
    public int getField(int id) {
        switch (id)
        {
            case 0 : return this.dTime;
            case 1 : return this.DTotalBurnTime;
            case 2 : return (int) this.essence.getComparableEssence();
            case 3 : return this.totalItemDTime;
            case 4: return this.itemCookTime;
            case 5: return (int) this.essence.getComparableMax();
            case 6: return this.essence.getMaxRiftEssence();
            case 7: return this.essence.getRiftEssence();
            default: return 0;
        }
    }

    @Override
    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0:
                this.dTime = value; break; //The Current burn time
            case 1:
                this.DTotalBurnTime = value; break; //The total Dematerialized Time
            case 3:
                this.totalItemDTime = value;break; //Ticks till item is completely dematerialized
            case 4:
                this.itemCookTime = value;break; //Ticks before completely dematerialize
            case 6:
                this.essence.setMaxRe(value);break;
            case 7:this.essence.setEssence(value); break;
            default : break;
        }

    }

    @Override
    public int getFieldCount() {
        return 5;
    }

    @Override
    public void clear() {
       /* for (int i = 0; i < this.slots.size(); ++i)
        {
            this.slots.remove(i);//Modifications here.
        }
        */
       this.slots.clear();
    }

    @Override
    public void update()
    {

        if(isDematerializing())
        {
            --this.DTotalBurnTime;
        }

        if(dTime != 250)
        {
            this.dTime = 250;
        }

        if(!world.isRemote)
        {
            onValveChange();
        if(isDematerializing() || !this.slots.get(1).isEmpty() && !this.slots.get(0).isEmpty()) //1.12.1 changes here.
        {
            if(!isDematerializing() && canDematerialize() && !this.essence.isReachedMax())
            {
                this.DTotalBurnTime = 500; //Sets the Ticks for the EnderPearl to burn for.

                if(isDematerializing())
                {
                    if(!this.slots.get(0).isEmpty())
                    {
                        this.slots.get(0).setCount( (this.slots.get(0).getCount()-1));

                        if(this.slots.get(0).getCount() == 0)
                        {
                            this.slots.set(0, ItemStack.EMPTY);
                        }
                    }
                }
            }

            if(this.isDematerializing() && this.canDematerialize()&& !this.essence.isReachedMax())
            {
                ++this.itemCookTime;
                this.essence.addEssence(1);
                if (this.itemCookTime == this.dTime)
                {
                    this.itemCookTime = 0;
                    this.dTime = 250;
                    this.dematerializeItem();
                    this.markDirty();
                }
            }
            else
            {
                this.itemCookTime = 0;
            }

        }

        }
    }

    private void updateEssence()
    {
        this.essence.setContainmentValve(((ContainmentValve)this.slots.get(3).getItem()).getContainmentValve());
    }

    private boolean isDematerializing()
    {
        return this.DTotalBurnTime > 0;
    }

    @Override
    public String getName() {
        return "container.dematerializer";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    public AbstractRiftEssence getEssence() {
        return essence;
    }

    @Override
    public int getComparableEssence() {
        return (int)this.essence.getComparableEssence();
    }

    @Override
    public int getComparableMaxEssence() {
        return this.essence.getComparableMax();
    }



    @Override
    public void addRiftEssence(IRiftEssence essence, int increment)
    {
        if(essence.getRiftEssence() !=0)
        {
            if(this.getEssence().getRiftEssence() != this.getEssence().getMaxRiftEssence())
            {
                int adding = essence.removeRiftEssence(increment);
                this.getEssence().addRawEssence(adding);
            }
        }
    }

    @Override
    public void decreRiftEssence(IRiftEssence essence, int decrement)
    {
        if(this.getEssence().getRiftEssence() != 0)
        {
            if(essence.getRiftEssence() != essence.getMaxRiftEssence())
            {
                int adding = this.getEssence().removeRiftEssence(decrement);
                essence.addRawEssence(adding);
            }


        }
    }

    @Override
    public void setComparableEssence(AbstractRiftEssence essence)
    {
        AbstractRiftEssence currentEssence = this.essence.copy();

        this.essence = essence;
        this.essence.setMaxRe((int) currentEssence.getMaxRiftEssence());
        this.essence.setEssence(currentEssence.getRiftEssence());

    }

    @Override
    public boolean isEssenceAllowed(IRiftEssence essence)
    {
        return true;
    }

    @Override
    public void setMaxRiftEssence(int riftEssence)
    {
     this.essence.setMaxRe(riftEssence);
    }


    private boolean canDematerialize() {
        if (this.slots.get(1).isEmpty()) {
            return false;
        }

        return this.slots.get(2).isEmpty() || this.slots.get(2).getCount() != this.getInventoryStackLimit();
    }

    private void dematerializeItem()
    {
        if(canDematerialize())
        {
            int random = ThreadLocalRandom.current().nextInt(0,8);
            if(this.slots.get(2).isEmpty())
            {
                if(random == 3)
                this.slots.set(2, new ItemStack((Item)ItemInit.RIFTITEM.get("riftessencechunk")));

            }
            else
            {
                if(random == 3)
                this.slots.get(2).setCount(this.slots.get(2).getCount() + 1);
            }

            this.slots.get(1).setCount(this.slots.get(1).getCount() - 1);//Change

            if(this.slots.get(1).getCount() == 0)
            {
                this.slots.set(1, ItemStack.EMPTY);
            }
        }
    }

    private void onValveChange()
    {
        if(this.slots.get(3).isEmpty())
        {
            if(this.getEssence().getContainmentValve() == null)
            {
                this.getEssence().setMaxRe(5000);
            }
            else
            {
                this.getEssence().clearValve();
            }
        }
        else
        {
            ContainmentValve valve = (ContainmentValve) this.slots.get(3).getItem();

            if(valve.getContainmentValve() != this.getEssence().getContainmentValve())
            {
                this.getEssence().setContainmentValve(valve.getContainmentValve());
            }
        }
    }


    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerDematerializer(playerInventory, this);
    }

    @Override
    public String getGuiID() {
        return "rift:dematerializer+";
    }

    net.minecraftforge.items.IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);
    net.minecraftforge.items.IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.DOWN);
    net.minecraftforge.items.IItemHandler handlerSide = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.WEST);

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing)
    {
        if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            if (facing == EnumFacing.DOWN)
                return (T) handlerBottom;
            else if (facing == EnumFacing.UP)
                return (T) handlerTop;
            else
                return (T) handlerSide;
        return super.getCapability(capability, facing);
    }

}
