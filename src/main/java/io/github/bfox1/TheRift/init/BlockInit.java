package io.github.bfox1.TheRift.init;

import com.google.common.base.Preconditions;
import io.github.bfox1.TheRift.client.creativetabs.RiftTabManager;
import io.github.bfox1.TheRift.common.blocks.*;
import io.github.bfox1.TheRift.common.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


import java.util.*;

/**
 * Created by bfox1 on 4/3/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
@Mod.EventBusSubscriber(modid = Reference.MODID)
public class BlockInit
{

    private static  HashMap<String, Block> RiftBlocks = new HashMap<>();

    static
    {

        RiftBlocks.put("riftchest", new RiftChest().setBlockRegisterName("riftchest"));
        RiftBlocks.put("dematerializer", new Dematerializer().setBlockRegisterName("dematerializer"));
        RiftBlocks.put("riftvessel", new RiftEssenceVessel().setBlockRegisterName("riftvessel"));
        RiftBlocks.put("processor", new EssenceProcessor(Material.IRON).setBlockRegisterName("processor"));

        setRegBlock("riftedobsidian", Material.GROUND);
    }

    /**
     * Subscribed Event Method which registers Blocks
     * @param event
     */
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        for(String blockName : RiftBlocks.keySet())
        {
        	final Block block = RiftBlocks.get(blockName);
			final ResourceLocation registryName = Objects.requireNonNull(block.getRegistryName());

            block.setUnlocalizedName(registryName.toString());

            event.getRegistry().register(block);
        }
    }

    /**
     * Subscribed event Method which registers ItemBlocks.
     * @param event
     */
    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event)
    {
        for(Block block : RiftBlocks.values())
        {

            ItemBlock item = new ItemBlock(block);
            ResourceLocation registryName = Preconditions.checkNotNull(block.getRegistryName(), "Block %s has null registry name", block);

            event.getRegistry().register(item.setRegistryName(registryName));
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(block.getRegistryName().toString()));

            block.setCreativeTab(RiftTabManager.RIFT_BLOCKS_TAB);
        }
        TileEntityInit.registerTileEntities();
       // RiftBlocks.forEach((k,v) -> v.setCreativeTab(RiftTabManager.RIFT_BLOCKS_TAB) );
    }

    /**
     * Helper Method for registering Simple Blocks and the material.
     * @param name
     * @param material
     */
    private static void setRegBlock(String name, Material material)
    {
        RiftBlocks.put(name, new Block(material).setRegistryName(name));
        System.out.println(name + " Has been found");
    }

    /**
     * Static Getter method for retriving Blocks from the HashMap.
     * @param name
     * @return
     */
    public static Block getRegItem(String name)
    {
        //TODO - Should check if block exists.
        return RiftBlocks.get(name);
    }

    public static HashMap<String, Block> getRiftBlocks()
    {
        return RiftBlocks;
    }

}
