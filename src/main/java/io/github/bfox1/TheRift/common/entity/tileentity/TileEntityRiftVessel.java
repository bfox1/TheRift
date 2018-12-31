package io.github.bfox1.TheRift.common.entity.tileentity;

import io.github.bfox1.TheRift.api.riftessence.IRiftEssence;
import io.github.bfox1.TheRift.api.riftessence.essencecontainer.IRiftEssenceContainer;
import io.github.bfox1.TheRift.common.items.ContainmentValve;
import io.github.bfox1.TheRift.riftessence.RiftLinkedSide;
import io.github.bfox1.TheRift.riftessence.essence.AbstractRiftEssence;
import io.github.bfox1.TheRift.riftessence.essence.JoryuEssence;
import io.github.bfox1.TheRift.riftessence.helper.RiftEssenceHelper;
import io.github.bfox1.TheRift.server.container.ContainerRiftVessel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

/**
 * Created by bfox1 on 11/12/2016.
 */
public class TileEntityRiftVessel extends AbstractRiftIventoryTileEntity implements IRiftEssenceContainer
{

    private AbstractRiftEssence essence;
    private int tick;

    public TileEntityRiftVessel()
    {
        this.essence = new JoryuEssence();
        this.essence.setMaxRe(50000);
    }

    @Override
    public int getSizeInventory()
    {
        return 3;
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
    public ItemStack decrStackSize(int index, int count)
    {
        ItemStack stack = null;
        if(!slots.get(index).isEmpty())
        {
            stack = slots.get(index);
            if(stack.getCount() <= count)
            {
                slots.set(index, ItemStack.EMPTY);
                return stack;
            }
            else
            {
                stack = slots.get(index).splitStack(count);
                if(stack.getCount() == 0)
                {
                    slots.set(index, ItemStack.EMPTY);
                }
            }

        }
        return stack;
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        ItemStack stack = slots.get(index);
        slots.set(index, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack)
    {
        if(stack != null)
        if(isItemValidForSlot(index, stack))
        {
                ItemStack slotStack = slots.get(index);

                    if(slotStack.isEmpty())
                    {
                        slots.set(index, stack);
                    }
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }



    @Override
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        return this.world.getTileEntity(this.pos) == this && player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory(EntityPlayer player)
    {

    }

    @Override
    public void closeInventory(EntityPlayer player)
    {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return index == 0 && stack.getItem() instanceof ContainmentValve || slots.isEmpty() && stack.getItem() instanceof IRiftEssenceContainer;
    }

    @Override
    public int getField(int id)
    {
        switch(id)
        {
            case 0: return this.getEssence().getRiftEssence();
            case 1 : return (int) this.getEssence().getMaxRiftEssence();
            case 2 : return (int) this.getEssence().getComparableEssence();
            case 3 : return this.getEssence().getComparableMax();
            default : return 0;
        }
    }

    @Override
    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0 : this.getEssence().setEssence((int) (value));break;
            case 1 : this.getEssence().setMaxRe(value);break;
            //case 2 : this.getEssence().addEssence((int) (value/getEssence().getEssenceMultiplier()));break;
            default: break;
        }
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear()
    {
        for (int i = 0; i < this.slots.size(); ++i)
        {
            this.slots.set(i, ItemStack.EMPTY);
        }
    }

    @Override
    public void update()
    {
        tick++;
        if(!this.world.isRemote)
        {
            onValveChange();
            processEssence();
        }
        if(tick == 40)
        {
            this.markDirty();
            this.tick = 0;
        }
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerRiftVessel(playerInventory, this);
    }

    @Override
    public String getGuiID() {
        return "container:RiftVessel";
    }

    @Override
    public String getName() {
        return "RiftVessel";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public AbstractRiftEssence getEssence()
    {
        return essence;
    }

    @Override
    public int getComparableEssence() {
        return (int) this.essence.getComparableEssence();
    }

    @Override
    public int getComparableMaxEssence() {
        return this.essence.getComparableMax();
    }

    @Override
    public void addRiftEssence(IRiftEssence essence, int increment)
    {
        if(essence instanceof JoryuEssence)
            RiftEssenceHelper.addRiftEssence(essence, this.essence, increment);

    }

    @Override
    public void decreRiftEssence(IRiftEssence essence, int decrement)
    {
        if(essence instanceof JoryuEssence)
            RiftEssenceHelper.addRiftEssence(this.essence, essence, decrement);

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
        return essence instanceof JoryuEssence;
    }

    @Override
    public void setMaxRiftEssence(int maxRiftEssence)
    {
        this.getEssence().setMaxRe(maxRiftEssence);
    }


    private void onValveChange()
    {
        if(this.slots.get(0).isEmpty())
        {
            if(this.getEssence().getContainmentValve() == null)
            {
                this.getEssence().setMaxRe(50000);
            }
            this.getEssence().clearValve();
        }
        else
        {
            ContainmentValve valve = (ContainmentValve) this.slots.get(0).getItem();

            if(valve.getContainmentValve() != this.getEssence().getContainmentValve())
            {
                this.getEssence().setContainmentValve(valve.getContainmentValve());
            }
        }
    }

    private void processEssence()
    {
        int i = 0;
        for(RiftLinkedSide side : linkedSides)
        {

            if(side != null && side.getAction() == RiftLinkedSide.EnumRiftAction.EJECT)
            {
                if(!side.isMasterLink())
                handleExtractingEssence(world.getTileEntity(side.getPos()), i);
            }
            else if(side != null && side.getAction() == RiftLinkedSide.EnumRiftAction.INSERT)
            {
                if(!side.isMasterLink())
                handleInsertEssence(world.getTileEntity(side.getPos()), i);
            }
            i++;
        }
    }

    private void handleExtractingEssence(TileEntity entity, int index)
    {

        if(entity == null)
        {
            linkedSides[index] = null;

        }
        else if(!(entity instanceof IRiftEssenceContainer))
        {

        }
        else
        {
            IRiftEssenceContainer container = (IRiftEssenceContainer) entity;
            System.out.println(this.essence.getRiftEssence() + " extracting");
            container.decreRiftEssence(this.essence, 24);
        }
    }

    private void handleInsertEssence(TileEntity entity,int index)
    {

        if(entity == null)
        {
            linkedSides[index] = null;
        }
        else if(entity instanceof IRiftEssenceContainer)
        {
            IRiftEssenceContainer container = (IRiftEssenceContainer) entity;
            System.out.println(this.essence.getRiftEssence() + "Inserting");
            container.addRiftEssence(this.essence, 24);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("re", this.getEssence().getRiftEssence());
        //compound.setInteger("maxRe", (int) this.getEssence().getMaxRiftEssence());

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
    }
}
