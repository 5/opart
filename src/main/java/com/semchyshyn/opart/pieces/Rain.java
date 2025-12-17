package com.semchyshyn.opart.pieces;

import com.semchyshyn.opart.animated.Change;
import com.semchyshyn.opart.animated.Graphics;
import com.semchyshyn.opart.animated.OpArt;
import com.semchyshyn.opart.animated.Range;
import com.semchyshyn.opart.animated.Type;
import com.semchyshyn.opart.animated.Value;
import com.semchyshyn.opart.common.Mathematics;
import com.semchyshyn.opart.common.Seconds;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import org.jspecify.annotations.NonNull;

///
/// # Rain
///
/// Drops of rain fall randomly,
/// causing ripples to interfere endlessly,
/// conjuring a beautiful pattern
///
public abstract class Rain extends OpArt {
	protected class Drop {
		public final double abscissa = horizontal().random();
		public final double ordinate = vertical().random();
		public final @NonNull Value<Double> radius = new Value<>(new Range(0d, Mathematics.diagonal(horizontal().length(),
		                                                                                            vertical().length())),
		                                                         Change.LINEAR.speedup(times())
		                                                                      .delay(active().random()),
		                                                         Type.DOUBLE);
	}

	protected final @NonNull List<Drop> drops = Stream.generate(Drop::new)
	                                                  .limit(drops())
	                                                  .sorted(Comparator.<Drop, Double>comparing(drop -> drop.radius.at(temporal().to()))
	                                                                    .reversed())
	                                                  .toList();

	@Override
	protected @NonNull String title() {
		return "Rain";
	}

	@Override
	protected @NonNull String description() {
		return "Drops of rain fall randomly, " +
		       "causing ripples to interfere endlessly, " +
		       "conjuring a beautiful pattern";
	}

	protected double speed() {
		return 5d;
	}

	protected double intensity() {
		return 1d;
	}

	protected @NonNull Range active() {
		return temporal();
	}

	protected double times() {
		final double speed = speed();
		final double seconds = Seconds.fromDuration(duration());
		final double times = seconds / speed;
		final double temporal = temporal().length();

		return times / temporal;
	}

	protected int drops() {
		final double intensity = intensity();
		final double active = active().length();
		final double temporal = temporal().length();
		final double fraction = active / temporal;
		final double seconds = Seconds.fromDuration(duration()) * fraction;
		final int drops = (int)(seconds * intensity);

		return drops + (drops & 1);
	}

	@Override
	protected void render(final @NonNull Graphics graphics,
	                      final double time) {
		drops.forEach(drop -> graphics.circle(drop.abscissa,
		                                      drop.ordinate,
		                                      drop.radius.at(time)));
	}
}
