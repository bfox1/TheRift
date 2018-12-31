package io.github.bfox1.TheRift.common.items;

import io.github.bfox1.TheRift.api.riftessence.IRiftEssence;
import io.github.bfox1.TheRift.common.items.essencecontainer.ItemRiftEssenceContainer;
import io.github.bfox1.TheRift.riftessence.essence.AbstractRiftEssence;
import io.github.bfox1.TheRift.riftessence.essence.JoryuEssence;

/**
 * Created by bfox1 on 12/2/2016.
 */
public class ItemRiftFluxCapacitor extends ItemRiftEssenceContainer
{
    private AbstractRiftEssence essence;

    public ItemRiftFluxCapacitor()
    {
        this.essence = new JoryuEssence();
        this.setMaxDamage(7500);
        this.essence.setMaxRe(7500);

    }
    @Override
    public AbstractRiftEssence getEssence()
    {
        return this.essence;
    }

    @Override
    public int getComparableEssence() {
        return (int) this.essence.getComparableEssence();
    }

    @Override
    public int getComparableMaxEssence() {
        return this.getEssence().getComparableMax();
    }



    @Override
    public void setComparableEssence(AbstractRiftEssence essence) {

    }

    @Override
    public boolean isEssenceAllowed(IRiftEssence essence) {
        return essence instanceof JoryuEssence;
    }

    @Override
    public void setMaxRiftEssence(int maxRiftEssence)
    {
        this.essence.setMaxRe(maxRiftEssence);
    }

}
