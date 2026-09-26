package com.semchyshyn.opart.pieces;

import com.semchyshyn.opart.animated.Graphics;
import com.semchyshyn.opart.animated.OpArt;
import com.semchyshyn.opart.animated.Range;
import com.semchyshyn.opart.common.Mathematics;
import java.awt.geom.Point2D;
import org.jspecify.annotations.NonNull;

///
/// # Cognition
///
/// Unrelenting rumination of competing ideas
/// overwhelms, but never stops: helplessly confined
/// to an inescapable reticulation
///
public abstract class Cognition extends OpArt {
	private int low(final @NonNull Range range) {
		final double side = side();
		double low = Math.min(range.from(), range.to());

		return (int)Math.floor(low / side);
	}

	private int high(final @NonNull Range range) {
		final double side = side();
		double high = Math.max(range.from(), range.to());

		return (int)Math.floor(Math.nextDown(high) / side);
	}

	private @NonNull Point2D center(final int row,
	                                final int column) {
		final double side = side();

		return new Point2D.Double((column + 0.5d) * side,
		                          (   row + 0.5d) * side);
	}

	@Override
	protected @NonNull String title() {
		return "Cognition";
	}

	@Override
	protected @NonNull String description() {
		return "Unrelenting rumination of competing ideas " +
		       "overwhelms, but never stops: helplessly confined " +
		       "to an inescapable reticulation";
	}

	protected double side() {
		return Mathematics.UNIT;
	}

	protected int steps(final int row,
	                    final int column) {
		return 8;
	}

	protected double phase(final int row,
	                       final int column,
	                       final double time) {
		return Mathematics.ZERO;
	}

	@Override
	protected void render(final @NonNull Graphics graphics,
	                      final double time) {
		final double side = side();
		final int bottom =  low(vertical());
		final int top    = high(vertical());
		final int left   =  low(horizontal());
		final int right  = high(horizontal());

		for (int row = bottom; row <= top; row++) {
			for (int column = left; column <= right; column++) {
				final Point2D center = center(row, column);
				final int steps = steps(row, column);
				final double phase = phase(row, column, time);

				for (int step = steps + 1; 0 < step; step--) {
					final double apothem = side * (step / 2d - phase) / steps;

					if (Mathematics.ZERO < apothem) {
						graphics.square(center, Math.min(apothem, side / 2d));
					}
				}
			}
		}

		for (int row = bottom; row <= top; row++) {
			graphics.line(center(row,  left),
			              center(row, right));
		}

		for (int column = left; column <= right; column++) {
			graphics.line(center(bottom, column),
			              center(   top, column));
		}
	}
}
