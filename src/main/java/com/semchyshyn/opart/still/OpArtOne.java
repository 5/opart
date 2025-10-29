package com.semchyshyn.opart.still;

import com.google.common.base.Preconditions;
import com.google.common.io.Files;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import javax.imageio.ImageIO;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.OptionHandlerFilter;
import org.kohsuke.args4j.ParserProperties;
import org.kohsuke.args4j.spi.ExplicitBooleanOptionHandler;

@SuppressWarnings("FieldMayBeFinal")
public final class OpArtOne {
	private static final String CLASS_NAME = OpArtOne.class.getSimpleName();
	private static final int DEFAULT_SIZE = 12 * 600;
	private static final double DEFAULT_FRACTION = Math.sqrt(2d / Math.PI);
	private static final int DEFAULT_SEGMENTS = 12;
	private static final int DEFAULT_POINTS = 1;
	private static final boolean DEFAULT_CLIP = true;
	private static final int DEFAULT_BORDER = 8;
	private static final String DEFAULT_FORMAT = "png";
	private static final int ERROR_PARSING = 1;
	private static final int ERROR_ALLOCATING = 2;
	private static final int ERROR_SAVING = 3;

	@Option(name = "--size", metaVar = "SIZE", usage = "Side of the square image in pixels")
	private int size = DEFAULT_SIZE;
	@Option(name = "--fraction", metaVar = "FRACTION", usage = "Diameter of the circle as a fraction of the size")
	private double fraction = DEFAULT_FRACTION;
	@Option(name = "--segments", metaVar = "SEGMENTS", usage = "Number of segments on the circle")
	private int segments = DEFAULT_SEGMENTS;
	@Option(name = "--points", metaVar = "POINTS", usage = "Number of points in each segment")
	private int points = DEFAULT_POINTS;
	@Option(name = "--clip", metaVar = "CLIP", usage = "Whether to clip lines outside of the circle", handler = ExplicitBooleanOptionHandler.class)
	private boolean clip = DEFAULT_CLIP;
	@Option(name = "--border", metaVar = "BORDER", usage = "Thickness of the border to draw outside the circle")
	private int border = DEFAULT_BORDER;
	@Option(name = "--file", metaVar = "FILE", usage = "Name of the file to save the image in")
	private String file = CLASS_NAME + "." + DEFAULT_FORMAT;

	private OpArtOne() {
	}

	@SuppressWarnings("deprecation")
	private static ProgressBar progress(final String task, final int steps) {
		return new ProgressBar(task, steps, 1, false, false, System.err, ProgressBarStyle.ASCII, "", 1L, false, null, null, 0L, Duration.ZERO);
	}

	@SuppressWarnings("UnnecessaryModifier")
	public static void main(final String... arguments) {
		System.err.println(CLASS_NAME + " (c) 2025 Yuriy Semchyshyn");

		OpArtOne art = null;
		CmdLineParser parser = null;
		try (final ProgressBar parsing = progress("Parsing", 1)) {
			art = new OpArtOne();
			final ParserProperties properties = ParserProperties.defaults().withUsageWidth(0xFFFF);
			parser = new CmdLineParser(art, properties);
			parser.parseArgument(arguments);
			Preconditions.checkArgument(300 <= art.size, "Side of the image must be at least 300 pixels");
			Preconditions.checkArgument(1d / 10d <= art.fraction && art.fraction <= 10d, "Diameter of the circle as a fraction must be between 1/10 and 10");
			Preconditions.checkArgument(3 <= art.segments, "There must be at least 3 segments on the circle");
			Preconditions.checkArgument(1 <= art.points, "There must be at least 1 point in each segment");
			Preconditions.checkArgument(0 <= art.border, "The border thickness must not be negative");
			parsing.step();
		} catch (final CmdLineException | IllegalArgumentException exception) {
			System.err.println(exception.getMessage());
			if (parser != null) {
				System.err.println("Usage: java " + CLASS_NAME + parser.printExample(OptionHandlerFilter.ALL));
				parser.printUsage(System.err);
			}
			System.exit(ERROR_PARSING);
		}

		final Point2D[] points = new Point2D[art.segments * art.points];
		try (final ProgressBar computing = progress("Computing", points.length)) {
			final double center = art.size / 2d;
			final double radius = art.size * art.fraction / 2d;
			final double angle = 2d * Math.PI / art.segments;
			Point2D end = new Point2D.Double(center,
			                                 center - radius);
			for (int segment = 0; segment < art.segments; segment++) {
				final Point2D start = end;
				end = new Point2D.Double(center - radius * Math.sin(angle * (segment + 1)),
				                         center - radius * Math.cos(angle * (segment + 1)));
				final Point2D delta = new Point2D.Double((end.getX() - start.getX()) / art.points,
				                                         (end.getY() - start.getY()) / art.points);
				for (int point = 0; point < art.points; point++) {
					points[segment * art.points + point] = new Point2D.Double(start.getX() + delta.getX() * point,
					                                                          start.getY() + delta.getY() * point);
					computing.step();
				}
			}
		}

		BufferedImage image = null;
		Graphics2D graphics = null;
		try (final ProgressBar allocating = progress("Allocating", 1)) {
			image = new BufferedImage(art.size, art.size, BufferedImage.TYPE_BYTE_BINARY);
			graphics = image.createGraphics();
			graphics.setBackground(Color.WHITE);
			graphics.setColor(Color.BLACK);
			graphics.setXORMode(Color.WHITE);
			graphics.clearRect(0, 0, art.size, art.size);
			allocating.step();
		} catch (final IllegalArgumentException | NegativeArraySizeException exception) {
			System.err.println(exception.getMessage());
			System.exit(ERROR_ALLOCATING);
		}

		final int lines = art.segments * (art.points * ((art.segments - 1) * art.points - 2) + 2) / 2;
		try (final ProgressBar rendering = progress("Rendering", lines)) {
			for (int start = 0; start < points.length; start++) {
				final int next = Math.min(start + art.points, (start / art.points + 1) * art.points + 1);
				final int last = points.length - (start == 0 ? art.points : 1);
				for (int end = next; end <= last; end++) {
					Point2D first = points[start];
					Point2D second = points[end];
					final double distance = first.distance(second);
					final double side = art.size * Math.max(Math.sqrt(2d) + Math.sqrt(2d / 3d), art.fraction * (Math.sqrt(6d) + 2d) / (Math.sqrt(3d) * 2d));
					if (side > distance) {
						final double coefficient = side / distance;
						final Point2D center = new Point2D.Double((first.getX() + second.getX()) / 2d,
						                                          (first.getY() + second.getY()) / 2d);
						first = new Point2D.Double((first.getX() - center.getX()) * coefficient + center.getX(),
						                           (first.getY() - center.getY()) * coefficient + center.getY());
						second = new Point2D.Double((second.getX() - center.getX()) * coefficient + center.getX(),
						                            (second.getY() - center.getY()) * coefficient + center.getY());
					}
					final Point2D third = new Point2D.Double((first.getX() + second.getX() + Math.sqrt(3d) * (first.getY() - second.getY())) / 2d,
					                                         (first.getY() + second.getY() + Math.sqrt(3d) * (second.getX() - first.getX())) / 2d);
					final Polygon triangle = new Polygon(new int[]{(int)first.getX(), (int)second.getX(), (int)third.getX()},
					                                     new int[]{(int)first.getY(), (int)second.getY(), (int)third.getY()}, 3);
					graphics.fillPolygon(triangle);
					rendering.step();
				}
			}
		}

		if (art.clip) {
			try (final ProgressBar clipping = progress("Clipping", 1)) {
				final double diameter = art.size * art.fraction;
				final double margin = (art.size - diameter) / 2d;
				final Area clip = new Area(new Rectangle(0, 0, art.size, art.size));
				final Area circle = new Area(new Ellipse2D.Double(margin, margin, diameter, diameter));
				clip.subtract(circle);
				graphics.setColor(Color.WHITE);
				graphics.setPaintMode();
				graphics.fill(clip);
				clipping.step();
			}
		}

		if (art.border > 0) {
			try (final ProgressBar bordering = progress("Bordering", 1)) {
				final double diameter = art.size * art.fraction;
				final double margin = (art.size - diameter) / 2d;
				final Area border = new Area(new Ellipse2D.Double(margin - art.border, margin - art.border,
				                                                  diameter + art.border * 2d, diameter + art.border * 2d));
				final Area circle = new Area(new Ellipse2D.Double(margin, margin, diameter, diameter));
				border.subtract(circle);
				graphics.setColor(Color.BLACK);
				graphics.setPaintMode();
				graphics.fill(border);
				bordering.step();
			}
		}

		try (final ProgressBar saving = progress("Saving", 1)) {
			final String format = Files.getFileExtension(art.file);
			final boolean saved = ImageIO.write(image, format, new File(art.file));
			if (!saved) {
				throw new IllegalArgumentException("No appropriate writer was found");
			}
			saving.step();
		} catch (final IllegalArgumentException | IOException exception) {
			System.err.println(exception.getMessage());
			System.exit(ERROR_SAVING);
		}
	}
}
