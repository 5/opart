package com.semchyshyn.opart.animated;

import java.util.Collections;
import java.util.List;
import org.jspecify.annotations.NonNull;

@SuppressWarnings({"ProtectedMemberInFinalClass", "UnnecessaryModifier"})
public abstract interface Codec {
	public static enum Type {
		AUDIO("a"),
		VIDEO("v");

		protected final @NonNull String specifier;

		private Type(final @NonNull String specifier) {
			this.specifier = specifier;
		}

		public @NonNull String specifier() {
			return specifier;
		}
	}

	public abstract @NonNull Type type();

	public abstract @NonNull String codec();

	public abstract @NonNull String[] parameters();

	public default void configure(final @NonNull List<String> command) {
		final String[] parameters = parameters();
		final String[] flags = new String[parameters.length + 2];
		flags[0] = "codec";
		flags[1] = codec();
		System.arraycopy(parameters, 0, flags, 2, parameters.length);

		final String specifier = type().specifier();
		for (int index = 0; index < flags.length; index += 2) {
			flags[index] = "-" + flags[index] + ":" + specifier;
		}

		Collections.addAll(command, flags);
	}
}
