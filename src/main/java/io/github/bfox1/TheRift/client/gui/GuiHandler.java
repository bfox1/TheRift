package io.github.bfox1.TheRift.client.gui;

import io.github.bfox1.TheRift.common.blocks.EssenceProcessor;
import io.github.bfox1.TheRift.common.blocks.RiftEssenceVessel;
import io.github.bfox1.TheRift.common.entity.tileentity.TileEntityEssenceProcessor;
import io.github.bfox1.TheRift.common.entity.tileentity.TileEntityRiftVessel;
import io.github.bfox1.TheRift.server.container.ContainerDematerializer;
import io.github.bfox1.TheRift.common.blocks.Dematerializer;
import io.github.bfox1.TheRift.common.entity.tileentity.TileEntityDematerializer;
import io.github.bfox1.TheRift.server.container.ContainerProcessor;
import io.github.bfox1.TheRift.server.container.ContainerRiftVessel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * Created by bfox1 on 11/5/2016.
 */
public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity e = world.getTileEntity(new BlockPos(x, y, z));


        if(ID == Dematerializer.DEMAT_GUI_INDEX)
        {
            if(e instanceof TileEntityDematerializer)
            {
                return new ContainerDematerializer(player.inventory, (TileEntityDematerializer) e);
            }
        }

        if(ID == RiftEssenceVessel.GUI_ESSENCE_VESSEL)
        {
            if(e instanceof TileEntityRiftVessel)
            {
                return new ContainerRiftVessel(player.inventory, (TileEntityRiftVessel) e);
            }
        }

        if(ID == EssenceProcessor.GUI_INDEX)
        {
            if(e instanceof TileEntityEssenceProcessor)
            {
                return new ContainerProcessor(player.inventory, (TileEntityEssenceProcessor)e);
            }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity entity = world.getTileEntity(new BlockPos(x, y, z));
        if(ID == Dematerializer.DEMAT_GUI_INDEX)
        {
            if(entity instanceof TileEntityDematerializer)
            {
                return new GuiDematerializer(player.inventory, (TileEntityDematerializer) entity);
            }
        }
        else if(ID == RiftEssenceVessel.GUI_ESSENCE_VESSEL)
        {
            if(entity instanceof TileEntityRiftVessel)
            {
                return new GuiRiftVessel(player.inventory, (TileEntityRiftVessel) entity);
            }
        }
        else if(ID == EssenceProcessor.GUI_INDEX)
        {
            if(entity instanceof TileEntityEssenceProcessor)
            {
                return new GuiProcessor(player.inventory, (TileEntityEssenceProcessor)entity);
            }
        }
        return null;
    }
}
