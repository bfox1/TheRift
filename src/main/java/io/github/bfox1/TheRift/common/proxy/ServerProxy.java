package io.github.bfox1.TheRift.common.proxy;


import io.github.bfox1.TheRift.common.event.ForgeEventHandler;
import io.github.bfox1.TheRift.common.event.ServerEventHandler;
import io.github.bfox1.TheRift.common.util.Settings;
import io.github.bfox1.TheRift.init.BlockInit;
import io.github.bfox1.TheRift.init.ItemInit;
import io.github.bfox1.TheRift.riftessence.RiftLinkedSide;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by bfox1 on 4/2/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class ServerProxy extends CommonProxy
{


    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
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
       // registerEventHandlers();
    }

    @Override
    public void registerEventHandlers()
    {
        System.out.println("IM LOADING");
        super.registerEventHandlers();
        MinecraftForge.EVENT_BUS.register(new ServerEventHandler());
    }
}
