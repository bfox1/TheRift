package io.github.bfox1.TheRift.init;

import io.github.bfox1.TheRift.client.creativetabs.RiftTabManager;
import io.github.bfox1.TheRift.common.TheRift;
import io.github.bfox1.TheRift.common.blocks.*;
import io.github.bfox1.TheRift.common.util.ClassReference;
import io.github.bfox1.TheRift.common.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by bfox1 on 4/3/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class BlockInit
{

    public static  HashMap<String, Block> RiftBlocks = new HashMap<>();
    public static final Set<Block> items = new HashSet<Block>();

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
            //items.add(block);

           // ItemBlock item = new ItemBlock(block);
           // item.setRegistryName(block.getRegistryName());

            //GameRegistry.register(item);
           // ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }



    }

    public static void initItemBlock(RegistryEvent.Register<Item> event)
    {
        for(Block block : RiftBlocks.values())
        {
            System.out.println(block.getRegistryName().toString());

            ItemBlock item = new ItemBlock(block);
            item.setRegistryName(block.getRegistryName().toString().substring(5));
            item.setUnlocalizedName(item.getRegistryName().toString());
            event.getRegistry().register(item);
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(block.getRegistryName().toString()));
            //ItemInit.items.add(item);

        }
        RiftBlocks.forEach((k,v) -> v.setCreativeTab(RiftTabManager.SaoBlocks) );
    }

    @Deprecated
    public static void registerRender()
    {
        System.out.println("Registering renders");
        //RiftBlocks.forEach((k,v) -> ModelLoader.setCustomModelResourceLocation(ItemBlock.getItemFromBlock(v), 0, new ModelResourceLocation(ItemBlock.getItemFromBlock(v).getRegistryName().toString())));
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
