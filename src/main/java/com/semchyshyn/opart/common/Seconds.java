package com.semchyshyn.opart.common;

import java.time.Duration;
import org.jspecify.annotations.NonNull;

public abstract class Seconds {
	protected static final double NANOSECONDS = 1e9d;

	public static @NonNull Duration toDuration(final double seconds) {
		return Duration.ofNanos((long)(seconds * NANOSECONDS));
	}

	public static double fromDuration(final @NonNull Duration duration) {
		return (double)duration.toNanos() / NANOSECONDS;
	}
}
