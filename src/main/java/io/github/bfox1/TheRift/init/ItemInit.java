package io.github.bfox1.TheRift.init;


import io.github.bfox1.TheRift.api.items.IRiftItem;
import io.github.bfox1.TheRift.client.creativetabs.RiftTabManager;
import io.github.bfox1.TheRift.common.TheRift;
import io.github.bfox1.TheRift.common.items.*;
import io.github.bfox1.TheRift.common.items.swords.ItemSwordRiftBlade;
import io.github.bfox1.TheRift.common.items.tools.ItemRiftPickaxe;
import io.github.bfox1.TheRift.common.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ItemInit
{


    public static HashMap<String, IRiftItem> RIFTITEM = new HashMap<>();
    //public static  Set<Item> items = new HashSet<Item>();

     static
    {



        RIFTITEM.put("riftessencechunk", new RiftEssenceChunk().setRegName("riftessencechunk"));
        RIFTITEM.put("riftmechanism", new RiftMechanism().setRegName("riftmechanism"));
        for(int i = 0; i < 3; i++)
        {
            ContainmentValve valve = ContainmentValve.getTieredValve(i);
            if(valve != null)
            RIFTITEM.put(valve.getContainmentValve().getValveName(), valve.setRegName(valve.getContainmentValve().getValveName()));
        }
        RIFTITEM.put("unknowneye", new RiftItem().setRegName("unknowneye"));
        RIFTITEM.put("materializedsteel",new RiftItem().setRegName("materializedsteel"));
        RIFTITEM.put("empoweredpearl",new RiftItem().setRegName("empoweredpearl"));
        RIFTITEM.put("diamondcore", new RiftItem().setRegName("diamondcore"));
        setRegItem("encrustedsteel");
        setRegItem("soliddiamondchunk");
        setRegItem("essencecluster");
        RIFTITEM.put("riftfluxcapacitor", new ItemRiftFluxCapacitor().setRegName("riftfluxcapacitor"));
        RIFTITEM.put("riftblade", new ItemSwordRiftBlade(Item.ToolMaterial.DIAMOND).setRegName("riftblade"));
        setRegItem("pearl_fragment");
        setRegItem(new ItemRiftPickaxe(MaterialEnumInit.getToolMaterial(MaterialEnumInit.RiftMaterialEnum.RIFT_CHUNK)), "essence_chunk_pickaxe");
        setRegItem(new DebugItem(), "debug_item");
    }


    public static void init(RegistryEvent.Register<Item> event)
    {
        for(IRiftItem item : RIFTITEM.values())
        {
            item.getItem().setUnlocalizedName(item.getItem().getRegistryName().toString());

            event.getRegistry().register(item.getItem());


        }

        RIFTITEM.forEach((k,v) -> ((Item)v).setCreativeTab(RiftTabManager.SaoItems) );
    }

    public static void registerRend()
    {
        RIFTITEM.forEach((k,v) -> ModelLoader.setCustomModelResourceLocation(((Item)v), 0, new ModelResourceLocation(((Item)v).getRegistryName(), "inventory")) );

    }

	public static String getUnlocalizedItemName(int id)
	{
		Item item = Item.getItemById(id);
		return item.getUnlocalizedName();
	}

    private static void setRegItem(String name)
    {
        RIFTITEM.put(name, new RiftItem().setRegName(name));
    }

    private static void setRegItem(IRiftItem item, String name)
    {
        RIFTITEM.put(name, item.setRegName(name));
    }

    public static IRiftItem getRegItem(String name)
    {
        return RIFTITEM.get(name);
    }




}
