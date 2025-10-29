package com.semchyshyn.opart.animated;

import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import org.jspecify.annotations.NonNull;

public class Progress implements AutoCloseable {
	protected final @NonNull ProgressBar progress;

	public Progress(final @NonNull String name,
	                final int maximum) {
		progress = new ProgressBarBuilder().setTaskName(name)
		                                   .setInitialMax(maximum)
		                                   .build();  // TODO: adjust settings for it to look beautiful
	}

	public void report() {
		progress.step();
	}

	@Override
	public void close() {
		progress.close();
	}
}
