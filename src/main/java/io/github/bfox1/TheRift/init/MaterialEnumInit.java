package io.github.bfox1.TheRift.init;

import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

import java.util.HashMap;

/**
 * Created by bfox1 on 12/9/2016.
 */
public class MaterialEnumInit
{
    public static final HashMap<String, Item.ToolMaterial> riftToolMaterial = new HashMap<String, Item.ToolMaterial>();
    public static final HashMap<String, Item.ToolMaterial> riftArmorMaterial = new HashMap<>();

    public static void registerToolMaterial()
    {
        buildToolMaterial(RiftMaterialEnum.RIFT_CHUNK, 3, 2500, 9.5F, 4.5F,15);
        buildToolMaterial(RiftMaterialEnum.RIFTED_OBSIDIAN, 4, 4000, 10.0F, 5.5F, 12);
        buildToolMaterial(RiftMaterialEnum.UNCHARTED, 5, 8000, 12.0F, 7.5F, 17);
    }

    private static void buildToolMaterial(RiftMaterialEnum mat, int harvestLevel, int maxUses, float eff, float dmg, int enchant)
    {
        if(!riftToolMaterial.containsKey(mat.getName()))
        {
            Item.ToolMaterial material = EnumHelper.addToolMaterial(mat.getName(), harvestLevel, maxUses, eff, dmg, enchant);
            riftToolMaterial.put(mat.getName(), material);
        }
        else
        {
            throw new IllegalArgumentException("The name " + mat.getName() + " already exists in the Map! please choose a different name" );
        }
    }

    public static Item.ToolMaterial getToolMaterial(RiftMaterialEnum materialEnum)
    {
        return riftToolMaterial.get(materialEnum.getName());
    }

    public enum RiftMaterialEnum
    {
        RIFT_CHUNK("rift_chunk"),
        RIFTED_OBSIDIAN("rifted_obsidian"),
        UNCHARTED("uncharted");

        private final String name;

        RiftMaterialEnum(String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return name;
        }

        public boolean compare(String string)
        {
            return this.name.equalsIgnoreCase(string);
        }
    }

}
