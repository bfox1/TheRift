package io.github.bfox1.TheRift.api.riftessence;

import io.github.bfox1.TheRift.riftessence.RiftLinkedSide;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

/**
 * Created by bfox1 on 11/10/2016.
 *
 * TODO: DOCUMENTATION COMING SOON
 */
public interface IRiftLinkableContainer
{

    boolean hasLinkedTileEntities();

    RiftLinkedSide getLinkedSide(int id);

    RiftLinkedSide[] getLinkedSides();

    void removeLinkedSide(BlockPos pos, EnumFacing face);

    void clearLinkedSides();

    boolean canAddLinkedSide(RiftLinkedSide side);

     void addLinkedSide(RiftLinkedSide side);

    boolean canExtractFromSide(IInventory inventory, ItemStack stack, EnumFacing facing, int slot, boolean isMasterLink);

    boolean canInsertFromSide(IInventory inventory, ItemStack stack, EnumFacing facing, int slot, boolean isMasterLink);

    boolean isInventorySided();




}
