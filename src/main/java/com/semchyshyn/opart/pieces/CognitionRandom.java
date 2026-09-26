package com.semchyshyn.opart.pieces;

import com.semchyshyn.opart.animated.ACodec;
import com.semchyshyn.opart.animated.Change;
import com.semchyshyn.opart.animated.Range;
import com.semchyshyn.opart.animated.Resolution;
import com.semchyshyn.opart.animated.Type;
import com.semchyshyn.opart.animated.VCodec;
import com.semchyshyn.opart.animated.Value;
import com.semchyshyn.opart.common.Mathematics;
import com.semchyshyn.opart.common.Random;
import org.jspecify.annotations.NonNull;

///
/// # Cognition
///
/// Animated Optical Art
///
///   * [YouTube](https://youtube.com/shorts/...)
///   * [Instagram](https://www.instagram.com/reels/...)
///
@SuppressWarnings("unchecked")
public class CognitionRandom extends Cognition {
	private final int @NonNull[] @NonNull[] steps = new int[16][9];
	private final @NonNull Value<Double> @NonNull[] @NonNull[] phases = new Value[16][9];

	{
		for (int row = 0; row < 16; row++) {
			for (int column = 0; column < 9; column++) {
//				final int q = Random.next(2, 5);
//				final int q = (int)(6.25d - (Math.abs(row - 8) + Math.abs(column - 4.5d)) / 2) + 1;
//				final int q = (row + column) % 6 + 1;
				final int q = 3;

				// TODO: Split into one "Random" - random steps with synchronized phase,
				//       and one "Perfect" (?) - fixed steps (2 or 3) with oval phase delay out of the center (when center=0 - corner=1)

				steps[row][column] = q;
				phases[row][column] = new Value<Double>(Range.UNIT_INTERVAL,
				                                        Change.LINEAR.repeating().speedup(q).delay((row + column) / 25d),
				                                        Type.LINEAR_DOUBLE);
			}
		}
	}

	@Override
	protected @NonNull Resolution resolution() {
		return Resolution.VERTICAL;
	}

	@Override
	protected @NonNull Range horizontal() {
		return new Range(Mathematics.ZERO, 2.16d);
	}

	@Override
	protected @NonNull Range vertical() {
		return new Range(Mathematics.ZERO, 3.84d);
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
	protected double side() {
		return 0.24d;  // GCD of 2160 and 3840
	}

	@Override
	protected int steps(final int row,
	                    final int column) {
		return steps[row][column];
	}

	@Override
	protected double phase(final int row,
	                       final int column,
	                       final double time) {
		return phases[row][column].at(time);
	}

	@SuppressWarnings({"UnnecessaryModifier", "unused"})
	public static void main(final @NonNull String @NonNull... arguments) {
		new CognitionRandom().create();
	}
}
