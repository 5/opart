package com.semchyshyn.opart.common;

import java.text.DecimalFormat;
import java.time.temporal.ChronoUnit;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarStyle;
import org.jspecify.annotations.NonNull;

@SuppressWarnings({"LongLiteralEndingWithLowercaseL", "unused"})
public class Progress implements AutoCloseable {
	protected static final int MILLISECONDS = 1000;
	protected static final int WIDTH = 120;
	protected static final @NonNull DecimalFormat FORMAT = new DecimalFormat("##0");

	protected final @NonNull ProgressBar progress;

	public Progress(final @NonNull String name,
	                final int frames,
	                final int refresh) {
		final int update = MILLISECONDS / refresh;

		progress = new ProgressBarBuilder().setTaskName(name)
		                                   .setInitialMax(frames)

		                                   .setUnit("f", 1l)
		                                   .setSpeedUnit(ChronoUnit.SECONDS)
		                                   .showSpeed(FORMAT)
		                                   .hideEta()

		                                   .setStyle(ProgressBarStyle.ASCII)
		                                   .setMaxRenderedLength(WIDTH)

		                                   .setUpdateIntervalMillis(update)
		                                   .continuousUpdate()
		                                   .build();
	}

	public void report(final int frames) {
		progress.stepBy(frames);
	}

	public void report() {
		progress.step();
	}

	@Override
	public void close() {
		progress.close();
	}
}
