package com.semchyshyn.opart.animated;

import com.semchyshyn.opart.common.Audio;
import com.semchyshyn.opart.common.Directory;
import com.semchyshyn.opart.common.Progress;
import com.semchyshyn.opart.common.Seconds;
import com.semchyshyn.opart.common.Uncheck;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public abstract class OpArt {
	protected @NonNull String title() {
		final Class<? extends OpArt> piece = getClass();

		return piece.getSimpleName();
	}

	protected @Nullable String description() {
		return null;
	}

	protected @Nullable String author() {
		return "yuriy@semchyshyn.com";
	}

	protected @Nullable Integer year() {
		final Year year = Year.now();

		return year.getValue();
	}

	protected @NonNull Resolution resolution() {
		return Resolution.PREVIEW;
	}

	protected int width() {
		final Resolution resolution = resolution();

		return resolution.width();
	}

	protected int height() {
		final Resolution resolution = resolution();

		return resolution.height();
	}

	protected @NonNull Duration duration() {
		final File audio = audio().toFile();

		return Audio.duration(audio);
	}

	protected int refresh() {
		return 10;
	}

	protected int rate() {
		return 60;
	}

	protected int frames() {
		final double seconds = Seconds.fromDuration(duration());
		final int rate = rate();

		return (int)(seconds * rate);
	}

	protected @NonNull Range horizontal() {
		final Resolution resolution = resolution();

		return resolution.horizontal();
	}

	protected @NonNull Range vertical() {
		final Resolution resolution = resolution();

		return resolution.vertical();
	}

	protected @NonNull Range temporal() {
		return Range.UNIT_INTERVAL;
	}

	protected @NonNull Path temporary() {
		return Path.of(".tmp");
	}

	protected @NonNull Directory directory() {
		final Path temporary = temporary();
		final String title = title();
		final Path directory = temporary.resolve(title);

		return new Directory(directory);
	}

	protected @NonNull String format() {
		return "png";
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

	protected @NonNull Path audio() {
		return Path.of("wav",
		               "Default.wav");
	}

	protected @NonNull String container() {
		return "mp4";
	}

	protected @NonNull Path video() {
		final Path temporary = temporary();
		final String title = title();
		final String container = container();
		final String video = title + "." + container;

		return temporary.resolve(video);
	}

	protected @NonNull VCodec vcodec() {
		return VCodec.FFV1;
	}

	protected @NonNull ACodec acodec() {
		return ACodec.COPY;
	}

	protected @NonNull Path ffmpeg() {
		return Path.of("bin",
		               "FFMPEG.exe");
	}

	protected int parallelism() {
		final Runtime runtime = Runtime.getRuntime();

		return runtime.availableProcessors() - 1;
	}

	protected @NonNull Progress progress(final int frames) {
		final int refresh = refresh();

		return new Progress("Rendering", frames, refresh);
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

		@SuppressWarnings("DataFlowIssue")
		final boolean found = Uncheck.call(() -> ImageIO.write(image, format, file));
		if (!found) {
			throw new RuntimeException("Failed to persist frame " + frame + " as " + file);
		}
	}

	protected void assemble(final @NonNull Directory directory) {
		final List<String> command = new ArrayList<>(64);

		final String ffmpeg = ffmpeg().toString();
		final String period = Double.toString(1d / refresh());
		Collections.addAll(command, ffmpeg,
		                            "-nostdin",
		                            "-hide_banner",
		                            "-loglevel", "warning",
		                            "-stats",
		                            "-stats_period", period);

		final String pattern = pattern();
		final String frames = directory.toPath().resolve(pattern).toString();
		final String rate = Integer.toString(rate());
		Collections.addAll(command, "-f",         "image2",
		                            "-framerate", rate,
		                            "-i",         frames);

		final String audio = audio().toString();
		Collections.addAll(command, "-f",              "wav",
		                            "-channel_layout", "stereo",
		                            "-stream_loop",    Integer.toString(-1),
		                            "-i",              audio);

		final String title = "title=" + title();
		Collections.addAll(command, "-metadata", title);
		final String description = description();
		if (description != null) {
			Collections.addAll(command, "-metadata", "description=" + description);
		}
		final String author = author();
		if (author != null) {
			Collections.addAll(command, "-metadata", "artist=" + author);
		}
		final Integer year = year();
		if (year != null) {
			Collections.addAll(command, "-metadata", "date=" + year);
		}

		final VCodec vcodec = vcodec();
		vcodec.configure(command);

		final ACodec acodec = acodec();
		acodec.configure(command);

		final String container = container();
		final String video = video().toString();
		Collections.addAll(command, "-shortest",
		                            "-f", container,
		                            "-y", video);

		@SuppressWarnings("DataFlowIssue")
		final int result = Uncheck.call(() -> new ProcessBuilder().command(command)
		                                                          .inheritIO()
		                                                          .start()
		                                                          .waitFor());
		if (result != 0) {
			throw new RuntimeException("Failed to assemble frames into " + video);
		}
	}

	protected void preview() {
		if (Desktop.isDesktopSupported()) {
			final Desktop desktop = Desktop.getDesktop();
			if (desktop.isSupported(Desktop.Action.OPEN)) {

				final File video = video().toFile();

				Uncheck.run(() -> desktop.open(video));
			}
		}
	}

	public void create() {
		final int parallelism = parallelism();
		final ForkJoinPool executor = ForkJoinPool.commonPool();
		executor.setParallelism(parallelism);

		final int frames = frames();

		try (final Directory directory = directory()) {
			try (final Progress progress = progress(frames)) {

				IntStream.rangeClosed(1, frames)
				         .parallel()
				         .forEach(frame -> {

					final double fraction = (frame - 1d) / (frames - 1d);
					final double time = temporal().at(fraction);

					final BufferedImage image = allocate();

					final Graphics graphics = prepare(image);

					render(graphics, time);

					persist(image, directory, frame);

					progress.report();
				});
			}

			assemble(directory);
		}

		preview();
	}
}
