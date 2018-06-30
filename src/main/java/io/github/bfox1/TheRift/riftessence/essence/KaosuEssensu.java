package io.github.bfox1.TheRift.riftessence.essence;

/**
 * Created by bfox1 on 11/7/2016.
 */
public class KaosuEssensu extends AbstractRiftEssence
{
    private int ke;

    public KaosuEssensu() {
        super("Kaosu Essensu", 3.6);
    }


    @Override
    public int getRiftEssence()
    {
        double re = (ke*3.6);
        return this.addContainmentMultiplier(re);

    }

    @Override
    public void setEssence(int i)
    {
        this.ke = i;
    }

    @Override
    public double getComparableEssence() {
        return this.ke;
    }

    @Override
    public void addEssence(int i)
    {
        this.ke += i;
    }

    @Override
    public int getComparableMax()
    {
        //return (int) (this.getContainmentValve() != null ? (((getMaxRiftEssence()/getContainmentValve().getMultiplier())/3.6)) : getMaxRiftEssence()/3.6);
        return (int) (getMaxRiftEssence()/3.6);
    }

}
