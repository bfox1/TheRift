package io.github.bfox1.FTBCurrencyRaw.handler;

import io.github.bfox1.FTBCurrencyRaw.Currency;
import io.github.bfox1.FTBCurrencyRaw.FoxCommandManager;
import io.github.bfox1.FTBCurrencyRaw.FoxCurrency;
import io.github.bfox1.FTBCurrencyRaw.command.FcCommand;
import io.github.bfox1.FTBCurrencyRaw.data.PermissionAccessTokenData;
import io.github.bfox1.FTBCurrencyRaw.event.FcCommandEvent;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommandEventHandler
{
    private final int accessToken = new PermissionAccessTokenData().loadToken(); //Unused Token System.
    public CommandEventHandler()
    {

    }


    /**
     * TODO - Still needs to be cleaned parse basic commands and feed them into FoxCommandManager
     * @param event
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerCommand(CommandEvent event)
    {
        event.setCanceled(true);

        FcCommand.CommandResult result = Currency.getInstance().getCommandManger().execute(event.getSender().getServer(), event.getCommand().getName(), event.getParameters(), event.getSender());

        checkResult(result, event.getSender());
    }



    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerChat(ServerChatEvent event)
    {
        String seq = "./";

        if(event.getMessage().startsWith(seq))
        {
            System.out.println(event.getPlayer());
            event.setCanceled(true);
            String commandLine = event.getMessage().replaceFirst(seq, "");

            String[] commandList = commandLine.split(" ");

            String commandName = commandList[0];

            String[] finishedCommandList = new String[commandList.length - 1];

            int i = 0;
            for(String s : commandList)
            {
                System.out.println(s);
                if(!s.equals(commandName))
                {
                    finishedCommandList[i] = s;
                    i++;
            }
            }

            FcCommand.CommandResult result = Currency.getInstance().getCommandManger().execute(event.getPlayer().getServer(), commandName, finishedCommandList, event.getPlayer());

            checkResult(result, event.getPlayer());
        }
    }

    private final void checkResult(FcCommand.CommandResult result, ICommandSender sender)
    {
        if(result.tellPlayer())
        {
            if(!result.getCommandName().equalsIgnoreCase(""))
            {
                sender.sendMessage(new TextComponentString("Issuer: " + result.getCommandName()));
            }
            sender.sendMessage(new TextComponentString("Server: " + result.getPlayerMessage()));
        }

        System.out.println(sender.getName() + " : " + result.getMessage());
    }

}



