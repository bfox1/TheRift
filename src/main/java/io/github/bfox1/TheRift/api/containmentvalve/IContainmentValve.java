package io.github.bfox1.TheRift.api.containmentvalve;

/**
 * Created by bfox1 on 11/7/2016.
 * Any and All containment valves should possess this interface to ensure these two methods are available.
 */
public interface IContainmentValve
{
    /**
     * Gets the multiplier in which Items get multiplied.
     * @return
     */
    double getMultiplier();

    /**
     * Gets the Valve name.
     * @return
     */
    String getValveName();
}
