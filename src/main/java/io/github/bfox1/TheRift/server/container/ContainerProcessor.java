package io.github.bfox1.TheRift.server.container;

import io.github.bfox1.TheRift.common.entity.tileentity.TileEntityDematerializer;
import io.github.bfox1.TheRift.common.entity.tileentity.TileEntityEssenceProcessor;
import io.github.bfox1.TheRift.common.inventory.SlotDematerializerContainmentValve;
import io.github.bfox1.TheRift.common.inventory.SlotIRiftEssenceContainer;
import io.github.bfox1.TheRift.common.items.ContainmentValve;
import io.github.bfox1.TheRift.common.items.RiftEssenceChunk;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by bfox1 on 11/19/2016.
 */
public class ContainerProcessor extends Container
{

    private TileEntityEssenceProcessor processor;

    private int riftEssence;
    private int maxRiftEssence;
    private int comparableEssence;
    private int maxComparableEessence;
    private int burnTime;
    private int cookTime;

    public ContainerProcessor(InventoryPlayer player, TileEntityEssenceProcessor essenceProcessor)
    {
        this.processor = essenceProcessor;

        this.addSlotToContainer(new SlotDematerializerContainmentValve(essenceProcessor, 0, 80, 36));
        this.addSlotToContainer(new Slot(essenceProcessor, 3, 80, 58));
        this.addSlotToContainer(new Slot(essenceProcessor, 1, 8, 36));
        this.addSlotToContainer(new SlotFurnaceOutput(player.player, processor, 2, 152,36));


        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(player, k, 8 + k * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return processor.isUsableByPlayer(playerIn);
    }

    @Override
    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.processor);
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for(int i = 0; i< this.listeners.size(); i++)
        {
            IContainerListener listener = this.listeners.get(i);

           checkFieldAndUpdate(riftEssence, 0, listener);
            checkFieldAndUpdate(maxRiftEssence, 1, listener);
            checkFieldAndUpdate(comparableEssence, 2, listener);
            checkFieldAndUpdate(maxComparableEessence, 3, listener);
            checkFieldAndUpdate(burnTime, 5, listener);
            checkFieldAndUpdate(cookTime, 4, listener);
        }

        this.riftEssence = getField(0);
        this.maxRiftEssence = getField(1);
        this.comparableEssence = getField(2);
        this.maxComparableEessence = getField(3);
        this.burnTime = getField(5);
        this.cookTime = getField(4);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.processor.setField(id, data);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index)
    {
        ItemStack stack = null;
        Slot slot = this.inventorySlots.get(index);
        System.out.println("HI " + index);

        if(slot != null && slot.getHasStack())
        {
            ItemStack stack1 = slot.getStack();
            stack = stack1.copy();

            if(index == 2)
            {
                if(!this.mergeItemStack(stack1, 4, 39, true))
                {
                    System.out.println("ID : 1");
                    return null;
                }

                slot.onSlotChange(stack1, stack);
            }
            else if(index != 1 && index != 0 && index != 3)
            {
                if(stack.getItem() instanceof ContainmentValve)
                {
                    System.out.println("ID: 2 - b");
                    if(!this.mergeItemStack(stack1, 0, 1, false))
                    {
                        System.out.println("ID : 2");
                        return null;
                    }
                }
                else if(stack1.getItem() instanceof RiftEssenceChunk)
                {
                    if(!this.mergeItemStack(stack1, 3,3, false))
                    {
                        System.out.println("ID : 3");
                        return null;
                    }
                }
                else if(index >=4 && index < 30)
                {
                    if(!this.mergeItemStack(stack1, 30, 39, false))
                    {
                        System.out.println("ID : 4");
                        return null;
                    }
                }
                else if(index >= 30 && index < 39 && !this.mergeItemStack(stack1, 4, 30, false))
                {
                    System.out.println("ID : 5");
                    return null;
                }

            }
            else if(!this.mergeItemStack(stack1, 4, 39, false))
            {
                System.out.println("ID : 6");
                return null;
            }

            if(stack1.getCount() == 0)
            {
                System.out.println("ID : 7");
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                System.out.println("ID : 8");
                slot.onSlotChanged();
            }

            if(stack1.getCount() == stack.getCount())
            {
                System.out.println("ID : 9");
                return null;
            }

            slot.onTake(player, stack1);
        }

        return stack;
    }

    private int checkFieldAndUpdate(int fieldCheck, int fieldIndex, IContainerListener listener)
    {
        if(fieldCheck != this.processor.getField(fieldIndex))
        {
            listener.sendWindowProperty(this, fieldIndex, processor.getField(fieldIndex));
        }

        return this.processor.getField(fieldIndex);
    }

    private int getField(int index)
    {
        return this.processor.getField(index);
    }
}
