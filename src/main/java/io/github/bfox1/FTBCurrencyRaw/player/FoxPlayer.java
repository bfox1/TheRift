package io.github.bfox1.FTBCurrencyRaw.player;

import io.github.bfox1.FTBCurrencyRaw.Currency;
import io.github.bfox1.FTBCurrencyRaw.FoxCurrency;
import io.github.bfox1.FTBCurrencyRaw.utility.Color;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.world.BossInfo;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class FoxPlayer
{

    private final FoxCurrency currency;
    private final FoxPermissions permissions;
    private final UUID playerId;

    private FoxPlayer(UUID uuid)
    {
        this.currency = new FoxCurrency();
        this.playerId = uuid;
        this.permissions = new FoxPermissions();
    }

    public final FoxCurrency getCurrency() {
        return currency;
    }


    public UUID getPlayerId() {
        return playerId;
    }

    public final FoxPermissions getPermissions()
    {
        return permissions;
    }

    public final EntityPlayerMP getPlayer()
    {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(this.playerId);
    }

    public final void sendMessage(String message)
    {

        EntityPlayerMP player = getPlayer();
        player.sendMessage(new TextComponentString(message));
    }

    public final void sendPluginMessage(String prefix, String message, Color prefixColor, Color bracketColor, Color messageColor)
    {
        EntityPlayerMP player = getPlayer();
        String fullMessage = bracketColor.getColorCode()+"["+prefixColor.getColorCode()+prefix+bracketColor.getColorCode()+"]"+Color.WHITE.getColorCode()+" : "+messageColor.getColorCode()+message;

        player.sendMessage(new TextComponentString(fullMessage));
    }

    public final void markDirty()
    {
        Currency.getInstance().getPlayerManager().addToUpdate(playerId);
    }






    public static void loadPlayerData(UUID id, String playerName)
    {

        PlayerData data = new PlayerData();

        Currency.getInstance().getLogger().info("We are trying to Load Player Currency data.");
        if(data.hasData(id))
        {
            Currency.getInstance().getLogger().info("Loading currency data.");
            HashMap<String, Object> serializedData = data.loadData(id);

            FoxPlayer player = new FoxPlayer(id);

            player.getCurrency().setCurrency((Integer) serializedData.get("currency"));

            Currency.getInstance().setServerEnv(2);
            if(serializedData.containsKey("perms"))
            {
                List<String> perms = (List<String>) serializedData.get("perms");

                player.getPermissions().onLoadPerms(perms);

                Currency.getInstance().setServerEnv(1);
            }




            Currency.getInstance().getPlayerManager().addPlayer(player);
        }
        else
        {
            FoxPlayer player = new FoxPlayer(id);
            player.getPermissions().addPerms("currency.basic", playerName);
            Currency.getInstance().getPlayerManager().addPlayer(player);

        }
    }

    public static void savePlayerData(UUID uuid)
    {

        PlayerData data = new PlayerData();

        FoxPlayer player = Currency.getInstance().getPlayerManager().getPlayer(uuid);


        if(player != null)
        {
            HashMap<String, Object> deserializeMap = new HashMap<>();

            deserializeMap.put("currency", player.getCurrency().getCurrency());

            Currency.getInstance().setServerEnv(2);
            deserializeMap.put("perms", player.getPermissions().getPerms());
            Currency.getInstance().setServerEnv(1);

            data.saveData(deserializeMap, uuid);
        }
    }
}
