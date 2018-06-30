package io.github.bfox1.TheRift.common.proxy;


import io.github.bfox1.TheRift.client.RiftRenderFactory;
import io.github.bfox1.TheRift.common.entity.mobs.EntityRiftCreeper;
import io.github.bfox1.TheRift.common.event.RiftSkinManager;
import io.github.bfox1.TheRift.common.util.RegisterUtility;
import io.github.bfox1.TheRift.common.util.Settings;
import io.github.bfox1.TheRift.init.BlockInit;
import io.github.bfox1.TheRift.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;

/**
 * Created by bfox1 on 4/2/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class ClientProxy extends CommonProxy
{
    public static Settings settings;
    public static RiftSkinManager skinManager;


    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        settings = new Settings(event);
        BlockInit.registerRender();
        ItemInit.registerRend();
        RiftRenderFactory.registerRenderEntityFactory();
        skinManager = new RiftSkinManager(Minecraft.getMinecraft().getTextureManager(), new File("parent"), Minecraft.getMinecraft().getSessionService());


    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
        //blockRenderRegister(BlockInit.aincradCobbleVariation);

    }
    
    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
    	super.postInit(event);
    }

    /**
     * Method is used to not only register but render the block and itemBlock.
     * @param block
     */
    public void blockRenderRegister(Block block)
    {

        //registerBlockMetaItem(block, "sao:AincradCobble_block_one","sao:AincradCobble_block_two","sao:AincradCobble_block_three","sao:AincradCobble_block_four");
        //RegisterUtility.registerBlockMetaItem(block, ((SaoBlockVariationAbstract)block).getSubtypeArray());
    }

    /**
     * Registers a single block.
     * @param block The Block to register and render.
     * @param meta The id of the MEta. Should be left as 0, if anything else, use ClientProxy#registerBlockMetaItem
     * @param blockName The Blocks name.
     */

}
