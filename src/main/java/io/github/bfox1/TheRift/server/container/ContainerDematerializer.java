package io.github.bfox1.TheRift.server.container;

import io.github.bfox1.TheRift.common.entity.tileentity.TileEntityDematerializer;
import io.github.bfox1.TheRift.common.inventory.SlotDematerializerContainmentValve;
import io.github.bfox1.TheRift.common.inventory.SlotDematerializerPearl;
import io.github.bfox1.TheRift.common.items.ContainmentValve;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by bfox1 on 11/8/2016.
 */
public class ContainerDematerializer extends Container
{
    private final TileEntityDematerializer demat;

    private int totalDTime = 0; //How long the enderpearl will last for
    private int dTime = 250; //The point where when the itemCookTime Reaches
    private int totalItemDTime; //matched with totalDTime
    private int itemCookTime; //The amount the current item is cooking for.
    private int comparableMaxEssence;
    private int comparableEssence;
    private int riftEssence;
    private int riftMaxEssence;

    public ContainerDematerializer(InventoryPlayer playerInventory, TileEntityDematerializer mat)
    {
        this.demat = mat;

        this.addSlotToContainer(new SlotDematerializerPearl(demat, 0, 80, 24));
        this.addSlotToContainer(new Slot(demat, 1, 44, 41));
        this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, demat, 2, 116, 41));
        this.addSlotToContainer(new SlotDematerializerContainmentValve(demat, 3, 80, 58));

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
        listener.sendAllWindowProperties(this, this.demat);
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

            if (this.comparableEssence != this.demat.getField(2))
            {
                icontainerlistener.sendWindowProperty(this, 2, this.demat.getField(2));
            }

            if (this.dTime != this.demat.getField(0))
            {
                icontainerlistener.sendWindowProperty(this, 0, this.demat.getField(0));
            }

            if (this.totalDTime != this.demat.getField(1))
            {
                icontainerlistener.sendWindowProperty(this, 1, this.demat.getField(1));
            }

            if (this.totalItemDTime != this.demat.getField(3))
            {
                icontainerlistener.sendWindowProperty(this, 3, this.demat.getField(3));
            }
            if (this.itemCookTime != this.demat.getField(4))
            {
                icontainerlistener.sendWindowProperty(this, 4, this.demat.getField(4));
            }
            if(this.comparableMaxEssence != this.demat.getField(5))
            {
                icontainerlistener.sendWindowProperty(this, 5, this.demat.getField(5));
            }
            if(this.riftEssence != this.demat.getField(7))
            {
                icontainerlistener.sendWindowProperty(this, 7, this.demat.getField(7));
            }
            if(this.riftMaxEssence != this.demat.getField(6))
            {
                icontainerlistener.sendWindowProperty(this, 6, this.demat.getField(6));
            }
        }

        this.comparableEssence = this.demat.getField(2);
        this.dTime = this.demat.getField(0);
        this.totalDTime = this.demat.getField(1);
        this.totalItemDTime = this.demat.getField(3);
        this.itemCookTime = this.demat.getField(4);
        this.comparableMaxEssence = this.demat.getField(5);
        this.riftMaxEssence = this.demat.getField(6);
        this.riftEssence = this.demat.getField(7);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.demat.setField(id, data);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.demat.isUsableByPlayer(playerIn);
    }


    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index)
    {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        System.out.println("HI " + index);

        if(!slot.getStack().isEmpty() && slot.getHasStack())
        {
            ItemStack stack1 = slot.getStack();
            stack = stack1.copy();

            if(index == 2)
            {
                if(!this.mergeItemStack(stack1, 4, 39, true))
                {
                    System.out.println("ID : 1");
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(stack1, stack);
            }
            else if(index != 1 && index != 0 && index != 3)
            {
                if(TileEntityDematerializer.isItemEPearl(stack1))
                {
                    System.out.println("ID: 2 - b");
                    if(!this.mergeItemStack(stack1, 0, 1, false))
                    {
                        System.out.println("ID : 2");
                        return ItemStack.EMPTY;
                    }
                }
                else if(stack1.getItem() instanceof ContainmentValve)
                {
                    if(!this.mergeItemStack(stack1, 2,3, false))
                    {
                        System.out.println("ID : 3");
                        return ItemStack.EMPTY;
                    }
                }
                else if(index >=4 && index < 30)
                {
                    if(!this.mergeItemStack(stack1, 30, 39, false))
                    {
                        System.out.println("ID : 4");
                        return ItemStack.EMPTY;
                    }
                }
                else if(index >= 30 && index < 39 && !this.mergeItemStack(stack1, 4, 30, false))
                {
                    System.out.println("ID : 5");
                    return ItemStack.EMPTY;
                }

            }
            else if(!this.mergeItemStack(stack1, 4, 39, false))
            {
                System.out.println("ID : 6");
                return ItemStack.EMPTY;
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
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stack1);
        }

        return stack;
    }
}
