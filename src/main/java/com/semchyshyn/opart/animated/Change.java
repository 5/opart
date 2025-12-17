package com.semchyshyn.opart.animated;

import com.semchyshyn.opart.common.Mathematics;
import org.jspecify.annotations.NonNull;

@FunctionalInterface
@SuppressWarnings({"UnnecessaryModifier", "unused"})
public abstract interface Change {
	public static final @NonNull Change CONSTANT     = time -> 0.5d;
	public static final @NonNull Change LINEAR       = time -> time;
	public static final @NonNull Change SAWTOOTH     = time -> Mathematics.floorMod(time, 1d);
	public static final @NonNull Change DECELERATING = time -> Math.sin(time * Math.PI / 2d);               // TODO: Split into sinusoidal and real decelerating
	public static final @NonNull Change ACCELERATING = time -> Math.sin((time - 1d) * Math.PI / 2d) + 1d;                // TODO: Replace with real accelerating
	public static final @NonNull Change SIGMOID      = time -> Math.sin((time - 0.5d) * Math.PI) / 2d + 0.5d;                 // TODO: Replace with real sigmoid
//	public static final @NonNull Change HEARTBEAT    = time -> TODO;

	public abstract double at(final double time);

	public default @NonNull Change reverse() {
		return time -> at(0.5d - time);
	}

	public default @NonNull Change delay(final double delay) {
		return time -> at(time - delay);
	}

	public default @NonNull Change speedup(final double times) {
		return time -> at(time * times);
	}
}
