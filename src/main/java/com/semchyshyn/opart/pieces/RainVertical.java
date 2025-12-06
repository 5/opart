package com.semchyshyn.opart.pieces;

import com.semchyshyn.opart.animated.Resolution;
import org.jspecify.annotations.NonNull;

/// # Rain
///
/// Animated Optical Art
///
///   * [YouTube Short](https://www.youtube.com/shorts/wpr-U0bmx6c)
///   * [TikTok](...)
public class RainVertical extends RainSquare {
	@Override
	protected @NonNull Resolution resolution() {
		return Resolution.VERTICAL;
	}

	@SuppressWarnings({"UnnecessaryModifier", "unused"})
	public static void main(final @NonNull String... arguments) {
		new RainVertical().create();
	}
}
