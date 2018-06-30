package io.github.bfox1.TheRift.client.creativetabs;

import io.github.bfox1.TheRift.init.BlockInit;
import io.github.bfox1.TheRift.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by bfox1 on 4/2/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class RiftTabManager extends CreativeTabs
{
    private Item itemIcon;
    public RiftTabManager(String label)
    {
        super(label);
    }

    public static final RiftTabManager SaoBlocks = new RiftTabManager("Sao Blocks").setItem(Item.getItemFromBlock(BlockInit.RiftBlocks.get("riftchest")));
    public static final RiftTabManager SaoItems = new RiftTabManager("Sao Items").setItem((Item)ItemInit.RIFTITEM.get("riftessencechunk"));

    /**
     * This method is used for setting TabIcon for creative Tab for Sao.
     * If you add Null into its parameters the Creative tab will return Barrier block
     * From minecraft.
     * @param item
     * @return
     */
    public final RiftTabManager setItem(Item item)
    {
        this.itemIcon = item;
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final ItemStack getTabIconItem()
    {
        return itemIcon != null ? new ItemStack(itemIcon) : new ItemStack(Item.getItemFromBlock(Blocks.BARRIER));
    }
}
