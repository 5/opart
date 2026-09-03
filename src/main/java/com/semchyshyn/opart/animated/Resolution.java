package com.semchyshyn.opart.animated;

import org.jspecify.annotations.NonNull;

@SuppressWarnings({"ProtectedMemberInFinalClass", "UnnecessaryModifier", "unused"})
public enum Resolution {
	PREVIEW   (1080, 1080,  500),

	TALL      (2160, 5120, 1000),
	VERTICAL  (2160, 3840, 1000),
	SQUARE    (2160, 2160, 1000),
	HORIZONTAL(3840, 2160, 1000),
	WIDE      (5120, 2160, 1000),

	PRINT     (7200, 7200, 3000);

	protected final int width;
	protected final int height;
	protected final @NonNull Range horizontal;
	protected final @NonNull Range vertical;

	private Resolution(final int width,
	                   final int height,
	                   final int unit) {
		this.width = width;
		this.height = height;

		horizontal = range(width, unit);
		vertical = range(height, unit).reverse();
	}

	protected static @NonNull Range range(final int dimension,
	                                      final int unit) {
		final double half = (double)dimension / (double)unit / 2d;

		return new Range(-half, +half);
	}

	public int width() {
		return width;
	}

	public int height() {
		return height;
	}

	public @NonNull Range horizontal() {
		return horizontal;
	}

	public @NonNull Range vertical() {
		return vertical;
	}
}
