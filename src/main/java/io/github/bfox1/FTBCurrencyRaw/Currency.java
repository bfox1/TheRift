package io.github.bfox1.FTBCurrencyRaw;


import io.github.bfox1.FTBCurrencyRaw.command.CurrencyCommand;
import io.github.bfox1.FTBCurrencyRaw.player.PlayerData;
import io.github.bfox1.FTBCurrencyRaw.handler.CommandEventHandler;
import io.github.bfox1.FTBCurrencyRaw.handler.CurrencyEventHandler;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

import java.util.Map;


@Mod(modid = Currency.MODID, name = Currency.NAME, version = Currency.VERSION, acceptableRemoteVersions = "*")
public class Currency
{

    public static final String MODID = "fc";
    public static final String NAME = "Fox Currency";
    public static final String VERSION = "0.1";

    private final FoxPlayerManager playerManager = new FoxPlayerManager();

    private final FoxCommandManager commandManger = new FoxCommandManager(this);

    private int serverEnv = 0;

    private static Currency instance;

    private static Logger logger;

    public Currency()
    {
        instance = this;
    }

    public static Currency getInstance() {
        return instance;
    }

    public Logger getLogger()
    {
        return logger;
    }


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code



        logger.info("ServerEnv: " + serverEnv );
        //TODO: Mod Changes should be done here.
        logger.info("ServerEnv: " + serverEnv);

    }

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new CurrencyEventHandler());
        MinecraftForge.EVENT_BUS.register(new CommandEventHandler());



        logger.info("ServerEnv: " + serverEnv );
        PlayerData.loadDataSettings();
        logger.info("ServerEnv: " + serverEnv);
        this.commandManger.addCommand(new CurrencyCommand("currency.basic"));
        //FcCommand commands = new FcCommand();

        //event.registerServerCommand(commands);

    }

    @Mod.EventHandler
    public void onServerStarted(FMLServerStartedEvent event)
    {
        Map<String, ICommand> map = FMLCommonHandler.instance().getMinecraftServerInstance().commandManager.getCommands();

        for(ICommand command : map.values())
        {
            this.getCommandManger().createCommand(command);
        }
    }




    public final FoxPlayerManager getPlayerManager()
    {
        return playerManager;
    }

    public final FoxCommandManager getCommandManger()
    {
        return this.commandManger;
    }

    public final int getServerEnv()
    {
        logger.info("ServerEnv is being looked at.");
        return serverEnv;
    }

    public final void setServerEnv(int i)
    {
        logger.info("ServerEnv is being changed.");
        logger.info("ServerEnv: " + i);
        serverEnv = i;
    }
}
