package com.semchyshyn.opart.common;

import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarStyle;
import org.jspecify.annotations.NonNull;

@SuppressWarnings("unused")
public class Progress implements AutoCloseable {
	protected final @NonNull ProgressBar progress;

	public Progress(final @NonNull String name,
	                final int steps) {
		progress = new ProgressBarBuilder().setTaskName(name)
		                                   .setInitialMax(steps)
		                                   .hideEta()
		                                   .setStyle(ProgressBarStyle.ASCII)
		                                   .setMaxRenderedLength(120)
		                                   .setUpdateIntervalMillis(50)
		                                   .continuousUpdate()
		                                   .build();
	}

	public void report(final int steps) {
		progress.stepBy(steps);
	}

	public void report() {
		progress.step();
	}

	@Override
	public void close() {
		progress.close();
	}
}
