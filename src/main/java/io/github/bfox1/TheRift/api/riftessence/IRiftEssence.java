package io.github.bfox1.TheRift.api.riftessence;

import io.github.bfox1.TheRift.api.containmentvalve.IContainmentValve;
import io.github.bfox1.TheRift.riftessence.essence.AbstractRiftEssence;

/**
 * Created by bfox1 on 11/7/2016.
 *
 * TODO: Clarification on improved Documentation
 */
public interface IRiftEssence
{
    /**
     * This method will return the Rift Essence amount.
     * This shouldnt be used when displaying Comparable Essence.
     * For Comparable Essence, use {@link IRiftEssence#getRiftEssence()}
     * @return The Rift Essence integer.
     */
    int getRiftEssence();

    /**
     * This will set the Rift Essence. DO NOT USE THIS when adding RE as this method does NOT account for
     * Comparable Essence. please use {@link IRiftEssence#addEssence(int, double)} for simply adding RE, otherwise you
     * can use {@link IRiftEssence#addRiftEssence(int)}. Please refer to Docs.
     * @param i The amount of RE to add.
     */
    void setEssence(int i);

    /**
     * Comparable Essence or CE is what is described as the harnessed version of RE, Typically, the readings of RE differ
     * based off how the Comparable Essence is generated, Some Comparable Essence when broken down into RE produces more
     * versus other.
     *
     * This Method will get the Comparable Essence.
     * @return The Comparable Essence.
     */
    double getComparableEssence();

    /**
     * This Method is the Primary Method to be called when Essence is being Generated. CE and CE should not be added
     * unless intentional.
     *
     * For Transferring of RE from one Essence container to another, please use {@link IRiftEssence#addRiftEssence(int)}.
     * @param i The Amount of RE being added.
     * @param essenceMultiplier The Multiplier of the Comparable Essence. IE: Joryu Essence contains a Multiplier of 1.7
     */
    void addEssence(int i, double essenceMultiplier);

    /**
     * Will Simply remove the RE This does not account for CE multipliers.
     * @param i The amount to remove
     * @return the new amount/total or RE.
     */
    int removeRiftEssence(int i);

    /**
     *  When Containment valves are added to TileEntities, they get added to the {@link AbstractRiftEssence#valve} variable.
     * They will further multiply the amount of RE being generated, this all gets calculated within the
     * {@link IRiftEssence#addEssence(int, double)} Method.
     * @param valve The Valve being added.
     */
    void setContainmentValve(IContainmentValve valve);

    /**
     * Will return the Containment Valve. If no valve is found, will return null
     * @return Will return Valve.
     */
    IContainmentValve getContainmentValve();

    /**
     * A Method to remove the Valve located within the Essence.
     *
     * This should get called when an update occurs within the Object which contains the valve. Either the object is a
     * Block/Item and it gets destroyed, modified, or added.
     */
    void clearValve();

    /**
     * Will return to max amount of CE this essence can Hold.
     * @return The amount of CE max.
     */
    int getComparableMax();

    /**
     * Will get the name of the Comparable Essence.
     * @return The Stringed Name.
     */
    String getName();

    /**
     * This method gets invoked when {@link IRiftEssence#addEssence(int, double)} gets called.
     *
     * Will calculate and perform math functions to further multiply RE production.
     * @param riftEssence The initial amount of RE coming in from the source.
     * @param comparableEssenceMultiplier The Comparable Essence Multiplier.
     * @return return the amount of RE created.
     */
    int translateToRiftEssence(int riftEssence, double comparableEssenceMultiplier);

    /**
     * Will convert the Essence RE into the Comparable Essence. This is what normally will get called when displaying
     * information to the player via GUI's, or other forms.
     *
     * Depending on the Type of Essence being Harnessed, the amount of Comparable Essence can have a number greater
     * than/less than the amount of RE, so translating is the best way
     *
     * IE: 3400 RE will translate to 2000 CE and vise versa. This method gets called
     * within {@link IRiftEssence#getComparableEssence()}
     * @param comparableEssenceMultiplier The Multiplier of the CE
     * @return the CE Int.
     */
    int translateToComparableEssence(double comparableEssenceMultiplier);

    /**
     * A Special Method to temporarily create a Copy of the Current state of the Essence.
     *
     * This method should NOT be used to create permanent copies of CE. This method is to be used when a Essence Container
     * switches from one CE Type to Another, such as Joryu to Kaosu.
     * @return
     */
    IRiftEssence copy();

    /**
     * Method to add Simple RE to the Essence Container, this should be used when transferring Essence from one
     * Container to another.
     * @param i The amount of essence to transfer.
     * @return
     */
    int addRiftEssence(int i);

    /**
     * Will get the max amount of Essence.
     * @return
     */
    int getMaxRiftEssence();


}
