package io.github.bfox1.TheRift.init;

import io.github.bfox1.TheRift.common.TheRift;
import io.github.bfox1.TheRift.common.util.Reference;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class RegisterStuff
{
    @SubscribeEvent
    public static void registerItemEvent(RegistryEvent.Register<Item> event)
    {
        System.out.println("I am registering! :) ");
        TheRift.proxy.registerItems(event);
    }
}
