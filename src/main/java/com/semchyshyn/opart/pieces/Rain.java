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
///   * [YouTube Short](https://www.youtube.com/shorts/123)
///   * [Instagram Reel](...)
public class Rain extends OpArt {
	protected static class Drop {
		public double abscissa = Range.UNIT_CIRCLE.random();
		public double ordinate = Range.UNIT_CIRCLE.random();
		public final @NonNull Value<Double> radius = new Value<>(Range.UNIT_INTERVAL, Change.LINEAR, Type.DOUBLE);
	}

	protected static final int DROPS = 50;

	protected Collection<Drop> drops = Stream.generate(Drop::new)
	                                         .limit(DROPS)
	                                         .toList();

	@Override
	protected void render(final @NonNull Graphics graphics,
	                      final double time) {
		drops.forEach(drop -> graphics.circle(drop.abscissa,
		                                      drop.ordinate,
		                                      drop.radius.at(time)));
	}

	@SuppressWarnings({"ConfusingMainMethod", "UnnecessaryModifier", "unused"})  // TODO
	public static void main(final @NonNull String... arguments) {
		new Rain().create();
	}
}
