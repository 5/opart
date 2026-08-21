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
///   * [YouTube](https://www.youtube.com/shorts/...)
///   * [Instagram](https://www.instagram.com/reels/...)
///
public class GalaxyTriple extends Galaxy {
	@Override
	protected @NonNull Resolution resolution() {
		return Resolution.VERTICAL;
	}

	@Override
	protected @NonNull VCodec vcodec() {
		return VCodec.FFV1;//H264;
	}

	@Override
	protected @NonNull ACodec acodec() {
		return ACodec.FLAC;//AAC;
	}

	@Override
	protected int branches() {
		return 3;
	}

	@Override
	protected double density() {
		return 4d;
	}

	@SuppressWarnings({"UnnecessaryModifier", "unused"})
	public static void main(final @NonNull String... arguments) {
		new GalaxyTriple().create();
	}
}
