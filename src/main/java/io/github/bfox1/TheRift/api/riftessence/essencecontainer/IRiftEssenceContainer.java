package io.github.bfox1.TheRift.api.riftessence.essencecontainer;

import io.github.bfox1.TheRift.api.riftessence.IRiftEssence;
import io.github.bfox1.TheRift.riftessence.essence.AbstractRiftEssence;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by bfox1 on 11/7/2016.
 */
public interface IRiftEssenceContainer
{
    AbstractRiftEssence getEssence();

    int getComparableEssence();

    int getComparableMaxEssence();

    /**
     * IRiftEssenceContainer#addRiftEssence is intended for other Comparable Essences to add to the current Essence of
     * this Container. For information about incrementing current Essence, check AbstractRiftEssence#addEssence(int i)
     * @param increment
     */
    void addRiftEssence(IRiftEssence essence, int increment);

    void decreRiftEssence(IRiftEssence essence, int decrement);

    void setComparableEssence(AbstractRiftEssence essence);

    boolean isEssenceAllowed(IRiftEssence essence);

    void setMaxRiftEssence(int maxRiftEssence);


}
