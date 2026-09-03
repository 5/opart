package com.semchyshyn.opart.pieces;

import com.semchyshyn.opart.animated.Change;
import com.semchyshyn.opart.animated.Graphics;
import com.semchyshyn.opart.animated.OpArt;
import com.semchyshyn.opart.animated.Range;
import com.semchyshyn.opart.animated.Type;
import com.semchyshyn.opart.animated.Value;
import com.semchyshyn.opart.common.Mathematics;
import java.awt.geom.Point2D;
import org.jspecify.annotations.NonNull;

///
/// # Sisyphus
///
/// Heavy triangular boulders:
/// pushed up, rolling down,
/// leaving nothing but tracery behind
///
public abstract class Sisyphus extends OpArt {
	protected final double minimum = Mathematics.maximum(Math.abs(horizontal().from()),
	                                                     Math.abs(horizontal().to()),
	                                                     Math.abs(vertical().from()),
	                                                     Math.abs(vertical().to()));
	protected final double maximum = minimum * 2d;
	protected final @NonNull Value<Double> push = new Value<>(new Range(maximum, minimum),
	                                                          Change.LINEAR
	                                                                .repeating()
	                                                                .speedup(speed()),
	                                                          Type.LINEAR_DOUBLE);
	protected final @NonNull Value<Double> roll = new Value<>(new Range(minimum, maximum),
	                                                          Change.LINEAR
	                                                                .repeating()
	                                                                .speedup(speed()),
	                                                          Type.LINEAR_DOUBLE);

	@Override
	protected @NonNull String title() {
		return "Sisyphus";
	}

	@Override
	protected @NonNull String description() {
		return "Heavy triangular boulders: " +
		       "pushed up, rolling down, " +
		       "leaving nothing but tracery behind";
	}

	protected int speed() {
		return 5;
	}

	protected void quadrant(final @NonNull Graphics graphics,
	                        double side,
	                        final double horizontal,
	                        final double vertical,
	                        final double diagonal) {
		final double zero = Mathematics.ZERO;

		for (double half = side / 2d;
		     Mathematics.EPSILON <= half;
		     half = (side = half) / 2d) {

			final double outer = half * (diagonal + 3d) / 2d;
			final double inner = half * (diagonal + 1d) / 2d;

			graphics.triangle(new Point2D.Double(horizontal * side,  vertical * half),
			                  new Point2D.Double(horizontal * half,  vertical * side),
			                  new Point2D.Double(horizontal * outer, vertical * outer));
			graphics.triangle(new Point2D.Double(horizontal * side,             zero),
			                  new Point2D.Double(horizontal * half,  vertical * half),
			                  new Point2D.Double(horizontal * outer, vertical * inner));
			graphics.triangle(new Point2D.Double(horizontal * half,  vertical * half),
			                  new Point2D.Double(             zero,  vertical * side),
			                  new Point2D.Double(horizontal * inner, vertical * outer));
		}
	}

	@Override
	protected void render(final @NonNull Graphics graphics,
	                      final double time) {
		final double unit = Mathematics.UNIT;
		final double push = this.push.at(time);
		final double roll = this.roll.at(time);

		quadrant(graphics, push, -unit,  unit, -unit);
		quadrant(graphics, roll, -unit, -unit,  unit);
		quadrant(graphics, push,  unit, -unit, -unit);
		quadrant(graphics, roll,  unit,  unit,  unit);
	}
}
