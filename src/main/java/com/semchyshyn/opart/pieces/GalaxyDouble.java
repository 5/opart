package com.semchyshyn.opart.pieces;

import com.semchyshyn.opart.animated.ACodec;
import com.semchyshyn.opart.animated.Resolution;
import com.semchyshyn.opart.animated.VCodec;
import org.jspecify.annotations.NonNull;

///
/// # Galaxy
///
/// Animated Optical Art
///
///   * [YouTube](https://youtube.com/shorts/E4ysQd7p5s4)
///   * [Instagram](https://www.instagram.com/reels/DcSczheBMz6)
///
public class GalaxyDouble extends Galaxy {
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
	protected int branches() {
		return 2;
	}

	@Override
	protected int speed() {
		return 20;
	}

	@SuppressWarnings({"UnnecessaryModifier", "unused"})
	public static void main(final @NonNull String... arguments) {
		new GalaxyDouble().create();
	}
}
