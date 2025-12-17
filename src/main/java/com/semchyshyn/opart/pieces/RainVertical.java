package com.semchyshyn.opart.pieces;

import com.semchyshyn.opart.animated.ACodec;
import com.semchyshyn.opart.animated.Graphics;
import com.semchyshyn.opart.animated.Resolution;
import com.semchyshyn.opart.animated.VCodec;
import org.jspecify.annotations.NonNull;

///
///  # Rain
///
/// Animated Optical Art
///
///   * [YouTube](https://www.youtube.com/shorts/qHC0lB2HCJk)
///   * [TikTok](https://www.tiktok.com/@an.op.art/video/7584137015223012638)
///   * [Instagram](https://www.instagram.com/reel/DSSvXePAWAS)
///
public class RainVertical extends Rain {
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

	protected double intensity() {
		return 1.5d;
	}

	@Override
	protected void render(final @NonNull Graphics graphics,
	                      final double time) {
		super.render(graphics, time + temporal().length());
		super.render(graphics, time);
	}

	@SuppressWarnings({"UnnecessaryModifier", "unused"})
	public static void main(final @NonNull String... arguments) {
		new RainVertical().create();
	}
}
