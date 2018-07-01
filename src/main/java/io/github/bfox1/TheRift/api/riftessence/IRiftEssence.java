package io.github.bfox1.TheRift.api.riftessence;

import io.github.bfox1.TheRift.api.containmentvalve.IContainmentValve;

/**
 * Created by bfox1 on 11/7/2016.
 *
 * TODO: Clarification on improved Documentation
 */
public interface IRiftEssence
{
    /**
     * returns the RiftEssence equivalent. sub-classes should convert there selective Essence source to Re
     * with this method
     * @return
     */
    int getRiftEssence();

    /**
     * Set the Current Essence amount.
     * @param i
     */
    void setEssence(int i);

    /**
     * get the Current Essence amount.
     * @return
     */
    double getComparableEssence();

    /**
     * Adds to the Raw Essence while adding to the Comparable Essence. This should ONLY be used for generation of
     * Rift Essence. Comparable Essence to Comparable does not exist, only the transfer of Rift Essence, for transferring
     * of Rift Essence, Look at IRiftEssence#addRawEssence
     */
    void addEssence(int i, double essenceMuliplier);

    int removeRiftEssence(int i);

    void setContainmentValve(IContainmentValve valve);

    IContainmentValve getContainmentValve();

    void clearValve();

    int getComparableMax();

    String getName();

    /**
     * Translate the ComparableEssence into RiftEssence, this does not include the ContainmentValve multiplier if any.
     * @param comparableEssence
     * @param comparableEssenceMultiplier
     * @return
     */
    int translateToRiftEssence(int comparableEssence, double comparableEssenceMultiplier);

    int translateToComparableEssence(double comparableEssenceMultiplier);

    IRiftEssence copy();

    /**
     * Should be used when two IRiftEssenceContainers interact with eachother. RE transfers between blocks NOT the
     * Comparable Essence.
     * @param i
     */
    int addRawEssence(int i);

    int getMaxRiftEssence();


}
