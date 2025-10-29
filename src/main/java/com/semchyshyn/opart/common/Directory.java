package com.semchyshyn.opart.common;

import com.google.common.io.MoreFiles;
import java.nio.file.Files;
import java.nio.file.Path;
import org.jspecify.annotations.NonNull;

@SuppressWarnings("ClassCanBeRecord")
public class Directory implements AutoCloseable {
	protected final @NonNull Path directory;

	public Directory(final @NonNull Path directory) {
		this.directory = directory;

		Uncheck.run(() -> Files.createDirectories(directory));
	}

	@Override
	public void close() {
		Uncheck.run(() -> MoreFiles.deleteRecursively(directory));
	}
}
