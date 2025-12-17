package com.semchyshyn.opart.animated;

import org.jspecify.annotations.NonNull;

@SuppressWarnings("UnnecessaryModifier")
public abstract interface Interval<Class> {
	public abstract @NonNull Class from();

	public abstract @NonNull Class at(final double time);

	public abstract @NonNull Class to();
}
