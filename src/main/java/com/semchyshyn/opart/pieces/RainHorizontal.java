package com.semchyshyn.opart.pieces;

import com.semchyshyn.opart.animated.Graphics;
import com.semchyshyn.opart.animated.Range;
import com.semchyshyn.opart.animated.Resolution;
import com.semchyshyn.opart.common.Mathematics;
import com.semchyshyn.opart.common.Seconds;
import java.nio.file.Path;
import org.jspecify.annotations.NonNull;

///
///  # Rain
///
/// Animated Optical Art:
/// Screen Saver for Meditation, Relaxation, and Happiness
/// (16:9 4K UHD / 21:9 UW5K)
///
///   * [YouTube](https://www.youtube.com/watch?v=kUj260CkgbI)
///   * [YouTube](https://www.youtube.com/watch?v=azSGua01q5U)
///
public class RainHorizontal extends Rain {
	@Override
	protected @NonNull Resolution resolution() {
		return Resolution.HORIZONTAL;
	}

	@Override
	protected @NonNull Path audio() {
		return Path.of("D:",
		               "Music",
		               "Kevin MacLeod",
		               "Chill",
		               "Wind of the Rainforest.wav");
	}

	protected double speed() {
		return 20d;
	}

	@Override
	protected @NonNull Range active() {
		final double speed = speed();
		final double seconds = Seconds.fromDuration(duration());
		final double active = (seconds - speed) / seconds;
		final Range temporal = temporal();

		return temporal.before(active);
	}

	@Override
	protected void render(final @NonNull Graphics graphics,
	                      final double time) {
		final double diagonal = Mathematics.diagonal(horizontal().length(),
		                                             vertical().length());
		final int skip = (int)drops.stream()
		                           .filter(drop -> diagonal < drop.radius.at(time))
		                           .count() & -2;
		drops.stream()
		     .skip(skip)
		     .forEach(drop -> graphics.circle(drop.abscissa,
		                                      drop.ordinate,
		                                      drop.radius.at(time)));
	}

	@SuppressWarnings({"UnnecessaryModifier", "unused"})
	public static void main(final @NonNull String... arguments) {
		new RainHorizontal().create();
	}
}
