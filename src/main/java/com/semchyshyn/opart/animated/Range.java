package com.semchyshyn.opart.animated;

import com.semchyshyn.opart.common.Random;
import org.jspecify.annotations.NonNull;

@SuppressWarnings("unused")
public class Range implements Interval<Double> {
	public static final @NonNull Range UNIT_INTERVAL = new Range( 0d, 1d);
	public static final @NonNull Range UNIT_CIRCLE   = new Range(-1d, 1d);
	public static final @NonNull Range FULL_CIRCLE   = new Range( 0d, Math.PI * 2d);

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
