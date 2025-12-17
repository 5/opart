package com.semchyshyn.opart.animated;

import org.jspecify.annotations.NonNull;

@SuppressWarnings({"ProtectedMemberInFinalClass", "UnnecessaryModifier"})
public enum VCodec implements Codec {
	FFV1("ffv1", "level",   Integer.toString(3),
	             "context", Integer.toString(1),
	             "g",       Integer.toString(1)),

	H264("libx264", "preset",  "placebo",
	                "tune",    "animation",
	                "crf",     Integer.toString(0),
	                "qp",      Integer.toString(0),
	                "g",       Integer.toString(1),
	                "pix_fmt", "yuv444p");

	protected final @NonNull String codec;
	protected final @NonNull String[] parameters;

	private VCodec(final @NonNull String codec,
	               final @NonNull String... parameters) {
		this.codec = codec;
		this.parameters = parameters;
	}

	@Override
	public @NonNull Type type() {
		return Type.VIDEO;
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
