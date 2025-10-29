package com.semchyshyn.opart.animated;

import com.semchyshyn.opart.common.Directory;
import com.semchyshyn.opart.common.Progress;
import com.semchyshyn.opart.common.Uncheck;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.imageio.ImageIO;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@SuppressWarnings("DataFlowIssue")
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
		final double duration = duration().toNanos() / 1e9d;
		final int rate = rate();

		return (int)(duration * rate);
	}

	protected @NonNull Range horizontal() {
		return new Range(-1.080d, 1.080d);
	}

	protected @NonNull Range vertical() {
		return new Range(1.080d, -1.080d);
	}

	protected @NonNull Range temporal() {
		return Range.UNIT_INTERVAL;
	}

	protected @NonNull Progress progress(final int frames) {
		final String name = name();

		return new Progress(name, frames);
	}

	protected int parallelism() {
		final Runtime runtime = Runtime.getRuntime();

		return runtime.availableProcessors() - 1;
	}

	protected @NonNull Path temporary() {
		return Path.of(".tmp");
	}

	protected @NonNull Path ffmpeg() {
		return Path.of("bin", "ffmpeg.exe");
	}

	protected @NonNull Directory directory() {
		final Path temporary = temporary();
		final String name = name();
		final Path directory = temporary.resolve(name);

		return new Directory(directory);
	}

	protected @NonNull String format() {
		return "png";
	}

	protected @NonNull String codec() {
		return "ffv1";
	}

	protected @NonNull String pattern() {
		final String format = format();

		return "%08d." + format;
	}

	protected @NonNull File file(final @NonNull Directory directory,
	                             final int frame) {
		final File parent = directory.toFile();
		final String file = String.format(pattern(), frame);

		return new File(parent, file);
	}

	protected @Nullable Path audio() {
		return Path.of("wav", "audio.wav");
	}

	protected @NonNull BufferedImage allocate() {
		final int width = width();
		final int height = height();

		return new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
	}

	protected @NonNull Graphics prepare(final @NonNull BufferedImage image) {
		final int width = width();
		final int height = height();
		final Range horizontal = horizontal();
		final Range vertical = vertical();

		return new Graphics(image, width, height, horizontal, vertical);
	}

	protected abstract void render(final @NonNull Graphics graphics,
	                               final double time);

	protected void persist(final @NonNull BufferedImage image,
	                       final @NonNull Directory directory,
	                       final int frame) {
		final String format = format();
		final File file = file(directory, frame);

		final boolean found = Uncheck.call(() -> ImageIO.write(image, format, file));
		if (!found) {
			throw new RuntimeException("Failed to persist frame " + frame + " as " + file);
		}
	}

	protected @NonNull Path video() {
		final Path temporary = temporary();
		final String video = name() + ".mp4";

		return temporary.resolve(video);
	}

	protected void assemble(final @NonNull Directory directory) {
		final List<String> command = new ArrayList<>(20);

		final String ffmpeg = ffmpeg().toString();
		Collections.addAll(command, ffmpeg,
		                            "-loglevel", "warning");

		final String frames = directory.toPath().resolve(pattern()).toString();   // TODO: this, then fix threading, also reorder methods, and focus on the Rain
		final String rate = Integer.toString(rate());
		final String codec = codec();
		Collections.addAll(command, "-f", "image2",
		                            "-i",  frames,
		                            "-framerate", rate,
		                            "-vcodec", codec);

		final Path audio = audio();
		if (audio != null) {
			Collections.addAll(command, "-stream_loop", "-1",
		                                "-i", audio.toString(),
		                                "-shortest",
		                                "-acodec", "copy");
		}

		final String video = video().toString();
		Collections.addAll(command, "-y",  video);

		int result = Uncheck.call(() -> new ProcessBuilder().command(command)
		                                                    .inheritIO()
		                                                    .start()
		                                                    .waitFor());
		if (result != 0) {
			throw new RuntimeException("Failed to assemble frames into " + video);
		}
	}

	protected @NonNull ExecutorService executor() {
		int threads = parallelism();

		return Executors.newFixedThreadPool(threads);
	}

	protected void create(Directory directory,
	                      int frame,
	                      double time,
	                      Progress progress) {
		// TODO
	}

	public void create() {
		final int frames = frames();
		final Range temporal = temporal();
		try (final Directory directory = directory()) {
			try (final Progress progress = progress(frames);
			     final ExecutorService executor = executor()) {

				for (int frame = 1; frame <= frames; frame++) {

					final int frame2 = frame;  // TODO

					executor.submit(() -> {
						final double fraction = (frame2 - 1d) / (frames - 1d);
						final double time = temporal.at(fraction);

						final BufferedImage image = allocate();

						final Graphics graphics = prepare(image);

						render(graphics, time);

						persist(image, directory, frame2);

						progress.report();
					});
				}
			}

			assemble(directory);
		}
	}
}
