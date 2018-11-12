package com.palvai.hash;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DiffK {
	public int diffPossible(final List<Integer> a, int b) {
		HashSet<Integer> visited = new HashSet<Integer>();
		for (Integer number : a) {
			if (visited.contains(number + b) || visited.contains(number - b))
				return 1;
			visited.add(number);
		}
		return 0;
	}

	public int diffPossible2(final List<Integer> list, int diff) {
		Set<Long> set = new HashSet<>();
		for (Integer number : list) {
			if (set.contains(1L * number + diff) || set.contains(1L * number - diff)) {
				return 1;
			}
			set.add(Long.valueOf(number));
		}
		return 0;
	}

	public int diffPossible3(final List<Integer> a, int b) {
		for (int i = 0; i < a.size(); i++) {
			for (int j = 0; j < a.size(); j++) {
				if (i == j) {
					continue;
				}

				if (a.get(i) - a.get(j) == b) {
					return 1;
				}
			}
		}

		return 0;
	}
}
