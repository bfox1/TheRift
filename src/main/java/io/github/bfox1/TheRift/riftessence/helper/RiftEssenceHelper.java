package io.github.bfox1.TheRift.riftessence.helper;

import io.github.bfox1.TheRift.api.riftessence.IRiftEssence;

/**
 * Created by bfox1 on 12/2/2016.
 */
public class RiftEssenceHelper
{

    /**
     * Helper method for adding RiftEssence to the OutGoing Essence Container
     * This method has the ability to determine is the incoming RE is less that the maxRE being removed.
     * @param incoming The Rift Essence Container's Current RE.
     * @param outgoing The Rift Essence Container the RE is going out to.
     * @param adding The Maximum Amount of RE that is getting delivered.
     */
    public static void addRiftEssence(IRiftEssence incoming, IRiftEssence outgoing, int adding)
    {
        adding = incoming.getRiftEssence() < adding ? incoming.getRiftEssence() : adding;
        if(incoming.getRiftEssence() !=0)
        {

            int difference = getDifference(outgoing, adding);
            if(difference == 0)
            {
                int n = incoming.removeRiftEssence(adding);
                outgoing.addRiftEssence(n);
            }
            else
            {
                incoming.removeRiftEssence(adding - difference);
                outgoing.addRiftEssence(adding);
            }
        }
    }

    public static void removeRiftEssence(IRiftEssence incoming, IRiftEssence outgoing, int removing)
    {
        removing = incoming.getRiftEssence() < removing ? incoming.getRiftEssence() : removing;
        if(incoming.getRiftEssence() != 0)
        {
            int difference = getDifference(outgoing, removing);
            if(difference == 0)
            {
                int n = incoming.removeRiftEssence(removing);
                outgoing.addRiftEssence(n);
            }
            else
            {
                incoming.removeRiftEssence(removing - difference);
                outgoing.addRiftEssence(removing);
            }
        }
    }

    private static int getDifference(IRiftEssence outgoing, int adding)
    {
        int currentRE = outgoing.getRiftEssence();
        int maxRE = outgoing.getMaxRiftEssence();

        int addedRE = currentRE + adding;

        if(addedRE > maxRE)
        {
            return addedRE - maxRE;
        }
        else if(addedRE <= maxRE)
        {
            return 0;
        }
        else
        {
            return 0;
        }

    }
}
