package com.semchyshyn.opart.pieces;

import com.semchyshyn.opart.animated.Graphics;
import com.semchyshyn.opart.animated.OpArt;
import com.semchyshyn.opart.animated.Value;
import java.awt.geom.Point2D;
import java.util.List;
import org.jspecify.annotations.NonNull;

///
/// # Gem
///
/// Crisp edges dissect the space,
/// over, and over, and over again,
/// manifesting a mesmerizing layout
///
public abstract class Gem extends OpArt {
	protected final @NonNull List<Value<Point2D>> points = points();

	@Override
	protected @NonNull String title() {
		return "Gem";
	}

	@Override
	protected @NonNull String description() {
		return "Crisp edges dissect the space, " +
		       "over, and over, and over again, " +
		       "manifesting a mesmerizing layout";
	}

	protected int count() {
		return 12;
	}

	protected abstract @NonNull List<Value<Point2D>> points();

	@Override
	protected void render(final @NonNull Graphics graphics,
	                      final double time) {
		Point2D[] points = this.points.stream()
		                              .map(point -> point.at(time))
		                              .toArray(Point2D[]::new);

		for (int first = 0; first < points.length; first++) {
			for (int second = first + 1; second < points.length; second++) {
				graphics.line(points[first],
				              points[second]);
			}
		}
	}
}
