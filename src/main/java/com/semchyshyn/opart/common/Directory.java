package com.semchyshyn.opart.common;

import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import org.jspecify.annotations.NonNull;

@SuppressWarnings("ClassCanBeRecord")
public class Directory implements AutoCloseable {
	protected final @NonNull Path directory;

	public Directory(@NonNull Path directory) {
		this.directory = directory.normalize();

		Uncheck.run(() -> Files.createDirectories(this.directory));
	}

	public @NonNull Path toPath() {
		return directory;
	}

	public @NonNull File toFile() {
		return directory.toFile();
	}

	@Override
	public @NonNull String toString() {
		return directory.toString();
	}

	@Override
	public void close() {
		Uncheck.run(() -> MoreFiles.deleteRecursively(directory, RecursiveDeleteOption.ALLOW_INSECURE));
	}
}
