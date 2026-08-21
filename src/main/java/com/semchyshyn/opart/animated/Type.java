package com.semchyshyn.opart.animated;

import java.awt.geom.Point2D;
import org.jspecify.annotations.NonNull;

@FunctionalInterface
@SuppressWarnings({"UnnecessaryModifier", "unused"})
public abstract interface Type<Class> {
	public static final @NonNull Type<Double> LINEAR_DOUBLE = value -> value;
	public static final @NonNull Type<Double> INVERTED_DOUBLE = value -> -value;
	public static final @NonNull Type<Point2D> RADIAL_POINT = angle -> new Point2D.Double(Math.sin(angle),
	                                                                                      Math.cos(angle));
//	public static final @NonNull Type<Point2D> CARTESIAN_POINT = value -> TODO;

	public abstract @NonNull Class from(final double value);
}
