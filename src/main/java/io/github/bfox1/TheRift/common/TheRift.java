package io.github.bfox1.TheRift.common;


import io.github.bfox1.TheRift.client.gui.GuiHandler;
import io.github.bfox1.TheRift.common.event.ForgeEventHandler;
import io.github.bfox1.TheRift.common.proxy.RiftProxy;
import io.github.bfox1.TheRift.common.util.LogHelper;
import io.github.bfox1.TheRift.common.util.Reference;
import io.github.bfox1.TheRift.init.EntityInit;
import io.github.bfox1.TheRift.init.MaterialEnumInit;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

import java.util.logging.Logger;

/**
 * Created by bfox1 on 4/2/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class TheRift
{
    @Mod.Instance(Reference.MODID)
    public static TheRift instance;
    public Logger logger;

    public static final SimpleNetworkWrapper NETWORK_WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);

    
    public static int modGuiIndex = 0;

    public static boolean flag;

    @SidedProxy(clientSide = Reference.CLIENTPROXY, serverSide = Reference.SERVERPROXY)
    public static RiftProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LogHelper.logger = event.getModLog();

        flag = event.getSide().isServer();

        MaterialEnumInit.registerToolMaterial();

        EntityInit.registerEntities();

        LogHelper.info("Finally Entering the Modding Scene! ~bfox1");
        LogHelper.info("Lets begin the Pre-initialization of The Rift!");
        proxy.preInit(event);
        LogHelper.info("Pre-initialization is complete!");
    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        LogHelper.info("Initialization Beginning.");
        NetworkRegistry.INSTANCE.registerGuiHandler(Reference.MODID, new GuiHandler());
        LogHelper.info("Initialization Complete!");

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        LogHelper.info("Post-Initialization Beginning");
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());
        LogHelper.info("Post-Initialization Complete");


    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartedEvent event)
    {
        LogHelper.info("Meh, Server stuff, nothing registering here :>");
        //((ServerCommandManager)MinecraftServer.getServer().getCommandManager()).registerCommand(new CommandSao());
    }


}
