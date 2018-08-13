package cn.com.bluemoon.utils.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapSortUtil {
	/**
	 * 使用 Map按key进行排序
	 * 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> sortMapByKey(Map<String, Object> oriMap, boolean isReverse) {
		if (oriMap == null || oriMap.isEmpty()) {
			return null;
		}
		TreeMap<String, Object> sortedMap = new TreeMap<String, Object>(new MapKeyComparator());
		sortedMap.putAll(oriMap);
		if (isReverse) {
			return sortedMap.descendingMap();
		}
		return sortedMap;
	}

	/**
	 * 使用 Map按value进行排序
	 * 
	 * @param map
	 * @return
	 */
	public static Map<String, Integer> sortMapByValue(Map<String, Integer> oriMap, boolean isReverse) {
		if (oriMap == null || oriMap.isEmpty()) {
			return null;
		}
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		List<Map.Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(oriMap.entrySet());
		Collections.sort(entryList, new MapValueComparator());
		if (isReverse) {
			Collections.reverse(entryList);
		}

		Iterator<Map.Entry<String, Integer>> iter = entryList.iterator();
		Map.Entry<String, Integer> tmpEntry = null;
		while (iter.hasNext()) {
			tmpEntry = iter.next();
			sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
		}
		return sortedMap;
	}
}
