package com.semchyshyn.opart.common;

import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarStyle;
import org.jspecify.annotations.NonNull;

@SuppressWarnings("unused")
public class Progress implements AutoCloseable {
	protected final @NonNull ProgressBar progress;

	public Progress(final @NonNull String title,
	                final int frames) {
		progress = new ProgressBarBuilder().setTaskName(title)
		                                   .setInitialMax(frames)
		                                   .setStyle(ProgressBarStyle.ASCII)
		                                   .setUpdateIntervalMillis(50)
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
