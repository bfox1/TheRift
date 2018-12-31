package io.github.bfox1.TheRift.common.world.storage.loot;

import io.github.bfox1.TheRift.common.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

/**
 * Created by bfox1 on 12/3/2016.
 */
public class RiftLootTableList
{
    public static ResourceLocation RIFT_CREEPER = register("entities/rift_creeper");



    private static ResourceLocation register(String name)
    {
        return LootTableList.register(new ResourceLocation(Reference.MODID, name));
    }
}
