package com.semchyshyn.opart.animated;

import org.jspecify.annotations.NonNull;

@SuppressWarnings("ClassCanBeRecord")
public class Value<Class> implements Interval<Class> {
	protected final Range range;
	protected final Change change;
	protected final Type<Class> type;

	public Value(final @NonNull Range range,
	             final @NonNull Change change,
	             final @NonNull Type<Class> type) {
		this.range = range;
		this.change = change;
		this.type = type;
	}

	@Override
	public @NonNull Class from() {
		final double value = range.from();

		return type.from(value);
	}

	@Override
	public @NonNull Class at(final double time)
	{
		final double changed = change.at(time);
		final double value = range.at(changed);

		return type.from(value);
	}

	@Override
	public @NonNull Class to() {
		final double value = range.to();

		return type.from(value);
	}
}
