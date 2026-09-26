package com.semchyshyn.opart.common;

import com.semchyshyn.opart.animated.Range;
import java.awt.geom.Point2D;
import org.jspecify.annotations.NonNull;

@SuppressWarnings({"LongLiteralEndingWithLowercaseL", "unused"})
public abstract class Random {
	protected static final java.util.@NonNull Random RANDOM = new java.util.Random(0l);

	public static void skip(final int bytes) {
		if (0 < bytes) {
			RANDOM.nextBytes(new byte[bytes]);
		}
	}

	public static byte next() {
		byte[] bytes = new byte[1];
		RANDOM.nextBytes(bytes);
		return bytes[0];
	}

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

	public static boolean bit() {
		return RANDOM.nextBoolean();
	}

	public static @NonNull Point2D point() {
		return point(Range.UNIT_CIRCLE,
		             Range.UNIT_CIRCLE);
	}

	public static @NonNull Point2D point(final @NonNull Range horizontal,
	                                     final @NonNull Range vertical) {
		return new Point2D.Double(horizontal.random(),
		                          vertical.random());
	}
}
