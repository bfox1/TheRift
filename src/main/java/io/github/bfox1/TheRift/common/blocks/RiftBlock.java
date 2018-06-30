package io.github.bfox1.TheRift.common.blocks;

import io.github.bfox1.TheRift.client.creativetabs.RiftTabManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Created by bfox1 on 4/2/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class RiftBlock extends Block
{

    private boolean canBreak;
    private final float HARDNESS_VALUE;

    public RiftBlock(Material p_i46399_1_, float hardnessValue)
    {
        super(p_i46399_1_);
        this.setCreativeTab(RiftTabManager.SaoBlocks);
        this.HARDNESS_VALUE = hardnessValue;
        this.setBreakable(false);
    }


    /**
     * This Method will determine if the Block is capable of being destroyed.
     * Setting Value to True will mean the Block can be broken.
     * @param value boolean value.
     */
    public void setBreakable(boolean value)
    {
        if(!value)
        {
            this.setBlockUnbreakable();
        }
        else
        {
            this.setHardness(HARDNESS_VALUE);
        }
    }

    /**
     * All this method does is returns RiftBlock instead of Block.
     * @param name The name of the Block
     * @return RiftBlock Object.
     */
    @Override
    public RiftBlock setUnlocalizedName(String name)
    {
        return (RiftBlock)super.setUnlocalizedName(name);
    }

}
