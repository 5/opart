package com.semchyshyn.opart.animated;

import org.jspecify.annotations.NonNull;

@FunctionalInterface
@SuppressWarnings("UnnecessaryModifier")
public abstract interface Type<Class> {
	public static final @NonNull Type<Double> DOUBLE = value -> value;

	public abstract @NonNull Class from(final double value);
}
