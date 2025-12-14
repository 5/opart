package com.semchyshyn.opart.animated;

import java.util.List;

public enum VCodec implements Codec {
	FFV1("ffv1"),
	H264("libx264", List.of());

	public VCodec() {
	}

}
