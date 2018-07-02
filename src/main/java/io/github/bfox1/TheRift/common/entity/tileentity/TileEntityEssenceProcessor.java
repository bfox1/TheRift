package io.github.bfox1.TheRift.common.entity.tileentity;

import io.github.bfox1.TheRift.api.riftessence.IRiftEssence;
import io.github.bfox1.TheRift.api.riftessence.essencecontainer.IRiftEssenceContainer;
import io.github.bfox1.TheRift.common.items.ContainmentValve;
import io.github.bfox1.TheRift.riftessence.RiftLinkedSide;
import io.github.bfox1.TheRift.riftessence.essence.AbstractRiftEssence;
import io.github.bfox1.TheRift.riftessence.essence.JoryuEssence;
import io.github.bfox1.TheRift.riftessence.helper.RiftEssenceHelper;
import io.github.bfox1.TheRift.server.container.ContainerProcessor;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nullable;

/**
 * Created by bfox1 on 11/17/2016.
 */
public class TileEntityEssenceProcessor extends AbstractRiftSidedTileEntity implements IRiftEssenceContainer
{

    private AbstractRiftEssence essence;

    private static final int[] SLOTS_SIDE = new int[]{0,1,2};
    private static final int[] SLOTS_BOTTOM = new int[] {3};
    private int burnTime;
    private int cookTime;
    private boolean isProcessing;
    private int tick;

    public TileEntityEssenceProcessor()
    {
        this.essence = new JoryuEssence();
        this.essence.setMaxRe(7500);
    }

    @Override
    public AbstractRiftEssence getEssence()
    {
        return essence;
    }

    @Override
    public int getComparableEssence() {
        return (int) essence.getComparableEssence();
    }

    @Override
    public int getComparableMaxEssence() {
        return essence.getComparableMax();
    }

    @Override
    public void addRiftEssence(IRiftEssence essence, int increment)
    {
        if(essence instanceof JoryuEssence)
        {
            RiftEssenceHelper.addRiftEssence(essence, this.essence, increment);
        }

        //    if(essence.getRiftEssence() !=0)
          //  {
            //    if(this.getEssence().getRiftEssence() != this.getEssence().getMaxRiftEssence())
              //  {
                //    int adding = essence.removeRiftEssence(increment);
                  //  this.getEssence().addRawEssence(adding);
           //     }
         //   }
    }

    @Override
    public void decreRiftEssence(IRiftEssence essence, int decrement)
    {
        if(essence instanceof JoryuEssence)
        {
            RiftEssenceHelper.removeRiftEssence(this.essence, essence, decrement);
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
        return essence instanceof JoryuEssence;
    }

    @Override
    public void setMaxRiftEssence(int maxRiftEssence)
    {
        this.essence.setMaxRe(maxRiftEssence);
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        return side == EnumFacing.DOWN ? SLOTS_BOTTOM : (side != EnumFacing.DOWN && EnumFacing.UP != side ? SLOTS_SIDE : null);
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return false;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        return false;
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
    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(this.slots, index);
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack)
    {
        this.slots.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());;
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return this.world.getTileEntity(this.pos) == this && player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @Override
    public int getField(int id) {
        switch (id)
        {
            case 0: return this.essence.getRiftEssence();
            case 1: return this.essence.getMaxRiftEssence();
            case 2: return (int) this.essence.getComparableEssence();
            case 3: return this.essence.getComparableMax();
            case 4: return this.cookTime;
            case 5: return this.burnTime;
            default: return 0;
        }
    }

    @Override
    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0: this.essence.setEssence(value); break;
            case 1: this.essence.setMaxRe(value); break;
            case 4: this.cookTime = value; break;
            case 5: this.burnTime = value; break;
        }
        //Red and Black nailed Person ;)
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public void update()
    {
        tick++;
        if(!world.isRemote)
        {

            onValveChange();
            checkBurnTime();
            checkProcess();
            processItems();
        }
        if(tick == 80)
        {
            this.markDirty();
            this.tick = 0;
        }
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerProcessor(playerInventory, this);
    }

    @Override
    public String getGuiID() {
        return null;
    }

    @Override
    public String getName() {
        return "Processor";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }
    private void onValveChange()
    {
        if(this.slots.get(0).isEmpty())
        {
            if(this.getEssence().getContainmentValve() == null)
            {
                this.getEssence().setMaxRe(5000);
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

    private void checkBurnTime()
    {
        if(essence.getContainmentValve() != null)
        {
            this.burnTime = (int) (burnTime/essence.getContainmentValve().getMultiplier());
        }
        else
        {
            this.burnTime = 450;
        }
    }

    private int getEssenceDoubler()
    {
        if(!slots.get(3).isEmpty() && slots.get(3).getCount() >= 3)
        {
            int d = ThreadLocalRandom.current().nextInt(1, 20);

            if(d >= 1 && d <= 15)
            {
                return 1;
            }
            else if(d >=16 && d <= 18)
            {
                return 2;
            }
            else if(d >=19 && d <= 20)
            {
                return 3;
            }
        }
        return 0;
    }

    private void performEssenceExtracting()
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

            container.addRiftEssence(this.essence, 24);
        }
    }

    private void processItems()
    {
        if(isProcessing)
        {
            this.cookTime--;
            if(this.essence.getRiftEssence() >3)
            {
                this.essence.removeRiftEssence(4);
            }
            else
            {
                this.isProcessing = false;
            }
        }

        if(!slots.get(1).isEmpty())
        if(isProcessing && FurnaceRecipes.instance().getSmeltingResult(slots.get(1)).isEmpty() && canProcess())
        {
            System.out.println("FIRST-RAN");
            ItemStack stack = FurnaceRecipes.instance().getSmeltingResult(slots.get(1));

            if(this.cookTime == 0)
            {
                slots.get(1).setCount(slots.get(1).getCount() -1);

                if (slots.get(1).getCount() == 0) {
                    slots.set(1, ItemStack.EMPTY);
                }
                stack.setCount(stack.getCount() + this.getEssenceDoubler() + 1);

                slots.set(2, stack);
                if(!slots.get(3).isEmpty())
                if(slots.get(3).getCount() >= 3)
                {
                    slots.get(3).setCount(slots.get(3).getCount() - 3);
                    if(slots.get(3).getCount() == 0)
                    {
                        slots.set(3, ItemStack.EMPTY);
                    }
                }
            }
        }
        else if(!isProcessing && FurnaceRecipes.instance().getSmeltingResult(slots.get(1)).isEmpty() && canProcess())
        {
            System.out.println("RAN-SECOND");
            this.cookTime = 150;
        }
    }

    private void checkProcess()
    {
        this.isProcessing = this.cookTime > 0;
    }
    private boolean canProcess()
    {
        if(!slots.get(1).isEmpty())
        {
            return true;
        }
        else if(this.essence.getRiftEssence() <= 3)
        {
            return false;
        }

        return this.slots.get(2).isEmpty() || this.slots.get(2).getCount() != this.getInventoryStackLimit();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("re", this.essence.getRiftEssence());
        compound.setInteger("burnTime", this.burnTime);
        compound.setInteger("cookTime", this.cookTime);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        if(compound.hasKey("re"))
        {
            this.essence.setEssence(compound.getInteger("re"));
            this.burnTime = compound.getInteger("burnTime");
            this.cookTime = compound.getInteger("cookTime");
        }
    }
}
