package org.incava.xumoqi.utils;

import java.util.ArrayList;
import java.util.TreeMap;

public class MapUtil {
    public static <K, V> void putMultiTreeMap(TreeMap<K, ArrayList<V>> map, K key, V value) {
    	ArrayList<V> current = map.get(key);
   		if (current == null) {
			current = new ArrayList<V>();
			map.put(key, current);
		}
    	current.add(value);
    }
}
