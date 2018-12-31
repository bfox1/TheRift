package io.github.bfox1.TheRift.common.inventory;

import io.github.bfox1.TheRift.api.riftessence.essencecontainer.IRiftEssenceContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by bfox1 on 11/14/2016.
 */
public class SlotIRiftEssenceContainer extends Slot {

    public SlotIRiftEssenceContainer(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    public boolean isItemValid(ItemStack stak)
    {
        return stak.getItem() instanceof IRiftEssenceContainer;
    }

}
