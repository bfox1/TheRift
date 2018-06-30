package io.github.bfox1.TheRift.riftessence.containmentvalves;

import io.github.bfox1.TheRift.api.containmentvalve.IContainmentValve;

/**
 * Created by bfox1 on 11/7/2016.
 */
public abstract class Valve implements IContainmentValve
{
    private final double mulitiplier;
    private final String valveName;

    public static Valve tierOne = new Valve("barubu_one",2.5) {};
    public static Valve tierTwo = new Valve("barubu_two",4) {};
    public static Valve tierThree = new Valve("barubu_three",6) {};
    public static Valve tierFour = new Valve("barubu_four",12) {};

    public Valve(String name, double mp)
    {
        this.mulitiplier = mp;
        this.valveName = name;
    }


    @Override
    public double getMultiplier()
    {
        return mulitiplier;
    }


    public String getValveName() {
        return valveName;
    }
}
