package com.semchyshyn.opart.animated;

import org.jspecify.annotations.NonNull;

@FunctionalInterface
@SuppressWarnings({"UnnecessaryModifier", "unused"})
public abstract interface Change {
	public static final @NonNull Change CONSTANT     = time -> 0.5d;
	public static final @NonNull Change LINEAR       = time -> time;
	public static final @NonNull Change DECELERATING = time -> Math.sin(time * Math.PI / 2d);               // TODO: Split into real decelerating and sinusoidal
	public static final @NonNull Change ACCELERATING = time -> Math.sin((time - 1d) * Math.PI / 2d) + 1d;                // TODO: Replace with real accelerating
	public static final @NonNull Change SIGMOID      = time -> Math.sin((time - 0.5d) * Math.PI) / 2d + 0.5d;                 // TODO: Replace with real sigmoid
//	public static final @NonNull Change HEARTBEAT    = time -> Math.sin((time - 0.5d) * Math.PI) / 2d + 0.5d;

	public abstract double at(final double time);
}
