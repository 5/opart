package com.semchyshyn.opart.pieces;

import com.semchyshyn.opart.animated.Graphics;
import com.semchyshyn.opart.animated.Resolution;
import org.jspecify.annotations.NonNull;

/// # Rain
///
/// Animated Optical Art
///
///   * [X](...)
public class RainSquare extends Rain {
	@Override
	protected @NonNull Resolution resolution() {
		return Resolution.SQUARE;
	}

	protected double intensity() {
		return 1.5d;
	}

	@Override
	protected void render(final @NonNull Graphics graphics,
	                      final double time) {
		super.render(graphics, time + temporal().length());
		super.render(graphics, time);
	}

	@SuppressWarnings({"UnnecessaryModifier", "unused"})
	public static void main(final @NonNull String... arguments) {
		new RainSquare().create();
	}
}
