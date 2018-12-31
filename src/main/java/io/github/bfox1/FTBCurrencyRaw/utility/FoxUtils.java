package io.github.bfox1.FTBCurrencyRaw.utility;

import io.github.bfox1.FTBCurrencyRaw.Currency;
import io.github.bfox1.FTBCurrencyRaw.player.FoxPlayer;
import io.github.bfox1.FTBCurrencyRaw.player.PlayerData;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class FoxUtils
{

    public static boolean verifyAuth()
    {
        return Currency.getInstance().getServerEnv() == 1;
    }

    public static boolean verifyAuthService()
    {
        System.out.println("Auth Service has been changed to 2");
        return Currency.getInstance().getServerEnv() == 2;
    }

    public static void sendPluginMessage(ICommandSender sender, String prefix, String message, Color prefixColor, Color bracketColor, Color messageColor)
    {
        String fullMessage = bracketColor.getColorCode()+"["+prefixColor.getColorCode()+prefix+bracketColor.getColorCode()+"]"+Color.WHITE.getColorCode()+" : "+messageColor.getColorCode()+message;

        sender.sendMessage(new TextComponentString(fullMessage));
    }

    public static void sendPluginMessage(ICommandSender sender, String prefix, String message, Color bracketColor, Color messageColor)
    {
        String fullMessage = bracketColor.getColorCode()+"["+prefix+bracketColor.getColorCode()+"]"+Color.WHITE.getColorCode()+" : "+messageColor.getColorCode()+message;
        sender.sendMessage(new TextComponentString(fullMessage));

    }

    public static void sendPluginMessage(ICommandSender sender, String prefix, String message, Color bracketColor)
    {
        String fullMessage = bracketColor.getColorCode()+"["+prefix+bracketColor.getColorCode()+"]"+Color.WHITE.getColorCode()+" : "+message;
        sender.sendMessage(new TextComponentString(fullMessage));
    }


    public static void sendMessage(ICommandSender sender, String message)
    {
        sender.sendMessage(new TextComponentString(message));
    }


}
