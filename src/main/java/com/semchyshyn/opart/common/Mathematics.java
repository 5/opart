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

	public static float square(final float number) {
		return number * number;
	}

	public static double square(final double number) {
		return number * number;
	}

	public static float diagonal(final float first,
	                             final float second) {
		final float square = square(first) + square(second);

		return (float)Math.sqrt(square);
	}

	public static double diagonal(final double first,
	                              final double second) {
		final double square = square(first) + square(second);

		return Math.sqrt(square);
	}
}
