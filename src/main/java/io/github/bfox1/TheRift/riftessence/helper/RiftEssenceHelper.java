package io.github.bfox1.TheRift.riftessence.helper;

import io.github.bfox1.TheRift.api.riftessence.IRiftEssence;

/**
 * Created by bfox1 on 12/2/2016.
 */
public class RiftEssenceHelper
{

    public static void addRiftEssence(IRiftEssence incoming, IRiftEssence outgoing, int adding)
    {
        if(incoming.getRiftEssence() !=0)
        {
            if(outgoing.getRiftEssence() != outgoing.getMaxRiftEssence())
            {
                int n = incoming.removeRiftEssence(adding);
                outgoing.addRawEssence(n);
            }
        }
    }

    public static void removeRiftEssence(IRiftEssence incoming, IRiftEssence outgoing, int removing)
    {
        if(incoming.getRiftEssence() != 0)
        {
            if(outgoing.getRiftEssence() != outgoing.getMaxRiftEssence())
            {
                int adding = incoming.removeRiftEssence(removing);
                outgoing.addRawEssence(adding);
            }
        }
    }
}
