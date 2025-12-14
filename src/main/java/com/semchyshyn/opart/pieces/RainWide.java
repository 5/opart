package com.semchyshyn.opart.pieces;

import com.semchyshyn.opart.animated.Resolution;
import org.jspecify.annotations.NonNull;

/// # Rain
///
/// Animated Optical Art
/// Screen Saver for Meditation, Relaxation, and Happiness
/// 21:9 UW5K
///
///   * [YouTube](https://www.youtube.com/watch?v=...)
public class RainWide extends RainHorizontal {
	@Override
	protected @NonNull Resolution resolution() {
		return Resolution.WIDE;
	}

	@SuppressWarnings({"UnnecessaryModifier", "unused"})
	public static void main(final @NonNull String... arguments) {
		new RainWide().create();
	}
}
