package com.semchyshyn.opart.common;

import org.jspecify.annotations.NonNull;

@SuppressWarnings({"LongLiteralEndingWithLowercaseL", "unused"})
public abstract class Random {
	protected static final java.util.@NonNull Random RANDOM = new java.util.Random(0l);

	public static int next(int from, int to) {
		if (from > to) {
			final int temporary = from;
			from = to;
			to = temporary;
		}

		return RANDOM.nextInt(from, to + 1);
	}

	public static long next(long from, long to) {
		if (from > to) {
			final long temporary = from;
			from = to;
			to = temporary;
		}

		return RANDOM.nextLong(from, to + 1l);
	}

	public static float next(float from, float to) {
		if (from > to) {
			final float temporary = from;
			from = to;
			to = temporary;
		}

		return RANDOM.nextFloat(from, Math.nextUp(to));
	}

	public static double next(double from, double to) {
		if (from > to) {
			final double temporary = from;
			from = to;
			to = temporary;
		}

		return RANDOM.nextDouble(from, Math.nextUp(to));
	}
}
