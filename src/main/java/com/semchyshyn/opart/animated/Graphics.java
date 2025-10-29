package com.semchyshyn.opart.animated;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.Map;
import org.jspecify.annotations.NonNull;

public class Graphics {
	protected static final @NonNull Map<RenderingHints.Key, Object> HINTS = Map.of(
		RenderingHints.KEY_RENDERING,         RenderingHints.VALUE_RENDER_QUALITY,
		RenderingHints.KEY_COLOR_RENDERING,   RenderingHints.VALUE_COLOR_RENDER_QUALITY,
		RenderingHints.KEY_INTERPOLATION,     RenderingHints.VALUE_INTERPOLATION_BICUBIC,
		RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON,
		RenderingHints.KEY_DITHERING,         RenderingHints.VALUE_DITHER_DISABLE,
		RenderingHints.KEY_STROKE_CONTROL,    RenderingHints.VALUE_STROKE_PURE,
		RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON
	);
	protected static final @NonNull Color BACKGROUND = Color.BLACK;
	protected static final @NonNull Color FOREGROUND = Color.WHITE;

	protected final @NonNull Graphics2D graphics;

	public Graphics(final @NonNull Graphics2D graphics,
	                final int width,
	                final int height,
	                final @NonNull Range horizontal,
	                final @NonNull Range vertical) {
		this.graphics = graphics;

		// TODO: move into protected void initialize() ?? or even into prepare/initialize upstack ??

		graphics.setRenderingHints(HINTS);
		graphics.setBackground(BACKGROUND);
		graphics.setColor(FOREGROUND);
		graphics.setXORMode(BACKGROUND);

		graphics.clearRect(0, 0, width, height); // TODO: is clip properly set by default?

		graphics.scale(width / horizontal.length(),
		               height / vertical.length());
		graphics.translate(-horizontal.from(),
		                   -vertical.from());
	}

	public void circle(final double x,
	                   final double y,
	                   final double radius) {
		final double diameter = radius * 2d;
		final Shape circle = new Ellipse2D.Double(x - radius,
		                                          y - radius,
		                                          diameter, diameter);
		graphics.fill(circle);
	}
}
