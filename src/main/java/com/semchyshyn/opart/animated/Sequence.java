package com.semchyshyn.opart.animated;

import org.jspecify.annotations.NonNull;

public class Sequence implements Interval<Double> {
	private final double @NonNull[] values;

	public Sequence(final double @NonNull... values) {
		this.values = values;
	}

	@Override
	public @NonNull Double from() {
		return values[0];
	}

	@Override
	public @NonNull Double at(double time) {

		// TODO

		return 0.0;
	}

	@Override
	public @NonNull Double to() {
		final int length = values.length;

		return values[length - 1];
	}
}
