package io.github.bfox1.TheRift.server.container;


import io.github.bfox1.TheRift.common.inventory.InventoryRiftBag;
import io.github.bfox1.TheRift.common.items.ItemRiftBag;
import io.github.bfox1.TheRift.common.items.RiftItem;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.event.ClickEvent;


public class ContainerRiftBag extends Container
{

    private final InventoryRiftBag inventoryRiftBag;

    public ContainerRiftBag(InventoryPlayer player, InventoryRiftBag riftBag)
    {
        ItemRiftBag.TabIndex index = ItemRiftBag.TabIndex.getTabIndex(riftBag.getTabIndex());

        inventoryRiftBag = riftBag;

        if(index == ItemRiftBag.TabIndex.POUCHINV)
        {
            for(int i = 0; i < 3; i++)
            {
                for(int j = 0; j < 9; j++)
                {
                    this.addSlotToContainer(new Slot(inventoryRiftBag, j + i*9+9, 8+j+18, 28+i*18));
                }
            }
        }
        else if(index == ItemRiftBag.TabIndex.PLAYERINV)
        {
            for (int i = 0; i < 3; ++i)
            {
                for (int j = 0; j < 9; ++j)
                {
                    this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
                }
            }
        }
    }

    @Override
    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        //listener.sendAllWindowProperties(this, inventoryRiftBag);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return false;
    }




}
