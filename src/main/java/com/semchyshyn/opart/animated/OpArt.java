package com.semchyshyn.opart.animated;

import com.semchyshyn.opart.common.Directory;
import com.semchyshyn.opart.common.Uncheck;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.nio.file.Path;
import java.time.Duration;
import javax.imageio.ImageIO;
import org.jspecify.annotations.NonNull;

public abstract class OpArt {
	protected @NonNull String name() {
		return getClass().getSimpleName();
	}

	protected int width() {
		return 2160;
	}

	protected int height() {
		return 2160;
	}

	protected @NonNull Duration duration() {
		return Duration.ofSeconds(15);
	}

	protected int rate() {
		return 60;
	}

	protected int frames() {
		return (int)(duration().toNanos() / 1e9d * rate());
	}

	protected @NonNull Range horizontal() {
		return new Range(-1.080d, 1.080d);
	}

	protected @NonNull Range vertical() {
		return new Range(1.080d, -1.080d);
	}

	protected @NonNull Range temporal() {
		return Range.UNIT;
	}

	protected @NonNull Progress progress() {
		return new Progress(name(), frames() + 1);
	}

	protected int parallelism() {
		final Runtime runtime = Runtime.getRuntime();
		final int processors = runtime.availableProcessors();
		return processors - 1;
	}

	protected @NonNull String temporary() {
		return ".\\.tmp";
	}

	protected @NonNull Directory directory() {
		final Path directory = Path.of(temporary(), name());
		return new Directory(directory);
	}

	protected @NonNull File file(final int frame) {
		final File directory = directory().toFile();
		final String file = String.format("%08d.%s", frame, format());
		return new File(directory, file);
	}

	protected @NonNull String format() {
		return "png";
	}

	protected @NonNull BufferedImage allocate() {
		return new BufferedImage(width(), height(), BufferedImage.TYPE_INT_RGB);  // TODO: monochrome bit-image for now, with configurability for later?
	}

	protected @NonNull Graphics prepare(final @NonNull BufferedImage image) {  // TODO: "initialize" ??
		return new Graphics(image.createGraphics(), width(), height(), horizontal(), vertical());
	}

	protected abstract void render(final Graphics graphics, final double time);

	protected void persist(final @NonNull RenderedImage image,
	                       final int frame) {
		final boolean found = Uncheck.call(() -> ImageIO.write(image, format(), file(frame)));
		if (!found) {
			throw new UnsupportedOperationException("No appropriate writer was found");
		}
	}

	protected void assemble() {
		final Runtime runtime = Runtime.getRuntime();

//		final ProcessBuilder ffmpeg = new ProcessBuilder().command(

		Process process = Uncheck.call(() -> runtime.exec(".\\bin\\ffmpeg.exe -f image2 -framerate " + rate() + " -i " + directory() + "\\%08d.png -vcodec libopenh264 -crf 20 -y " + temporary() + "\\" + name() + ".mp4"));  // TODO: processbuilder??  fix arguments !!!  get ffmpeg build with libx264 ?? (fuck GPL)

		int result = Uncheck.call(process::waitFor);  // TODO: streams have to be read or piped in order for this to return
		if (result != 0) {
			throw new RuntimeException("The exit value of the process indicates an abnormal termination");
		}
	}

	public void create() {
		try (final Directory directory = directory();
		     final Progress progress = progress()) {

			final Range temporal = temporal();
			final int frames = frames();

			for (int frame = 1; frame <= frames; frame++) {
				final double fraction = (frame - 1d) / (frames - 1d);
				final double time = temporal.at(fraction);

				final BufferedImage image = allocate();

				final Graphics graphics = prepare(image);

				render(graphics, time);

				// File file = directory.file(frame)
				persist(image, file);

				progress.report();
			}

			assemble();

			progress.report();
		}
	}
}
