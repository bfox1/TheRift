package io.github.bfox1.TheRift.common.util;

/**
 * Created by bfox1 on 11/9/2016.
 */
public class MathHelper
{


    public static double newRangeValue(double oldMax, double oldMin, double newMax, double newMin, double value)
    {
        double oldRange = oldMax - oldMin;
        double newRange = newMax - newMin;

        return (((value - oldMin)*newRange)/oldRange)+newMin;
    }

    public static boolean isIntWithinRange(double pos1, double pos2, double pos3, double pos4, double x, double y)
    {
        double minX = Math.min(pos1, pos3);
        double maxX = Math.max(pos1, pos3);

        double minY = Math.min(pos2, pos4);
        double manY = Math.max(pos2, pos4);



        return x > minX && x < maxX && y > minY && y < manY;
    }
}
