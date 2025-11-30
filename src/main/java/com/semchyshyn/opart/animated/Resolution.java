package com.semchyshyn.opart.animated;

import org.jspecify.annotations.NonNull;

@SuppressWarnings({"ProtectedMemberInFinalClass", "unused"})
public enum Resolution {
	VERTICAL  (3840, 2160),
	SQUARE    (2160, 2160),
	HORIZONTAL(2160, 3840),
	WIDE      (2160, 5120);

	protected final int width;
	protected final int height;
	protected final Range horizontal;
	protected final Range vertical;

	protected static Range range(final int dimension) {
		final double half = dimension / 2d / 1e3d;

		return new Range(-half, +half);
	}

	@SuppressWarnings("UnnecessaryModifier")
	private Resolution(final int width,
	                   final int height) {
		this.width = width;
		this.height = height;

		horizontal = range(width);
		vertical = range(height).reverse();
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
