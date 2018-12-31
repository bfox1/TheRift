package io.github.bfox1.TheRift.common.inventory;

import io.github.bfox1.TheRift.common.items.ContainmentValve;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by bfox1 on 11/8/2016.
 */
public class SlotDematerializerContainmentValve extends Slot
{
    public SlotDematerializerContainmentValve(IInventory inventoryIn, int index, int xPosition, int yPosition)
    {
        super(inventoryIn, index, xPosition, yPosition);
    }

    public boolean isItemValid(ItemStack stak)
    {
        return stak.getItem() instanceof ContainmentValve;
    }
}
