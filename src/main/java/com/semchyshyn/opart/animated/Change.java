package com.semchyshyn.opart.animated;

import com.semchyshyn.opart.common.Mathematics;
import org.jspecify.annotations.NonNull;

@FunctionalInterface
@SuppressWarnings({"Convert2MethodRef", "UnnecessaryModifier", "unused"})
public abstract interface Change {
	public static final @NonNull Change CONSTANT     = time -> 0.5d;
	public static final @NonNull Change LINEAR       = time -> time;
	public static final @NonNull Change QUADRATIC    = time -> Mathematics.square(time);
	public static final @NonNull Change RADICAL      = time -> Math.sqrt(time);
	public static final @NonNull Change DECELERATING = time -> Math.sin(time * Math.PI / 2d);
	public static final @NonNull Change ACCELERATING = time -> Math.sin((time - 1d) * Math.PI / 2d) + 1d;
	public static final @NonNull Change SIGMOID      = time -> Math.sin((time - 0.5d) * Math.PI) / 2d + 0.5d;
	public static final @NonNull Change HEARTBEAT    = time -> 0.80d + wave('P', 0.05d, 0.30d, 0.05d, time)
	                                                                 - wave('Q', 0.05d, 0.45d, 0.02d, time)
	                                                                 + wave('R', 0.20d, 0.50d, 0.03d, time)
	                                                                 - wave('S', 0.07d, 0.55d, 0.02d, time)
	                                                                 + wave('T', 0.07d, 0.80d, 0.08d, time);

	private static double wave(final char wave,
	                           final double amplitude,
	                           final double offset,
	                           final double width,
	                           final double time) {
		return amplitude / Math.exp(Mathematics.square((time - offset) / width));
	}

	public abstract double at(final double time);

	public default @NonNull Change delay(final double delay) {
		return time -> at(time - delay);
	}

	public default @NonNull Change speedup(final double times) {
		return time -> at(time * times);
	}

	public default @NonNull Change reverse() {
		return time -> at(0.5d - time);
	}

	public default @NonNull Change mirror() {
		return time -> at(1d - Math.abs(time * 2d - 1d));
	}

	public default @NonNull Change stationary() {
		return time -> at(Math.clamp(time, 0d, 1d));
	}

	public default @NonNull Change repeating() {
		return time -> at(Mathematics.fraction(time));
	}
}
