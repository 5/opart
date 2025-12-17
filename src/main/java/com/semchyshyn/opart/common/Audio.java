package com.semchyshyn.opart.common;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.jspecify.annotations.NonNull;

public abstract class Audio {
	public static @NonNull Duration duration(final @NonNull File audio) {
		try (final AudioInputStream stream = AudioSystem.getAudioInputStream(audio)) {
			final AudioFormat format = stream.getFormat();
			final long frames = stream.getFrameLength();
			final float rate = format.getFrameRate();
			final double seconds = (double)frames / (double)rate;

			return Seconds.toDuration(seconds);

		} catch (UnsupportedAudioFileException | IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
