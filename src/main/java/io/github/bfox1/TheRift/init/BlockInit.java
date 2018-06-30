package io.github.bfox1.TheRift.init;

import io.github.bfox1.TheRift.client.creativetabs.RiftTabManager;
import io.github.bfox1.TheRift.common.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashMap;
import java.util.List;

/**
 * Created by bfox1 on 4/3/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class BlockInit
{

    public static final HashMap<String, Block> RiftBlocks = new HashMap<>();


    static
    {

        RiftBlocks.put("riftchest", new RiftChest().setBlockRegisterName("riftchest"));
        RiftBlocks.put("dematerializer", new Dematerializer().setBlockRegisterName("dematerializer"));
        setRegBlock("riftedobsidian", Material.GROUND);
        RiftBlocks.put("riftvessel", new RiftEssenceVessel().setBlockRegisterName("riftvessel"));
        RiftBlocks.put("processor", new EssenceProcessor(Material.IRON).setBlockRegisterName("processor"));
    }



    public static void init(RegistryEvent.Register<Block> event)
    {


        for(Block block : RiftBlocks.values())
        {

            block.setUnlocalizedName(block.getRegistryName().toString());
            event.getRegistry().register(block);

           // ItemBlock item = new ItemBlock(block);
           // item.setRegistryName(block.getRegistryName());

            //GameRegistry.register(item);
           // ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }

        RiftBlocks.forEach((k,v) -> v.setCreativeTab(RiftTabManager.SaoBlocks) );

    }

    public static void initItemBlock(RegistryEvent.Register<Item> event)
    {
        for(Block block : RiftBlocks.values())
        {
            ItemBlock item = new ItemBlock(block);
            item.setRegistryName(block.getRegistryName());

            event.getRegistry().register(item);
        }
    }
    public static void registerRender()
    {
        RiftBlocks.forEach((k,v) -> ModelLoader.setCustomModelResourceLocation(ItemBlock.getItemFromBlock(v), 0, new ModelResourceLocation(ItemBlock.getItemFromBlock(v).getRegistryName(), "inventory")));
    }

    private static void setRegBlock(String name, Material material)
    {
        RiftBlocks.put(name, new Block(material).setRegistryName(name));
        System.out.println(name + " Has been found");
    }

    public static Block getRegItem(String name)
    {
        return RiftBlocks.get(name);
    }

}
