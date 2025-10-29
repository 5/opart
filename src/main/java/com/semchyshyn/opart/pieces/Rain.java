package com.semchyshyn.opart.pieces;

import com.semchyshyn.opart.animated.Change;
import com.semchyshyn.opart.animated.Graphics;
import com.semchyshyn.opart.animated.OpArt;
import com.semchyshyn.opart.animated.Range;
import com.semchyshyn.opart.animated.Type;
import com.semchyshyn.opart.animated.Value;
import org.jspecify.annotations.NonNull;

public class Rain extends OpArt {
	protected static class Drop {
		public final @NonNull Value<Double> radius = new Value<>(Range.UNIT, Change.LINEAR, Type.DOUBLE);
	}

	protected static final int DROPS = 50;


	@Override
	protected void render(final @NonNull Graphics graphics, final double time) {
		graphics.circle(0d, 0d, radius.at(time));
	}

	@SuppressWarnings({"UnnecessaryModifier", "unused"})
	public static void main(final @NonNull String... arguments) {
		new Rain().create();
	}
}
