package com.semchyshyn.opart.animated;

import com.semchyshyn.opart.common.Mathematics;
import java.awt.geom.Point2D;
import org.jspecify.annotations.NonNull;

@FunctionalInterface
@SuppressWarnings({"ProtectedMemberInFinalClass", "UnnecessaryModifier", "unused"})
public abstract interface Type<Class> {
	public static final @NonNull Type<Double> LINEAR_DOUBLE = value -> value;
	public static final @NonNull Type<Double> INVERTED_DOUBLE = value -> -value;
	public static final @NonNull Type<Point2D> POLAR_POINT = new PolarPoint();

	public static final class PolarPoint implements Type<Point2D> {
		protected final double radius;

		public PolarPoint() {
			this(Mathematics.UNIT);
		}

		public PolarPoint(final double radius) {
			this.radius = radius;
		}

		@Override
		public @NonNull Point2D from(final double angle) {
			return new Point2D.Double(Math.sin(angle) * radius,
			                          Math.cos(angle) * radius);
		}
	}

	public static final class CartesianPoint implements Type<Point2D> {
		protected final double abscissa;
		protected final double ordinate;
		protected final double horizontal;
		protected final double vertical;

		public CartesianPoint(final @NonNull Point2D terminal) {
			this(Mathematics.ORIGIN,
			     terminal);
		}

		public CartesianPoint(final @NonNull Point2D initial,
		                      final @NonNull Point2D terminal) {
			horizontal = terminal.getX() - (abscissa = initial.getX());
			vertical   = terminal.getY() - (ordinate = initial.getY());
		}

		@Override
		public @NonNull Point2D from(final double progress) {
			return new Point2D.Double(progress * horizontal + abscissa,
			                          progress * vertical   + ordinate);
		}
	}

	public abstract @NonNull Class from(final double value);
}
