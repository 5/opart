package com.semchyshyn.opart.animated;

import java.util.List;
import org.jspecify.annotations.NonNull;

@SuppressWarnings("unused")
public enum ACodec implements Codec {
	COPY("copy"),
	FLAC("flac"),
	AAC("libfdk_aac", List.of());

	protected final @NonNull String[] flags;

	private ACodec(final @NonNull String codec) {
		this(codec);
	}

	private ACodec(final @NonNull String acodec,
	              final @NonNull String[] flags) {

	}

	@Override
	public @NonNull String[] flags() {
		return new String[0];
	}
}
