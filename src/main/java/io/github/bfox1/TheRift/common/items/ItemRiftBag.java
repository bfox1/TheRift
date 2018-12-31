package io.github.bfox1.TheRift.common.items;

import io.github.bfox1.TheRift.api.riftessence.IRiftLinkableContainer;
import io.github.bfox1.TheRift.common.inventory.InventoryRiftBag;
import io.github.bfox1.TheRift.riftessence.RiftLinkedSide;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemRiftBag extends RiftItem implements IRiftLinkableContainer
{
    public static final int RIFTBAG = 6;

    /**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        if(!worldIn.isRemote)
        {
            ItemStack stack = playerIn.getHeldItem(handIn);

            if(!stack.hasTagCompound())
            {
                stack.setTagCompound(this.createCompound());
            }
            //playerIn.openGui();
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }

    @Override
    public boolean hasLinkedTileEntities()
    {
        return false;
    }

    @Override
    public RiftLinkedSide getLinkedSide(int id)
    {
        return null;
    }

    @Override
    public RiftLinkedSide[] getLinkedSides()
    {
        return new RiftLinkedSide[0];
    }

    @Override
    public void removeLinkedSide(BlockPos pos, EnumFacing face)
    {

    }

    @Override
    public void clearLinkedSides()
    {

    }

    @Override
    public boolean canAddLinkedSide(RiftLinkedSide side)
    {
        return false;
    }

    @Override
    public void addLinkedSide(RiftLinkedSide side)
    {

    }

    @Override
    public boolean canExtractFromSide(IInventory inventory, ItemStack stack, EnumFacing facing, int slot, boolean isMasterLink)
    {
        return false;
    }

    @Override
    public boolean canInsertFromSide(IInventory inventory, ItemStack stack, EnumFacing facing, int slot, boolean isMasterLink)
    {
        return false;
    }

    @Override
    public boolean isInventorySided()
    {
        return false;
    }


    //=====Helper Methods for getting Rift Bag information=====///

    public static TabIndex getTabIndex(ItemStack stack)
    {
        return TabIndex.getTabIndex(getStackCompound(stack).getInteger("tab"));
    }

    private static NBTTagCompound getStackCompound(ItemStack stack)
    {
        return stack.getTagCompound();
    }


    private NBTTagCompound createCompound()
    {
        NBTTagCompound compound = new NBTTagCompound();

        compound.setInteger("tab", TabIndex.POUCHINV.getIndex());

        return compound;
    }

    public static InventoryRiftBag getInventory(ItemStack stack)
    {
       NBTTagCompound compound = stack.getTagCompound();

        InventoryRiftBag inventoryRiftBag = new InventoryRiftBag(stack);
        inventoryRiftBag.readFromNBT(compound);

        return inventoryRiftBag;
    }



    public enum TabIndex
    {
        POUCHINV(1), PLAYERINV(0), ITEMFILTER(2),RIFTLINKED(3);


        private final int index;

        TabIndex(int index)
        {
            this.index = index;
        }

        public int getIndex()
        {
            return index;
        }

        public static TabIndex getTabIndex(int i)
        {
            for(TabIndex is : values())
            {
                if(is.getIndex() == i)
                {
                    return is;
                }
            }
            return POUCHINV;
        }
    }
}
