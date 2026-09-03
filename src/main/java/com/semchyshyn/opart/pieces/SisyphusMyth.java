package com.semchyshyn.opart.pieces;

import com.semchyshyn.opart.animated.ACodec;
import com.semchyshyn.opart.animated.Resolution;
import com.semchyshyn.opart.animated.VCodec;
import org.jspecify.annotations.NonNull;

///
/// # Sisyphus
///
/// Animated Optical Art
///
///   * [YouTube](https://youtube.com/shorts/I9yWoVn8Mas)
///   * [Instagram](https://www.instagram.com/reels/DcurQ4IBQLb)
///
public class SisyphusMyth extends Sisyphus {
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

	protected int speed() {
		return 10;
	}

	@SuppressWarnings({"UnnecessaryModifier", "unused"})
	public static void main(final @NonNull String... arguments) {
		new SisyphusMyth().create();
	}
}
