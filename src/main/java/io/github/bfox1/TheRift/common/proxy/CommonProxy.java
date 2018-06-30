package io.github.bfox1.TheRift.common.proxy;



import io.github.bfox1.TheRift.client.gui.GuiHandler;
import io.github.bfox1.TheRift.common.TheRift;
import io.github.bfox1.TheRift.common.entity.mobs.EntityRiftCreeper;
import io.github.bfox1.TheRift.common.entity.tileentity.*;
import io.github.bfox1.TheRift.common.event.ForgeEventHandler;
import io.github.bfox1.TheRift.common.util.Reference;
import io.github.bfox1.TheRift.common.world.storage.loot.RiftLootTableList;
import io.github.bfox1.TheRift.init.*;
import io.github.bfox1.TheRift.riftessence.RiftLinkedSide;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.item.Item;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableManager;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by bfox1 on 4/2/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class CommonProxy implements RiftProxy
{


    public static HashMap<UUID, RiftLinkedSide[]> riftLinkedConnection = new HashMap<>();


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

    @Override
    public void registerEventHandlers()
    {

        MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());

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

    @Override
    public void preInit(FMLPreInitializationEvent event) {


        MaterialEnumInit.registerToolMaterial();


        TileEntityInit.registerTileEntities();

        EntityInit.registerEntities();
        

    }

    @Override
    public void init(FMLInitializationEvent event) {

        RecipeInit.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(Reference.MODID, new GuiHandler());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
    	registerEventHandlers();
    }

    @Override
    public void registerItems(RegistryEvent.Register<Item> event)
    {
        ItemInit.init(event);
        BlockInit.initItemBlock(event);
    }

    @Override
    public void registerBlocks(RegistryEvent.Register<Block> event)
    {
        BlockInit.init(event);
    }
}
