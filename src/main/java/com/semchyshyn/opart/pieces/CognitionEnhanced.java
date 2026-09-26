package com.semchyshyn.opart.pieces;

import com.semchyshyn.opart.animated.ACodec;
import com.semchyshyn.opart.animated.Change;
import com.semchyshyn.opart.animated.Range;
import com.semchyshyn.opart.animated.Resolution;
import com.semchyshyn.opart.animated.Type;
import com.semchyshyn.opart.animated.VCodec;
import com.semchyshyn.opart.animated.Value;
import com.semchyshyn.opart.common.Mathematics;
import org.jspecify.annotations.NonNull;

///
/// # Cognition
///
/// Animated Optical Art
///
///   * [YouTube](https://youtube.com/shorts/0nvmr4h7fvM)
///   * [Instagram](https://www.instagram.com/reels/DduoasqhHB0)
///
public class CognitionEnhanced extends Cognition {
	protected final @NonNull Value<Double> phase = new Value<>(Range.UNIT_INTERVAL,
	                                                           Change.LINEAR
	                                                                 .repeating()
	                                                                 .speedup(speed()),
	                                                           Type.LINEAR_DOUBLE);

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
	protected double phase(final int row,
	                       final int column,
	                       final double time) {
		double phase = this.phase.at(time);

		if (Mathematics.even(row + column)) {
			phase = Mathematics.UNIT - phase;
		}

		return phase;
	}

	protected int speed() {
		return 8;
	}

	@SuppressWarnings({"UnnecessaryModifier", "unused"})
	public static void main(final @NonNull String @NonNull... arguments) {
		new CognitionEnhanced().create();
	}
}
