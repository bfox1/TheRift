package io.github.bfox1.TheRift.init;

import io.github.bfox1.TheRift.common.util.Reference;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.datafix.fixes.ItemIntIDToString;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by bfox1 on 11/11/2016.
 */
public class RecipeInit
{
    public static void init()
    {
        GameRegistry.addSmelting(new ItemStack((Item)ItemInit.RIFTITEM.get("encrustedsteel"),1), new ItemStack((Item)ItemInit.RIFTITEM.get("materializedsteel"),2), 10.0F);
        GameRegistry.addSmelting(new ItemStack((Item)ItemInit.RIFTITEM.get("soliddiamondchunk"),1), new ItemStack((Item)ItemInit.RIFTITEM.get("diamondcore"),1),20.0F);
        /*GameRegistry.addShapelessRecipe(new ItemStack((Item)ItemInit.RIFTITEM.get("essencecluster"), 1), "DDD","DDD","DDD", 'D', (Item)ItemInit.RIFTITEM.get("riftessencechunk"));

        GameRegistry.addShapedRecipe(new ItemStack(BlockInit.RiftBlocks.get("dematerializer"), 1),
                "DOD",
                "OEO",
                "DPD",
                'D', ItemInit.RIFTITEM.get("diamondcore"), 'O', BlockInit.RiftBlocks.get("riftedobsidian"), 'E', ItemInit.RIFTITEM.get("unknowneye").getItem(), 'P', Items.ENDER_PEARL
        );

        GameRegistry.addShapedRecipe(new ItemStack((Item)ItemInit.RIFTITEM.get("encrustedsteel"),2),
                "RRR",
                "IPI",
                "RRR",
                'R', Items.REDSTONE, 'I', Items.IRON_INGOT, 'P', Items.ENDER_PEARL
        );

        GameRegistry.addShapedRecipe(new ItemStack((Item)ItemInit.RIFTITEM.get("empoweredpearl"),1),
                "EPE",
                "PDP",
                "EPE",
                'E',Items.EMERALD, 'P', Items.ENDER_PEARL, 'D',Items.DIAMOND
        );

        GameRegistry.addShapedRecipe(new ItemStack((Item)ItemInit.RIFTITEM.get("unknowneye"),1),
                "PRP",
                "PEP",
                "PRP",
                'P', Items.ENDER_PEARL, 'R', Items.BLAZE_ROD, 'E', Items.ENDER_EYE
        );

        GameRegistry.addShapedRecipe(new ItemStack((Item)ItemInit.RIFTITEM.get("soliddiamondchunk"),1),
                "DDD",
                "DPD",
                "DDD",
                'D', Blocks.DIAMOND_BLOCK, 'P', Items.ENDER_PEARL);

        GameRegistry.addShapedRecipe(new ItemStack((Item)ItemInit.RIFTITEM.get("riftedobsidian"), 4),
                "PPP",
                "POP",
                "PPP",
                'P', Items.ENDER_PEARL, 'O', Blocks.OBSIDIAN);
        GameRegistry.addShapedRecipe(new ItemStack(BlockInit.RiftBlocks.get("riftedobsidian"), 4),
                "PPP",
                "POP",
                "PPP",
                'P', Items.ENDER_PEARL, 'O', Blocks.OBSIDIAN);
        GameRegistry.addShapedRecipe(new ItemStack((Item)ItemInit.RIFTITEM.get("riftvessel"), 1),
                "RUR",
                "CEC",
                "RUR",
                'R', BlockInit.getRegItem("riftedobsidian"), 'U', ItemInit.getRegItem("unknowneye").getItem(), 'C', ItemInit.getRegItem("essencecluster").getItem(), 'E', ItemInit.getRegItem("empoweredpearl").getItem());

        GameRegistry.addShapedRecipe(new ItemStack(BlockInit.getRegItem("processor"),1),
                "SDS",
                "SCS",
                "SDS",
                'S', ItemInit.getRegItem("materializedsteel").getItem(), 'D', ItemInit.getRegItem("diamondcore").getItem(), 'C', ItemInit.getRegItem("essencecluster").getItem());

        GameRegistry.addShapedRecipe(new ItemStack(BlockInit.getRegItem("riftchest"),1),
                "CCC","CUC","CCC", 'C', ItemInit.getRegItem("essencecluster"), 'U', ItemInit.getRegItem("unknowneye"));

        GameRegistry.addShapedRecipe(new ItemStack(ItemInit.getRegItem("riftblade").getItem(), 1), " M ", " M ", " C ", 'M', ItemInit.getRegItem("materializedsteel").getItem(), 'C', ItemInit.getRegItem("diamondcore").getItem());

*/
    }
}
