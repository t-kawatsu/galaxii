package com.galaxii.common.util;

import java.util.Collections;
import java.util.List;

public class CollectionUtils {
	
	public static <T> List<T> detectRandom(List<T> list, int limit) {
		if(list == null) {
			return null;
		}
		List<T> _list = list.subList(0, list.size() < limit ? list.size() : limit);
		Collections.shuffle(_list);
		return CollectionUtils.subList(_list, 0, limit);
	}

	public static <T> List<T> subList(List<T> list, int fromIndex, int toIndex) {
		if(list == null || list.isEmpty()) {
			return null;
		}
		fromIndex = list.size()-1 < fromIndex ? list.size()-1 : fromIndex;
		toIndex   = list.size() < toIndex ? list.size() : toIndex;
		if(toIndex < fromIndex) {
			return null;
		}
		return list.subList(fromIndex, toIndex);
	}
}
