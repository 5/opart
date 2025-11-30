package com.semchyshyn.opart.common;

@SuppressWarnings("unused")
public abstract class Mathematics {
	public static float floorMod(final float dividend,
	                             final float divisor) {
		float remainder = dividend % divisor;
		if (Math.signum(dividend) != Math.signum(divisor) &&
			remainder != 0) {
			remainder += divisor;
		}
		return remainder;
	}

	public static double floorMod(final double dividend,
	                              final double divisor) {
		double remainder = dividend % divisor;
		if (Math.signum(dividend) != Math.signum(divisor) &&
			remainder != 0) {
			remainder += divisor;
		}
		return remainder;
	}
}
