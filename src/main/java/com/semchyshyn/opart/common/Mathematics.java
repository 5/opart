package com.semchyshyn.opart.common;

import java.awt.geom.Point2D;
import java.util.Arrays;
import org.jspecify.annotations.NonNull;

@SuppressWarnings("unused")
public abstract class Mathematics {
	public static final double ZERO = 0d;
	public static final double EPSILON = 1e-9d;
	public static final double UNIT = 1d;
	public static final @NonNull Point2D ORIGIN = new Point2D.Double(ZERO, ZERO);

	public static double minimum(final double... values) {
		return Arrays.stream(values)
		             .min()
		             .orElse(ZERO);
	}

	public static double maximum(final double... values) {
		return Arrays.stream(values)
		             .max()
		             .orElse(ZERO);
	}

	public static double modulo(final double dividend,
	                            final double divisor) {
		double remainder = dividend % divisor;

		if (Math.signum(dividend) != Math.signum(divisor) &&
			remainder != 0d) {
			remainder += divisor;
		}

		return remainder;
	}

	public static double fraction(final double value) {
		return modulo(value, 1d);
	}

	public static double square(final double number) {
		return number * number;
	}

	public static double diagonal(final double first,
	                              final double second) {
		final double square = square(first) + square(second);

		return Math.sqrt(square);
	}

	public static double arctangent(final @NonNull Point2D point) {
		return Math.atan2(point.getY(), point.getX());
	}
}
