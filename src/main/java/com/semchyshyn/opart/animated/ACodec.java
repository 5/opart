package com.semchyshyn.opart.animated;

import org.jspecify.annotations.NonNull;

@SuppressWarnings({"ProtectedMemberInFinalClass", "UnnecessaryModifier", "unused"})
public enum ACodec implements Codec {
	COPY("copy"),

	PCM("pcm_s24le", "ar", Integer.toString(48_000)),

	FLAC("flac", "ar",                Integer.toString(48_000),
	             "compression_level", Integer.toString(12)),

	AAC("libfdk_aac", "ar",          Integer.toString(48_000),
	                  "cutoff",      Integer.toString(20_000),
	                  "b",           Integer.toString(576 * 1024),
	                  "afterburner", Integer.toString(1));

	protected final @NonNull String codec;
	protected final @NonNull String[] parameters;

	private ACodec(final @NonNull String codec,
	               final @NonNull String... parameters) {
		this.codec = codec;
		this.parameters = parameters;
	}

	@Override
	public @NonNull Type type() {
		return Type.AUDIO;
	}

	@Override
	public @NonNull String codec() {
		return codec;
	}

	@Override
	public @NonNull String[] parameters() {
		return parameters;
	}
}
