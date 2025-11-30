package com.semchyshyn.opart.pieces;

import com.semchyshyn.opart.animated.Graphics;
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
public class RainLoop extends Rain {
	@Override
	protected void render(final @NonNull Graphics graphics,
	                      final double time) {
		super.render(graphics, time + 1d);
		super.render(graphics, time);
	}

	@SuppressWarnings({"UnnecessaryModifier", "unused"})
	public static void main(final @NonNull String... arguments) {
		new RainLoop().create();
	}
}
