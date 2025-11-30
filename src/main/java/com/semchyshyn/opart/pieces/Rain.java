package com.semchyshyn.opart.pieces;

import com.semchyshyn.opart.animated.Change;
import com.semchyshyn.opart.animated.Graphics;
import com.semchyshyn.opart.animated.OpArt;
import com.semchyshyn.opart.animated.Range;
import com.semchyshyn.opart.animated.Type;
import com.semchyshyn.opart.animated.Value;
import java.util.Collection;
import java.util.stream.Stream;
import org.jspecify.annotations.NonNull;

/// # Rain
///
/// Drops of rain fall randomly,
/// causing ripples to interfere endlessly,
/// conjuring a beautiful pattern
public class Rain extends OpArt {
	protected class Drop {
		protected static final Range RADIUS = new Range(0d, 3d);

		public final double abscissa = horizontal().random();
		public final double ordinate = vertical().random();
		public final @NonNull Value<Double> radius = new Value<>(RADIUS,
		                                                         Change.LINEAR.delay(temporal().random()),
		                                                         Type.DOUBLE);

//		public Drop(final @NonNull Rain rain) {
//			abscissa = rain.
//		}
	}

	protected static final int DROPS = 20;

	protected Collection<Drop> drops = Stream.generate(Drop::new)
	                                         .limit(DROPS)
	                                         .toList();

	@Override
	protected @NonNull String description() {
		return "Drops of rain fall randomly, " +
		       "causing ripples to interfere endlessly, " +
		       "conjuring a beautiful pattern";
	}

	@Override
	protected void render(final @NonNull Graphics graphics,
	                      final double time) {
		drops.forEach(drop -> graphics.circle(drop.abscissa,
		                                      drop.ordinate,
		                                      drop.radius.at(time)));
	}

	@SuppressWarnings({"UnnecessaryModifier", "unused"})
	public static void main(final @NonNull String... arguments) {
		new Rain().create();
	}
}
