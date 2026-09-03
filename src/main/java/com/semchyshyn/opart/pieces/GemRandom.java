package com.semchyshyn.opart.pieces;

import com.semchyshyn.opart.animated.ACodec;
import com.semchyshyn.opart.animated.Change;
import com.semchyshyn.opart.animated.Range;
import com.semchyshyn.opart.animated.Resolution;
import com.semchyshyn.opart.animated.Type;
import com.semchyshyn.opart.animated.VCodec;
import com.semchyshyn.opart.animated.Value;
import com.semchyshyn.opart.common.Array;
import com.semchyshyn.opart.common.Mathematics;
import com.semchyshyn.opart.common.Random;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import org.jspecify.annotations.NonNull;

///
/// # Gem
///
/// Animated Optical Art
///
///   * [YouTube](https://youtube.com/shorts/iRpQv6pckMM)
///   * [Instagram](https://www.instagram.com/reels/DcxSsYFhAj8)
///
public class GemRandom extends Gem {
	protected static final @NonNull Comparator<Point2D> COMPARATOR = Comparator.comparing(Mathematics::arctangent);

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
		                .mapToObj(point -> Math.TAU * point / points)
		                .map(angle -> new Value<>(new Range(Random.next(-Math.PI, Math.PI) + angle, angle),
		                                          Change.SIGMOID
		                                                .mirror(),
		                                          Type.POLAR_POINT))
		                .toList();
	}

	@Override
	protected @NonNull Point2D[] points(final double time) {
		final Point2D[] points = super.points(time);

		final Point2D first = points[0];
		Arrays.sort(points, COMPARATOR);

		int index = Array.find(points, first);
		index = Math.min(index, 7);
		Array.rotate(points, -index);

		return points;
	}

	@SuppressWarnings({"UnnecessaryModifier", "unused"})
	public static void main(final @NonNull String... arguments) {
		new GemRandom().create();
	}
}
