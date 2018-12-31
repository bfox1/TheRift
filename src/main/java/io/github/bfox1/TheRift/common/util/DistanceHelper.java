package io.github.bfox1.TheRift.common.util;

public class DistanceHelper {
	
	public static Integer distance2D(double xD, double xC, double Dx, double Cx) {
    	double xDelta = Math.abs(Dx - xD);
    	double zDelta = Math.abs(Cx - xC);
    	double distance = Math.sqrt(xDelta*xDelta + zDelta*zDelta);
    	return (int) distance;
    }
	
	public static Integer distance2D(double xD, double Cx) {
		double xDelta = Math.abs(xD);
		double zDelta = Math.abs(Cx);
		double distance = Math.sqrt(xDelta*xDelta + zDelta*zDelta);
    	return (int) distance;
	}
}