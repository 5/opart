package com.semchyshyn.opart.still;

import com.google.common.base.Preconditions;
import com.google.common.io.Files;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
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
public final class OpArtTwo {
	private static final String CLASS_NAME = OpArtTwo.class.getSimpleName();
	private static final int DEFAULT_SIZE = 12 * 600;
	private static final double DEFAULT_FRACTION = Math.sqrt(2d / Math.PI);
	private static final int DEFAULT_WAVES = 16;
	private static final int DEFAULT_TIMES = 2;
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
	@Option(name = "--waves", metaVar = "WAVES", usage = "Number of waves to draw on the circle")
	private int waves = DEFAULT_WAVES;
	@Option(name = "--times", metaVar = "TIMES", usage = "Number of times to rotate the waves")
	private int times = DEFAULT_TIMES;
	@Option(name = "--clip", metaVar = "CLIP", usage = "Whether to clip lines outside of the circle", handler = ExplicitBooleanOptionHandler.class)
	private boolean clip = DEFAULT_CLIP;
	@Option(name = "--border", metaVar = "BORDER", usage = "Thickness of the border to draw outside the circle")
	private int border = DEFAULT_BORDER;
	@Option(name = "--file", metaVar = "FILE", usage = "Name of the file to save the image in")
	private String file = CLASS_NAME + "." + DEFAULT_FORMAT;

	private OpArtTwo() {
	}

	@SuppressWarnings("deprecation")
	private static ProgressBar progress(final String task, final int steps) {
		return new ProgressBar(task, steps, 1, false, false, System.err, ProgressBarStyle.ASCII, "", 1L, false, null, null, 0L, Duration.ZERO);
	}

	@SuppressWarnings("UnnecessaryModifier")
	public static void main(final String... arguments) {
		System.err.println(CLASS_NAME + " (c) 2025 Yuriy Semchyshyn");

		OpArtTwo art = null;
		CmdLineParser parser = null;
		try (final ProgressBar parsing = progress("Parsing", 1)) {
			art = new OpArtTwo();
			final ParserProperties properties = ParserProperties.defaults().withUsageWidth(0xFFFF);
			parser = new CmdLineParser(art, properties);
			parser.parseArgument(arguments);
			Preconditions.checkArgument(300 <= art.size, "Side of the image must be at least 300 pixels");
			Preconditions.checkArgument(1d / 10d <= art.fraction && art.fraction <= 10d, "Diameter of the circle as a fraction must be between 1/10 and 10");
			Preconditions.checkArgument(1 <= art.waves, "At least 1 wave must be drawn on the circle");
			Preconditions.checkArgument(1 <= art.times, "The waves must be rotated at least 1 time");
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

		try (final ProgressBar rendering = progress("Rendering", (art.waves + 2) * art.times)) {
			final double center = art.size / 2d;
			final double diameter = art.size * art.fraction;
			final int circles = !art.clip ? (int)Math.ceil(Math.sqrt(2d) / art.fraction) * 2 + 1 : 1;
			final double angle = Math.PI / art.times;
			for (int wave = 0; wave <= art.waves + 1; wave++) {
				final double left = diameter * wave / (art.waves + 1);
				final double right = diameter - left;
				final Area area = new Area(new Rectangle2D.Double(0d, 0d, diameter * circles, diameter * circles / 2d));
				for (int circle = 0; circle < circles; circle++) {
					if (wave > 0) {
						area.add(new Area(new Ellipse2D.Double(diameter * circle, -left / 2d, left, left)));
					}
					if (wave < art.waves + 1) {
						area.subtract(new Area(new Ellipse2D.Double(diameter * circle + left, -right / 2d, right, right)));
					}
				}
				area.transform(AffineTransform.getTranslateInstance(center - diameter * circles / 2d, center));
				for (int time = 0; time < art.times; time++) {
					if (time > 0) {
						area.transform(AffineTransform.getRotateInstance(angle, center, center));
					}
					graphics.fill(area);
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
