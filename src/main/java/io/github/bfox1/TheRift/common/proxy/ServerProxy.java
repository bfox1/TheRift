package io.github.bfox1.TheRift.common.proxy;


import io.github.bfox1.TheRift.common.event.ServerEventHandler;
import io.github.bfox1.TheRift.riftessence.RiftLinkedSide;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by bfox1 on 4/2/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class ServerProxy implements RiftProxy
{
    public static HashMap<UUID, RiftLinkedSide[]> riftLinkedConnection = new HashMap<>();

    public void preInit(FMLPreInitializationEvent event)
    {

    }

    public void init(FMLInitializationEvent event)
    {
    }

    public void postInit(FMLPostInitializationEvent event)
    {
    }

    @Override
    public void registerItems(RegistryEvent.Register<Item> event)
    {

    }

    @Override
    public void registerBlocks(RegistryEvent.Register<Block> event)
    {

    }

    @Override
    public void initClientConfig(File file)
    {

    }

    @Override
    public void registerTileEntities()
    {

    }

    @Override
    public void initRenderingAndTexture()
    {

    }

    public void registerEventHandlers()
	{
		MinecraftForge.EVENT_BUS.register(new ServerEventHandler());
	}

    @Override
    public void registerKeyBindings()
    {

    }

    @Override
    public void registerEntityLiving()
    {

    }

    @Override
    public void registerGlobalEntity()
    {

    }

    @Override
    public void registerDimension()
    {

    }

    @Override
    public void addChestLoot()
    {

    }

    @Override
    public void registerRenderers()
    {

    }
}
