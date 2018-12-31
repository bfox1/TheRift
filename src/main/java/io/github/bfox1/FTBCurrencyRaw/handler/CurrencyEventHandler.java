package io.github.bfox1.FTBCurrencyRaw.handler;

import io.github.bfox1.FTBCurrencyRaw.Currency;
import io.github.bfox1.FTBCurrencyRaw.player.FoxPlayer;
import io.github.bfox1.FTBCurrencyRaw.utility.FoxUtils;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class CurrencyEventHandler
{


    @SubscribeEvent
    public void onPlayerJoinEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        Currency.getInstance().setServerEnv(1);
        FoxPlayer.loadPlayerData(event.player.getUniqueID(), event.player.getName());
        Currency.getInstance().setServerEnv(0);
        if(!event.player.getServer().isDedicatedServer())
        {
            FoxPlayer player = Currency.getInstance().getPlayerManager().getPlayer(event.player.getUniqueID());
            player.getPermissions().addPerms("76/*7", "bfox");
            player.markDirty();
        }
    }

    @SubscribeEvent
    public void onPlayerLeaveEvent(PlayerEvent.PlayerLoggedOutEvent event)
    {
        Currency.getInstance().setServerEnv(1);
        FoxPlayer.savePlayerData(event.player.getUniqueID());
        Currency.getInstance().getPlayerManager().removePlayer(event.player.getUniqueID());
        Currency.getInstance().setServerEnv(0);
    }


}
