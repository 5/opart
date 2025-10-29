package com.semchyshyn.opart.animated;

import com.semchyshyn.opart.common.Random;
import org.jspecify.annotations.NonNull;

public class Range implements Interval<Double> {
	public static final @NonNull Range UNIT = new Range(0d, 1d);

	private final double from;
	private final double to;

	public Range(final double from, final double to) {
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
}
