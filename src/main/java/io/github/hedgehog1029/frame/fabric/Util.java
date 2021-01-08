package io.github.hedgehog1029.frame.fabric;

import java.util.LinkedList;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {
	public static <T> T reduceRight(Stream<T> stream, BiFunction<T, T, T> reducer) {
		LinkedList<T> elements = stream.collect(Collectors.toCollection(LinkedList::new));

		T acc = elements.removeLast();
		while (!elements.isEmpty()) {
			T cursor = elements.removeLast();
			acc = reducer.apply(cursor, acc);
		}

		return acc;
	}
}
