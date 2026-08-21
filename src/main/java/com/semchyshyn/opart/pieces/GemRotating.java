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
///   * [YouTube](https://youtube.com/shorts/CHNdCtq5Kfw)
///   * [Instagram](https://www.instagram.com/reels/Db2PvdCBbSL)
///
public class GemRotating extends Gem {
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
		final int points = count();

		return IntStream.range(0, points)
		                .mapToObj(point -> new Value<>(Range.FULL_CIRCLE.shift(Math.TAU * point / points),
		                                               Change.LINEAR,
		                                               Type.RADIAL_POINT))
		                .toList();
	}

	@SuppressWarnings({"UnnecessaryModifier", "unused"})
	public static void main(final @NonNull String... arguments) {
		new GemRotating().create();
	}
}
