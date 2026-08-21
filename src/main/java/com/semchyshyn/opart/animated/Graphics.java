package com.semchyshyn.opart.animated;

import com.semchyshyn.opart.common.Mathematics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
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
	protected static final @NonNull Point2D ORIGIN = new Point2D.Double(0d, 0d);
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

	public void shift(final double horizontal,
	                  final double vertical) {
		graphics.translate(horizontal,
		                   vertical);
	}

	public void scale(final double horizontal,
	                  final double vertical) {
		graphics.scale(horizontal,
		               vertical);
	}

	public void rotate(final double angle) {
		graphics.rotate(angle);
	}

	public void circle(final double radius) {
		circle(ORIGIN,
		       radius);
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
		if (Mathematics.EPSILON <= radius) {
			final double diameter = radius * 2d;
			final Ellipse2D circle = new Ellipse2D.Double(abscissa - radius,
			                                              ordinate - radius,
			                                              diameter, diameter);

			graphics.fill(circle);
		}
	}

	public void line(final @NonNull Point2D point) {
		line(ORIGIN,
		     point);
	}

	public void line(final @NonNull Point2D first,
	                 final @NonNull Point2D second) {
		final double abscissa = (first.getX() + second.getX()) / 2d;
		final double ordinate = (first.getY() + second.getY()) / 2d;

		double width = second.getX() - first.getX();
		double height = second.getY() - first.getY();

		final double length = Mathematics.diagonal(width, height);

		if (Mathematics.EPSILON <= length) {
			final Rectangle2D clip = graphics.getClip().getBounds2D();

			final double maximum = Mathematics.diagonal(clip.getWidth(),
			                                            clip.getHeight());

			final double factor = maximum / length;
			width *= factor;
			height *= factor;

			final Path2D halfspace = new Path2D.Double(Path2D.WIND_EVEN_ODD, 4);
			halfspace.moveTo(abscissa - width,          ordinate - height);
			halfspace.lineTo(abscissa - width + height, ordinate - height - width);
			halfspace.lineTo(abscissa + width + height, ordinate + height - width);
			halfspace.lineTo(abscissa + width,          ordinate + height);
			halfspace.closePath();

			final Area area = new Area(halfspace);
			area.intersect(new Area(clip));

			graphics.fill(area);
		}
	}
}
