package io.github.bfox1.TheRift.common.items;

import io.github.bfox1.TheRift.api.containmentvalve.IContainmentValve;
import io.github.bfox1.TheRift.riftessence.containmentvalves.Valve;

/**
 * Created by bfox1 on 11/7/2016.
 */
public class ContainmentValve extends RiftItem
{
    private final IContainmentValve valve;


    private ContainmentValve(IContainmentValve valve)
    {
        super();
        this.valve = valve;
        this.setMaxStackSize(1);
    }

    public final IContainmentValve getContainmentValve() {
        return valve;
    }

    public static ContainmentValve getTieredValve(int id)
    {
        switch (id)
        {
            case 0: return new ContainmentValve(Valve.tierOne);
            case 1 : return new ContainmentValve(Valve.tierTwo);
            case 2 : return new ContainmentValve(Valve.tierThree);
            default: return null;
        }
    }
}
