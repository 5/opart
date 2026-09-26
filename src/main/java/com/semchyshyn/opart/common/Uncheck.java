package com.semchyshyn.opart.common;

import com.google.common.base.Throwables;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@SuppressWarnings("UnnecessaryModifier")
public abstract class Uncheck {
	@FunctionalInterface
	public static abstract interface Runnable {
		public abstract void run() throws Exception;
	}

	@FunctionalInterface
	public static abstract interface Callable<Class> {
		@Nullable
		public abstract Class call() throws Exception;
	}

	@SuppressWarnings("deprecation")
	public static void run(final @NonNull Runnable runnable) {
		try {
			runnable.run();

		} catch (final Exception exception) {
			Throwables.propagate(exception);
		}
	}

	@Nullable
	public static <Class> Class call(final @NonNull Callable<Class> callable) {
		try {
			return callable.call();

		} catch (final Exception exception) {
			Throwables.throwIfUnchecked(exception);
			throw new RuntimeException(exception);
		}
	}
}
