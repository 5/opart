package com.semchyshyn.opart.pieces;

import com.semchyshyn.opart.animated.ACodec;
import com.semchyshyn.opart.animated.Change;
import com.semchyshyn.opart.animated.Range;
import com.semchyshyn.opart.animated.Resolution;
import com.semchyshyn.opart.animated.Type;
import com.semchyshyn.opart.animated.VCodec;
import com.semchyshyn.opart.animated.Value;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.stream.IntStream;
import org.jspecify.annotations.NonNull;

///
/// # Gem
///
/// Animated Optical Art
///
///   * [YouTube](https://youtube.com/shorts/QzhIAMdkI6g)
///   * [Instagram](https://www.instagram.com/reels/DcCr8mNhd6v)
///
public class GemCounterrotating extends Gem {
	@Override
	protected @NonNull Resolution resolution() {
		return Resolution.VERTICAL;
	}

	@Override
	protected @NonNull VCodec vcodec() {
		return VCodec.H264;
	}

	@Override
	protected @NonNull ACodec acodec() {
		return ACodec.AAC;
	}

	@Override
	protected @NonNull List<Value<Point2D>> points() {
		final int points = count() & ~1;
		final double range = Math.PI * (points - 2) / points;
		final Range[] ranges = {new Range(0d,  range),
		                        new Range(0d, -range)};

		return IntStream.range(0, points)
		                .mapToObj(point -> new Value<>(ranges[point & 1].shift(Math.PI * (point * 2 + 1) / points),
		                                               Change.LINEAR,
		                                               Type.POLAR_POINT))
		                .toList();
	}

	@SuppressWarnings({"UnnecessaryModifier", "unused"})
	public static void main(final @NonNull String... arguments) {
		new GemCounterrotating().create();
	}
}
