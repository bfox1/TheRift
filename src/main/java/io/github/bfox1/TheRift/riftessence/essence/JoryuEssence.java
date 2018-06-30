package io.github.bfox1.TheRift.riftessence.essence;

/**
 * Created by bfox1 on 11/7/2016.
 */
public class JoryuEssence extends AbstractRiftEssence
{

    public JoryuEssence() {
        super("Joryu Essensu", 1.7);

    }


    @Override
    public void setEssence(int i)
    {
        super.setEssence(i);
    }

    @Override
    public double getComparableEssence()
    {
        return this.translateToComparableEssence(getEssenceMultiplier());
    }

    @Override
    public void addEssence(int i)
    {
        super.addEssence(i, getEssenceMultiplier());
    }

    @Override
    public int getComparableMax()
    {

        return (int) (getMaxRiftEssence()/getEssenceMultiplier());


    }


}
