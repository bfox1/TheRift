package io.github.bfox1.TheRift.common.blocks;

import io.github.bfox1.TheRift.common.TheRift;
import io.github.bfox1.TheRift.common.entity.tileentity.TileEntityRiftVessel;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by bfox1 on 11/11/2016.
 */
public class RiftEssenceVessel extends AbstractRiftBlock
{
    public static final int GUI_ESSENCE_VESSEL = 4;


    public RiftEssenceVessel()
    {
        super(Material.IRON);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityRiftVessel();
    }

    @ARiftBlock
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityRiftVessel)
            {
                playerIn.openGui(TheRift.instance, GUI_ESSENCE_VESSEL, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }

            return true;
        }
    }
}
