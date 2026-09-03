package com.semchyshyn.opart.pieces;

import com.semchyshyn.opart.animated.ACodec;
import com.semchyshyn.opart.animated.Change;
import com.semchyshyn.opart.animated.Range;
import com.semchyshyn.opart.animated.Resolution;
import com.semchyshyn.opart.animated.Type;
import com.semchyshyn.opart.animated.VCodec;
import com.semchyshyn.opart.animated.Value;
import com.semchyshyn.opart.common.Random;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.stream.IntStream;
import org.jspecify.annotations.NonNull;

///
/// # Gem
///
/// Animated Optical Art
///
///   * [YouTube](https://youtube.com/shorts/iwHwsS3QL0w)
///   * [Instagram](https://www.instagram.com/reels/Dco0Qcahoug)
///
public class GemFree extends Gem {
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

		Random.skip(5);

		return IntStream.range(0, points)
		                .mapToObj(point -> Math.TAU * point / points)
		                .map(angle -> new Value<>(Range.UNIT_INTERVAL,
		                                          Change.SIGMOID
		                                                .mirror(),
		                                          new Type.CartesianPoint(Random.point(),
		                                                                  Type.POLAR_POINT.from(angle))))
		                .toList();
	}

	@SuppressWarnings({"UnnecessaryModifier", "unused"})
	public static void main(final @NonNull String... arguments) {
		new GemFree().create();
	}
}
