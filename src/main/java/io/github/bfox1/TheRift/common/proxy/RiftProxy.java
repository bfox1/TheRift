package io.github.bfox1.TheRift.common.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

/**
 * Created by bfox1 on 4/2/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public interface RiftProxy
{
    void initClientConfig(File file);

    void registerTileEntities();

    void initRenderingAndTexture();

    void registerEventHandlers();

    void registerKeyBindings();

    void registerEntityLiving();

    void registerGlobalEntity();

    void registerDimension();

    void addChestLoot();

    void registerRenderers();

    void preInit(FMLPreInitializationEvent event);

    void init(FMLInitializationEvent event);

    void postInit(FMLPostInitializationEvent event);

    void registerItems(RegistryEvent.Register<Item> event);

    void registerBlocks(RegistryEvent.Register<Block> event);
}
