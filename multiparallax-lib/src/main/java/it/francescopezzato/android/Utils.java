package it.francescopezzato.android;

/**
 * Created by francesco on 03/04/2015.
 */
public class Utils {

	public static float clamp(float val, float min, float max) {
		return Math.max(min, Math.min(max, val));
	}
}
