package io.github.bfox1.TheRift.riftessence.essence;

import io.github.bfox1.TheRift.api.riftessence.IRiftEssence;
import io.github.bfox1.TheRift.api.containmentvalve.IContainmentValve;

/**
 * Created by bfox1 on 11/7/2016.
 */
public abstract class AbstractRiftEssence implements IRiftEssence
{

    private IContainmentValve valve;
    private int maxRe;
    private int re;
    private boolean reachedMax;
    private boolean reachedMin;
    private final String essenceName;
    private final double essenceMultiplier;
    public AbstractRiftEssence(String name, double multipler)
    {

        this.essenceName = name;
        this.essenceMultiplier = multipler;
    }



    @Override
    public void clearValve()
    {
        try
        {
            this.maxRe = (int) (valve.getValveName().equalsIgnoreCase("barubu_one") ? maxRe / valve.getMultiplier() : (valve.getValveName().equalsIgnoreCase("barubu_two") ? maxRe / valve.getMultiplier() :
                    (valve.getValveName().equalsIgnoreCase("barubu_three") ? maxRe / valve.getMultiplier() : maxRe)));
            if (this.re > maxRe) {
                this.re = maxRe;
            }

            this.valve = null;
        }catch(Exception e)
        {
            if (this.re > maxRe)
            {
                this.re = maxRe;
            }
        }
    }

    @Override
    public final void setContainmentValve(IContainmentValve valve)
    {
        this.valve = valve;
        this.reachedMax = false;
        this.maxRe = (int) (valve.getValveName().equalsIgnoreCase("barubu_one") ? maxRe*valve.getMultiplier() : (valve.getValveName().equalsIgnoreCase("barubu_two") ? maxRe*valve.getMultiplier() :
                        (valve.getValveName().equalsIgnoreCase("barubu_three") ? maxRe*valve.getMultiplier() : maxRe)));
    }

    @Override
    public void setEssence(int riftEssence)
    {
        this.re = riftEssence;
    }

    @Override
    public IContainmentValve getContainmentValve()
    {
        return valve;
    }

    @Deprecated
    public final int addContainmentMultiplier(double re)
    {
        return valve != null ? ((int) (re*valve.getMultiplier())) : (int) re;
    }

    public abstract void addEssence(int i);


    @Override
    public final void addEssence(int i, double essenceMultiplier)
    {
        double value = translateToRiftEssence(i, essenceMultiplier)*(getContainmentValve() != null ? valve.getMultiplier() : 1);

        this.re += (int)value;
        this.reachedMax = this.re >= maxRe;
    }


    public boolean isReachedMax() {
        return reachedMax;
    }

    public void setReachedMax(boolean reachedMax) {
        this.reachedMax = reachedMax;
    }

    public void setMaxRe(int maxRe)
    {
        this.maxRe = maxRe;
    }

    public int getMaxRiftEssence()
    {
        return this.maxRe;
    }

    @Override
    public String getName() {
        return this.essenceName;
    }

    @Override
    public int getRiftEssence()
    {
        return this.re;
    }

    /**
     * Translate the ComparableEssence into RiftEssence, this does not include the ContainmentValve multiplier if any.
     * @param comparableEssence
     * @param comparableEssenceMultiplier
     * @return
     */
    @Override
    public int translateToRiftEssence(int comparableEssence, double comparableEssenceMultiplier)
    {
        return (int) (comparableEssence*comparableEssenceMultiplier);
    }

    /**
     * Translate the Rift Essence into Comparable Essence, this does not include the ContainmentValve multiplier if any.
     * @param comparableEssenceMultiplier
     * @return
     */
    @Override
    public int translateToComparableEssence(double comparableEssenceMultiplier)
    {
        return (int) (this.re / comparableEssenceMultiplier);
    }


    public int removeRiftEssence(int i)
    {
        if(this.re < i)
        {
            int x = this.re;
            this.re = 0;
            return x;
        }
        else
        {
            this.re -= i;
        }

        return i;
    }

    public double getEssenceMultiplier()
    {
        return essenceMultiplier;
    }

    @Override
    public AbstractRiftEssence copy()
    {
        AbstractRiftEssence essence = new AbstractRiftEssence(this.essenceName, this.essenceMultiplier)
        {

            @Override
            public void addEssence(int i) {

            }

            @Override
            public void setEssence(int i) {

            }

            @Override
            public double getComparableEssence() {
                return 0;
            }

            @Override
            public int getComparableMax() {
                return 0;
            }

            @Override
            public int addRawEssence(int i) {
                return 0;
            }
        };

        essence.re = this.getRiftEssence();
        essence.maxRe = (int) this.getMaxRiftEssence();
        essence.valve = this.getContainmentValve();
        return essence;
    }

    public int addRawEssence(int i)
    {
        if(this.re + i > maxRe)
        {
            int x = this.re + i - maxRe;
            this.re = maxRe;
            return x;
        }
        else
        {
            this.re += i;
            return i;
        }

    }


}
