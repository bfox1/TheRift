package io.github.bfox1.TheRift.server.container;


import io.github.bfox1.TheRift.common.entity.tileentity.TileEntityDematerializer;
import io.github.bfox1.TheRift.common.entity.tileentity.TileEntityRiftVessel;
import io.github.bfox1.TheRift.common.inventory.SlotDematerializerContainmentValve;
import io.github.bfox1.TheRift.common.inventory.SlotDematerializerPearl;
import io.github.bfox1.TheRift.common.inventory.SlotIRiftEssenceContainer;
import io.github.bfox1.TheRift.common.items.ContainmentValve;
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
 * Created by bfox1 on 11/13/2016.
 */
public class ContainerRiftVessel extends Container
{
    private final TileEntityRiftVessel vessel;

    private int riftEssence;
    private int maxRiftEssence;
    private int comparableEssence;
    private int maxComparableEssence;


    public ContainerRiftVessel(InventoryPlayer playerInventory, TileEntityRiftVessel mat)
    {
        this.vessel = mat;

        this.addSlotToContainer(new SlotDematerializerContainmentValve(vessel, 0, 80, 11));
        this.addSlotToContainer(new SlotIRiftEssenceContainer(vessel, 1, 80, 58));


        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.vessel);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);

            if (this.riftEssence != this.vessel.getField(0))
            {
                icontainerlistener.sendWindowProperty(this, 0, this.vessel.getField(0));
            }

            if (this.maxRiftEssence != this.vessel.getField(1))
            {
                icontainerlistener.sendWindowProperty(this, 1, this.vessel.getField(1));
            }

            if (this.comparableEssence != this.vessel.getField(2))
            {
                icontainerlistener.sendWindowProperty(this, 2, this.vessel.getField(2));
            }

            if (this.maxComparableEssence != this.vessel.getField(3))
            {
                icontainerlistener.sendWindowProperty(this, 3, this.vessel.getField(3));
            }

        }

        this.riftEssence = this.vessel.getField(0);
        this.maxRiftEssence = this.vessel.getField(1);
        this.comparableEssence = this.vessel.getField(2);
        this.maxComparableEssence = this.vessel.getField(3);

    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.vessel.setField(id, data);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.vessel.isUsableByPlayer(playerIn);
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

            if(index != 1 && index != 0)
            {
                 if(stack1.getItem() instanceof ContainmentValve)
                {
                    if(!this.mergeItemStack(stack1, 0,0, false))
                    {
                        System.out.println("ID : 3");
                        return null;
                    }
                }
                else if(index >=3 && index < 30)
                {
                    if(!this.mergeItemStack(stack1, 30, 39, false))
                    {
                        System.out.println("ID : 4");
                        return null;
                    }
                }
                else if(index >= 30 && index < 39 && !this.mergeItemStack(stack1, 3, 30, false))
                {
                    System.out.println("ID : 5");
                    return null;
                }

            }
            else if(!this.mergeItemStack(stack1, 3, 39, false))
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
}
