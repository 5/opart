package com.semchyshyn.opart.pieces;

import com.semchyshyn.opart.animated.Graphics;
import com.semchyshyn.opart.animated.OpArt;
import com.semchyshyn.opart.animated.Value;
import java.awt.geom.Point2D;
import org.jspecify.annotations.NonNull;

///
/// # Gem
///
/// qwe
/// asd
/// zxc
///
public abstract class Gem extends OpArt {
	protected final @NonNull Value<Double>[] angles = angles();

	@Override
	protected @NonNull String title() {
		return "Gem";
	}

	@Override
	protected @NonNull String description() {
		return "qwe" +
		       "asd" +
		       "zxc";
	}

	protected abstract  @NonNull Value<Double>[] angles();

	protected @NonNull Point2D vertex(final double angle) {
		return new Point2D.Double(Math.sin(angle),
		                          Math.cos(angle));
	}

	@Override
	protected void render(@NonNull Graphics graphics,
	                      double time) {
		for (int first = 0; first < angles.length; first++) {
			for (int second = first + 1; second < angles.length; second++) {
				graphics.line(vertex(angles[first].at(time)),
				              vertex(angles[second].at(time)));
			}
		}
	}
}
