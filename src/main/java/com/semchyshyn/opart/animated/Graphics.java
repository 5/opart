package com.semchyshyn.opart.animated;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Map;
import org.jspecify.annotations.NonNull;

@SuppressWarnings("unused")
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

	public Graphics(final @NonNull BufferedImage image,
	                final int width,
	                final int height,
	                final @NonNull Range horizontal,
	                final @NonNull Range vertical) {
		graphics = image.createGraphics();

		graphics.setRenderingHints(HINTS);
		graphics.setBackground(BACKGROUND);
		graphics.setColor(FOREGROUND);
		graphics.setXORMode(BACKGROUND);

		graphics.setClip(0, 0, width, height);
		graphics.clearRect(0, 0, width, height);

		graphics.scale(width / horizontal.length(),
		               height / vertical.length());
		graphics.translate(-horizontal.from(),
		                   -vertical.from());
	}

	public void circle(final @NonNull Point2D center,
	                   final double radius) {
		circle(center.getX(),
		       center.getY(),
		       radius);
	}

	public void circle(final double abscissa,
	                   final double ordinate,
	                   final double radius) {
		if (radius > 0) {
			final double diameter = radius * 2d;
			final Ellipse2D circle = new Ellipse2D.Double(abscissa - radius,
			                                              ordinate - radius,
			                                              diameter, diameter);

			graphics.fill(circle);
		}
	}

	public void line(final @NonNull Point2D first,
	                 final @NonNull Point2D second) {
		final Path2D halfspace = new Path2D.Double(Path2D.WIND_EVEN_ODD);

//		Line2D
//		Rectangle2D rect = new Rectangle2D.Double();
//		rect.in
//		halfspace.moveTo();

		graphics.fill(halfspace);
	}
}
