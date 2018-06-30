package io.github.bfox1.TheRift.common.util;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

import net.minecraft.item.Item;

/**
 * Created by bfox1 on 4/14/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class RegisterUtility
{
    public static void registerBlockItem(Block block, int meta, String blockName)
    {
        //ModelBakery.registerItemVariants(Item.getItemFromBlock(block), blockName);

        registerBlock(block, meta, blockName);
    }

    /**
     * Registers Blocks with different BlockStates or as they were called MetaData.
     * @param block The Block to register and render.
     * @param blockNames The Array of Stringed names of the Blocks.
     */
    public static void registerBlockMetaItem(Block block, String... blockNames)
    {
       // ModelBakery.registerItemVariants(Item.getItemFromBlock(block), blockNames);

        for(int i = 0; i < blockNames.length; i++)
        {
            registerBlock(block, i, blockNames[i].replaceAll("sao:", ""));
        }
    }
    public static void registerBlock(Block block , int meta, String file)
    {
        Item item = Item.getItemFromBlock(block);
        System.out.println(item);
        if (item != null)
        {
            //item.setRegistryName()
        }
        registerItem(item, meta, file);
    }
    public static void registerItem(Item item, String file)
    {
        registerItem(item, 0, file);
    }
    public static void registerItem(Item item, int meta, String file)
    {

        System.out.println(file);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(file, "inventory"));

    }
}
