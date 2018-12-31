package io.github.bfox1.TheRift.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by bfox1 on 11/17/2016.
 */
public class RiftRelay extends AbstractRiftBlock
{

    protected RiftRelay(Material materialIn)
    {
        super(materialIn);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null;
    }
}
