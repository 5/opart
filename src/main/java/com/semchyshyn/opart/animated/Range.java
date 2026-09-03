package com.semchyshyn.opart.animated;

import com.semchyshyn.opart.common.Mathematics;
import com.semchyshyn.opart.common.Random;
import org.jspecify.annotations.NonNull;

@SuppressWarnings("unused")
public class Range implements Interval<Double> {
	public static final @NonNull Range ZERO_POINT    = new Range( Mathematics.ZERO, Mathematics.ZERO);
	public static final @NonNull Range UNIT_INTERVAL = new Range( Mathematics.ZERO, Mathematics.UNIT);
	public static final @NonNull Range UNIT_CIRCLE   = new Range(-Mathematics.UNIT, Mathematics.UNIT);
	public static final @NonNull Range HALF_CIRCLE   = new Range( Mathematics.ZERO, Math.PI);
	public static final @NonNull Range FULL_CIRCLE   = new Range( Mathematics.ZERO, Math.TAU);

	private final double from;
	private final double to;

	public Range(final double from,
	             final double to) {
		this.from = from;
		this.to = to;
	}

	@Override
	public @NonNull Double from() {
		return from;
	}

	@Override
	public @NonNull Double at(final double time) {
		return from + (to - from) * time;
	}

	@Override
	public @NonNull Double to() {
		return to;
	}

	public @NonNull Double length() {
		return to - from;
	}

	public @NonNull Double random() {
		return Random.next(from, to);
	}

	public @NonNull Range shift(final double shift) {
		return new Range(from + shift, to + shift);
	}

	public @NonNull Range reverse() {
		return new Range(to, from);
	}

	public @NonNull Range before(final double time) {
		return new Range(from, at(time));
	}

	public @NonNull Range after(final double time) {
		return new Range(at(time), to);
	}
}
