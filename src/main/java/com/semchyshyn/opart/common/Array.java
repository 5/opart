package com.semchyshyn.opart.common;

import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@SuppressWarnings("unused")
public abstract class Array {
	public static <Class> int find(final @NonNull Class[] array,
	                               final @Nullable Class value) {
		for (int index = 0; index < array.length; index++) {
			if (Objects.equals(array[index], value)) {
				return index;
			}
		}

		return -1;
	}

	public static <Class> void rotate(final @NonNull Class[] array,
	                                  int distance) {
		final int length = array.length;

		distance = (distance % length + length) % length;

		reverse(array, 0, length - 1);
		reverse(array, 0, distance - 1);
		reverse(array, distance, length - 1);
	}

	public static <Class> void reverse(final @NonNull Class[] array,
	                                   int start, int end) {
		while (start < end) {
			final Class temporary = array[start];
			array[start++] = array[end];
			array[end--] = temporary;
		}
	}

	public static <Class> void reverse(final @NonNull Class[] array) {
		final int length = array.length;

		reverse(array, 0, length - 1);
	}
}
