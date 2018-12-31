package io.github.bfox1.FTBCurrencyRaw;

import io.github.bfox1.FTBCurrencyRaw.player.FoxPlayer;
import net.minecraft.util.ITickable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class FoxPlayerManager implements ITickable
{

    private final HashMap<UUID, FoxPlayer> currencyHashMap;

    private final List<UUID> updateList;
    private final List<UUID> overflow;
    private boolean lock;
    int count = 0;

    public FoxPlayerManager()
    {
        this.currencyHashMap = new HashMap<>();
        this.updateList = new ArrayList<>();
        this.overflow = new ArrayList<>();
    }

    public HashMap<UUID, FoxPlayer> getCurrencyHashMap() {
        return currencyHashMap;
    }

    public FoxPlayer getPlayer(UUID uuid)
    {
        return currencyHashMap.get(uuid);
    }

    public FoxPlayer getPlayerByName(String name)
    {
        for(FoxPlayer player : currencyHashMap.values())
        {
            if(player.getPlayer().getName().equalsIgnoreCase(name))
            {
                return player;
            }
        }


        return null;
    }

    public void addPlayer(FoxPlayer player)
    {
        if (Currency.getInstance().getServerEnv() == 1)
        {
            this.currencyHashMap.put(player.getPlayerId(), player);

        }
        else
            {
            Currency.getInstance().getLogger().debug("Server Env is not compatible. Cannot add Player to Manager.");
        }

    }
    public void removePlayer(UUID id)
    {
        if(Currency.getInstance().getServerEnv() == 1)
        {
            this.currencyHashMap.remove(id);
        }
        else
        {
            Currency.getInstance().getLogger().debug("Server Env is not compatible! Cannot remove player from manager.");
        }
    }

    public void addToUpdate(UUID uuid)
    {
        if(lock)
        {
            this.overflow.add(uuid);
        }
        else if(!updateList.contains(uuid))
        {
            this.updateList.add(uuid);
        }

    }

    private void overToUpdate()
    {
        this.updateList.addAll(overflow);
    }



    @Override
    public void update()
    {
        if(count == 60)
        {
            this.lock = true;
            for(UUID id : updateList)
            {
                FoxPlayer.savePlayerData(id);
            }
            this.lock = false;
            overToUpdate();
            count = 0;
        }
        count++;
    }
}
