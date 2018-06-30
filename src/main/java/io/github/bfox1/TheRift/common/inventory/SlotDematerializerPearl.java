package io.github.bfox1.TheRift.common.inventory;

import io.github.bfox1.TheRift.common.entity.tileentity.TileEntityDematerializer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * Created by bfox1 on 11/8/2016.
 */
public class SlotDematerializerPearl extends Slot
{
    public SlotDematerializerPearl(IInventory inventoryIn, int index, int xPosition, int yPosition)
    {
        super(inventoryIn, index, xPosition, yPosition);
    }

    public boolean isItemValid(@Nullable ItemStack stack)
    {
        return TileEntityDematerializer.isItemEPearl(stack);
    }
}
