package com.semchyshyn.opart.pieces;

import com.semchyshyn.opart.animated.ACodec;
import com.semchyshyn.opart.animated.Change;
import com.semchyshyn.opart.animated.Range;
import com.semchyshyn.opart.animated.Resolution;
import com.semchyshyn.opart.animated.Type;
import com.semchyshyn.opart.animated.VCodec;
import com.semchyshyn.opart.animated.Value;
import org.jspecify.annotations.NonNull;

///
/// # Gem
///
/// Animated Optical Art
///
///   * [YouTube](https://www.youtube.com/shorts/...)
///   * [TikTok](https://www.tiktok.com/@an.op.art/video/...)
///   * [Instagram](https://www.instagram.com/reel/...)
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
	@SuppressWarnings("unchecked")
	protected @NonNull Value<Double>[] angles() {
		return new Value[] {
			new Value<>(Range.FULL_CIRCLE.shift(                0d), Change.LINEAR, Type.DOUBLE),
			new Value<>(Range.FULL_CIRCLE.shift(Math.PI / 6d      ), Change.LINEAR, Type.DOUBLE),
			new Value<>(Range.FULL_CIRCLE.shift(Math.PI / 3d      ), Change.LINEAR, Type.DOUBLE),
			new Value<>(Range.FULL_CIRCLE.shift(Math.PI / 2d      ), Change.LINEAR, Type.DOUBLE),
			new Value<>(Range.FULL_CIRCLE.shift(Math.PI / 3d *  2d), Change.LINEAR, Type.DOUBLE),
			new Value<>(Range.FULL_CIRCLE.shift(Math.PI / 6d *  5d), Change.LINEAR, Type.DOUBLE),
			new Value<>(Range.FULL_CIRCLE.shift(Math.PI           ), Change.LINEAR, Type.DOUBLE),
			new Value<>(Range.FULL_CIRCLE.shift(Math.PI / 6d *  7d), Change.LINEAR, Type.DOUBLE),
			new Value<>(Range.FULL_CIRCLE.shift(Math.PI / 3d *  4d), Change.LINEAR, Type.DOUBLE),
			new Value<>(Range.FULL_CIRCLE.shift(Math.PI / 2d *  3d), Change.LINEAR, Type.DOUBLE),
			new Value<>(Range.FULL_CIRCLE.shift(Math.PI / 3d *  5d), Change.LINEAR, Type.DOUBLE),
			new Value<>(Range.FULL_CIRCLE.shift(Math.PI / 6d * 11d), Change.LINEAR, Type.DOUBLE)
		};
	}

	@SuppressWarnings({"UnnecessaryModifier", "unused"})
	public static void main(final @NonNull String... arguments) {
		new GemRotating().create();
	}
}
