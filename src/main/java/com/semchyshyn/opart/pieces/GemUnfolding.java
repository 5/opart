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
///   * [YouTube](https://youtube.com/shorts/...)
///   * [Instagram](https://www.instagram.com/reels/...)
///
public class GemUnfolding extends Gem {
//	@Override
//	protected @NonNull Resolution resolution() {
//		return Resolution.VERTICAL;
//	}

	@Override
	protected @NonNull VCodec vcodec() {
		return VCodec.FFV1;//H264;
	}

	@Override
	protected @NonNull ACodec acodec() {
		return ACodec.FLAC;//AAC;
	}

	@Override
	protected @NonNull List<Value<Point2D>> points() {
		final int points = count();

		// ...

		return IntStream.range(0, points)
		                .mapToObj(point -> new Value<>(Range.ZERO_POINT.shift(Math.TAU * point / points),
		                                               Change.CONSTANT,
		                                               Type.RADIAL_POINT))
		                .toList();
	}

	@SuppressWarnings({"UnnecessaryModifier", "unused"})
	public static void main(final @NonNull String... arguments) {
		new GemUnfolding().create();
	}
}
