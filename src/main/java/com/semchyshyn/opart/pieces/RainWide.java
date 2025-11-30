package com.semchyshyn.opart.pieces;

import com.semchyshyn.opart.animated.Resolution;
import org.jspecify.annotations.NonNull;

/// # Rain
///
/// Drops of rain fall randomly,
/// causing ripples to interfere endlessly,
/// conjuring a beautiful pattern
///
///   * [YouTube Short](https://www.youtube.com/shorts/123)
///   * [Instagram Reel](...)
///   * [TikTok](...)
///   * [X](...)
public class RainWide extends Rain {
	@Override
	protected @NonNull Resolution resolution() {
		return Resolution.WIDE;
	}

//	@Override
//	protected @NonNull Duration duration() {
//		return super.duration();
//	}
//
//	@Override
//	protected @Nullable Path audio() {
//		return super.audio();
//	}

	@SuppressWarnings({"UnnecessaryModifier", "unused"})
	public static void main(final @NonNull String... arguments) {
		new RainWide().create();
	}
}
