package io.github.bfox1.TheRift.api.riftessence;

import io.github.bfox1.TheRift.common.entity.tileentity.AbstractRiftTileEntity;
import io.github.bfox1.TheRift.riftessence.RiftLinkedSide;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

/**
 * Created by bfox1 on 11/10/2016.
 */
public interface IRiftLinkableContainer
{

    /**
     * Method is to Check to see if this Container is connected to a TileEntity.
     * This method get information from {@link AbstractRiftTileEntity#linkedSides}
     * @return Boolean if any have been found.
     */
    boolean hasLinkedTileEntities();

    /**
     * Method will check {@link AbstractRiftTileEntity#linkedSides} To see if it can find any Sides associated with
     * that int.
     *
     * Although, it never gets called, may serve a purpose in the future.
     * @param id The index of the LinkedSides.
     * @return Will return the RiftLinkedSide
     */
    RiftLinkedSide getLinkedSide(int id);

    /**
     * Will return all the Linked Sides. in a RiftLinkedSide Array.
     * @return Return the RiftLinkedSide[]
     */
    RiftLinkedSide[] getLinkedSides();

    /**
     * Self explanatory, will remove the linked Side and the face that it is attached to.
     * @param pos The TileEntity this Container is connected to.
     * @param face The Face that we are removing the linked side from.
     */
    void removeLinkedSide(BlockPos pos, EnumFacing face);

    /**
     * A Simple method to clear all LinkedSides.
     */
    void clearLinkedSides();

    /**
     * Will check {@link AbstractRiftTileEntity#linkedSides} to see if a new side can be added.
     * @param side The RiftLinkedSide to be added.
     * @return If the side can be added.
     */
    boolean canAddLinkedSide(RiftLinkedSide side);

    /**
     * This method will add the Side to the Container. This method will not perform a check.
     * @param side The RiftLinkedSide to be added.
     */
     void addLinkedSide(RiftLinkedSide side);

    /**
     * This is a very intricate method, and shouldn't be tampered with without complete understanding.
     * This method is to be used to check if the Container can extract items from a LinkedSide it is connected to.
     * Since two rift Containers add each other to they're RiftLinkSide Array when one connects with the other, Only one
     * Can be the MasterLink(The Container executing the extraction) thus, the one who is NOT the masterlink will not
     * perform this method and return false.
     *
     * This method will perform a RiftLinkedSide Check first
     * before it runs a {@link ISidedInventory#canExtractItem(int, ItemStack, EnumFacing)} boolean check.
     *
     * @param inventory The Inventory of the IRiftLinkableContainer that THIS container is pulling from.
     * @param stack The Stack in question of pulling.
     * @param facing The Face of the Linked Side THIS container is looking at.(Should be the face linked)
     * @param slot The Slot in question containing the Item.
     * @param isMasterLink Will either be True or False. This gets set when connect two rift containers together.
     * @return Will return true IF all checks are correct.
     */
    boolean canExtractFromSide(IInventory inventory, ItemStack stack, EnumFacing facing, int slot, boolean isMasterLink);

    /**
     * This is a very intricate method, Similar to
     * its counterpart {@link IRiftLinkableContainer#canExtractFromSide(IInventory, ItemStack, EnumFacing, int, boolean)}
     * and shouldn't be tampered with without complete understanding.
     *
     * This method is to be used to check if the Container can extract items from a LinkedSide it is connected to.
     * Since two rift Containers add each other to they're RiftLinkSide Array when one connects with the other, Only one
     * Can be the MasterLink(The Container executing the extraction) thus, the one who is NOT the masterlink will not
     * perform this method and return false.
     *
     * This method will perform a RiftLinkedSide Check first
     * before it runs a {@link ISidedInventory#canInsertItem(int, ItemStack, EnumFacing)} boolean check.
     *
     * @param inventory The Inventory of the IRiftLinkableContainer that THIS container is pushing to.
     * @param stack The Stack in question of pushing.
     * @param facing The Face of the Linked Side THIS container is looking at.(Should be the face linked)
     * @param slot The Slot in question containing the Item.
     * @param isMasterLink Will either be True or False. This gets set when connect two rift containers together.
     * @return Will return true IF all checks are correct.
     */
    boolean canInsertFromSide(IInventory inventory, ItemStack stack, EnumFacing facing, int slot, boolean isMasterLink);

    /**
     * Simple check to determine if the inventory
     * @return Runs a check to see if the current container is an instance of ISidedInventory.
     */
    boolean isInventorySided();




}
