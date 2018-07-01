package io.github.bfox1.TheRift.common.blocks;


import io.github.bfox1.TheRift.common.entity.tileentity.TileEntityRiftChest;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;


/**
 * Created by bfox1 on 11/3/2016.
 */
public class RiftChest extends BlockContainer
{
    public static int TESTBLOCKGUI = 5;
    public boolean check = false;
    public RiftChest()
    {
        super(Material.ROCK);
        //this.setCreativeTab(RiftTabManager.RIFT_BLOCKS_TAB);

    }


    @SuppressWarnings("NullableProblems")
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityRiftChest();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public RiftChest setUnlocalizedName(String name)
    {
        return (RiftChest) super.setUnlocalizedName(name);
    }


    public RiftChest setBlockRegisterName(String s)
    {
        this.setRegistryName(s);
        return this;
    }


    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    /**
     * Gets the {@link IBlockState} to place
     * @param world The world the block is being placed in
     * @param pos The position the block is being placed at
     * @param facing The side the block is being placed on
     * @param hitX The X coordinate of the hit vector
     * @param hitY The Y coordinate of the hit vector
     * @param hitZ The Z coordinate of the hit vector
     * @param meta The metadata of {@link ItemStack} as processed by {@link Item#getMetadata(int)}
     * @param placer The entity placing the block
     * @return The state to be placed in the world
     */
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        /**
         * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
         * IBlockstate
         */
        ThreadLocalRandom random = ThreadLocalRandom.current();
        if(world.isRemote)
        for(int i = 0; i < 10; i++)
        {
            double x = pos.getX() + random.nextDouble(-2,2);
            double y = pos.getY() + random.nextDouble(-2,2);
            double z = pos.getZ() + random.nextDouble(-2, 2);

            world.spawnParticle(EnumParticleTypes.PORTAL, x, y, z, determineVelocity(x, pos.getX()), determineVelocity(y, pos.getY()), determineVelocity(z, pos.getZ()), new int[0]);
        }
        return getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
    }

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

    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        //super.onBlockAdded(worldIn, pos, state);
    }

    private double determineVelocity(double coord, double blockCoord)
    {
        if(coord < blockCoord)
        {
            return ThreadLocalRandom.current().nextDouble(0.5, 1);
        }
        else
        {
            return ThreadLocalRandom.current().nextDouble(-1, -0.5);
        }

    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if (rand.nextInt(100) == 0)
        {
           // worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
        }

        if(this.check)
        for (int i = 0; i < 4; ++i)
        {
            double d0 = (double)((float)pos.getX() + rand.nextFloat());
            double d1 = (double)((float)pos.getY() + rand.nextFloat());
            double d2 = (double)((float)pos.getZ() + rand.nextFloat());
            double d3 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            double d4 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            double d5 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            int j = rand.nextInt(2) * 2 - 1;

            if (worldIn.getBlockState(pos.west()).getBlock() != this && worldIn.getBlockState(pos.east()).getBlock() != this)
            {
                d0 = (double)pos.getX() + 0.5D + 0.25D * (double)j;
                d3 = (double)(rand.nextFloat() * 2.0F * (float)j);
            }
            else
            {
                d2 = (double)pos.getZ() + 0.5D + 0.25D * (double)j;
                d5 = (double)(rand.nextFloat() * 2.0F * (float)j);
            }

            worldIn.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5, new int[0]);
        }
    }

}
