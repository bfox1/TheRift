package io.github.bfox1.TheRift.init;

import io.github.bfox1.TheRift.common.entity.tileentity.TileEntityDematerializer;
import io.github.bfox1.TheRift.common.entity.tileentity.TileEntityEssenceProcessor;
import io.github.bfox1.TheRift.common.entity.tileentity.TileEntityRiftChest;
import io.github.bfox1.TheRift.common.entity.tileentity.TileEntityRiftVessel;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by bfox1 on 12/8/2016.
 */
public class TileEntityInit
{

    public static void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileEntityRiftVessel.class, "RiftVessel");
        GameRegistry.registerTileEntity(TileEntityRiftChest.class, "RiftChest");
        GameRegistry.registerTileEntity(TileEntityDematerializer.class, "Dematerializer");
        GameRegistry.registerTileEntity(TileEntityEssenceProcessor.class, "Processor");
    }
}
