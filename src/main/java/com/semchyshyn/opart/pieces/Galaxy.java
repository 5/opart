package com.semchyshyn.opart.pieces;

import com.semchyshyn.opart.animated.Change;
import com.semchyshyn.opart.animated.Graphics;
import com.semchyshyn.opart.animated.OpArt;
import com.semchyshyn.opart.animated.Range;
import com.semchyshyn.opart.animated.Type;
import com.semchyshyn.opart.animated.Value;
import com.semchyshyn.opart.common.Mathematics;
import org.jspecify.annotations.NonNull;

///
/// # Galaxy
///
/// The black hole is pulling clouds of dust in,
/// gravity making them denser and denser,
/// forging an immaculate structure
///
public abstract class Galaxy extends OpArt {
	protected final double ratio = Math.PI / 4d / branches();
	protected final @NonNull Value<Double> phase = new Value<>(new Range(0d, step(ratio)),
	                                                           Change.SAWTOOTH
	                                                                 .reverse()
	                                                                 .speedup(speed()),
	                                                           Type.LINEAR_DOUBLE);

	@Override
	protected @NonNull String title() {
		return "Galaxy";
	}

	@Override
	protected @NonNull String description() {
		return "The black hole is pulling clouds of dust in, " +
		       "gravity making them denser and denser, " +
		       "forging an immaculate structure";
	}

	protected int branches() {
		return 1;
	}

	protected double density() {
		return 2d;
	}

	protected int speed() {
		return 15;
	}

	protected double distance(final double angle) {
		return (Math.exp(angle / Math.TAU) - 1d) / (Math.E - 1d);
	}

	protected double radius(final double difference) {
		return difference * Math.PI / branches() / 4d;
	}

	protected double step(final double ratio) {
		return Math.asin(ratio) * 2d / density();
	}

	@Override
	protected void render(final @NonNull Graphics graphics,
	                      final double time) {
		final double maximum = Mathematics.diagonal(Math.max(Math.abs(horizontal().from()),
		                                                      Math.abs(horizontal().to())),
		                                             Math.max(Math.abs(vertical().from()),
		                                                      Math.abs(vertical().to())));
		final int branches = branches();

		for (double angle = phase.at(time); ; ) {
			final double distance = distance(angle);
			final double inside = Math.TAU <= angle ? distance(angle - Math.TAU) : 0d;
			final double radius = radius(distance - inside);

			if (maximum < distance - radius) {
				break;
			}

			for (int branch = 0; branch < branches; branch++) {
				final double sector = angle + Math.TAU * branch / branches;
				graphics.circle(Math.sin(sector) * distance,
				                Math.cos(sector) * distance,
				                radius);
			}

			final double ratio = Mathematics.EPSILON <= distance ? radius / distance : this.ratio;
			angle += step(ratio);
		}
	}
}
