package io.github.bfox1.TheRift.common.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by bfox1 on 11/7/2016.
 */
public abstract class AbstractRiftBlock extends BlockContainer
{
    protected AbstractRiftBlock(Material materialIn)
    {
        super(materialIn);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof IInventory)
        {
            InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
            worldIn.updateComparatorOutputLevel(pos, this);
        }
        super.breakBlock(worldIn, pos, state);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public AbstractRiftBlock setUnlocalizedName(String name)
    {
        return (AbstractRiftBlock) super.setUnlocalizedName(name);
    }


    public AbstractRiftBlock setBlockRegisterName(String s)
    {
        this.setRegistryName(s);
        return this;
    }
}
