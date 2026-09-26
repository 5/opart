package com.semchyshyn.opart.common;

import java.awt.geom.Point2D;
import java.util.Arrays;
import org.jspecify.annotations.NonNull;

@SuppressWarnings({"LongLiteralEndingWithLowercaseL", "unused"})
public abstract class Mathematics {
	public static final double ZERO = 0d;
	public static final double EPSILON = 1e-9d;
	public static final double UNIT = 1d;
	public static final @NonNull Point2D ORIGIN = new Point2D.Double(ZERO, ZERO);

	public static boolean even(final int value) {
		return (value & 1) == 0;
	}

	public static boolean even(final long value) {
		return (value & 1l) == 0l;
	}

	public static boolean odd(final int value) {
		return (value & 1) != 0;
	}

	public static boolean odd(final long value) {
		return (value & 1l) != 0l;
	}

	public static int minimum(final int @NonNull... values) {
		return Arrays.stream(values)
		             .min()
		             .orElse(0);
	}

	public static long minimum(final long @NonNull... values) {
		return Arrays.stream(values)
		             .min()
		             .orElse(0l);
	}

	public static double minimum(final double @NonNull... values) {
		return Arrays.stream(values)
		             .min()
		             .orElse(ZERO);
	}

	public static int maximum(final int @NonNull... values) {
		return Arrays.stream(values)
		             .max()
		             .orElse(0);
	}

	public static long maximum(final long @NonNull... values) {
		return Arrays.stream(values)
		             .max()
		             .orElse(0l);
	}

	public static double maximum(final double @NonNull... values) {
		return Arrays.stream(values)
		             .max()
		             .orElse(ZERO);
	}

	public static double modulo(final double dividend,
	                            final double divisor) {
		double remainder = dividend % divisor;

		if (Math.signum(dividend) != Math.signum(divisor) &&
			remainder != ZERO) {
			remainder += divisor;
		}

		return remainder;
	}

	public static double fraction(final double value) {
		return modulo(value, UNIT);
	}

	public static long square(final long number) {
		return number * number;
	}

	public static double square(final double number) {
		return number * number;
	}

	public static double diagonal(final long first,
	                              final long second) {
		final long square = square(first) + square(second);

		return Math.sqrt(square);
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
