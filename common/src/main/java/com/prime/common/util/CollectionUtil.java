package com.prime.common.util;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionUtil {
    private CollectionUtil() {
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(byte[] collection) {
        return collection == null || collection.length == 0;
    }

    public static boolean isEmpty(Object[] collection) {
        return collection == null || collection.length == 0;
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    public static <T> List<T> removeDuplicateInList(List<T> retList) {
        Set<T> set = new HashSet<>(retList);
        retList = new ArrayList<>(set);
        return retList;
    }

    @SafeVarargs
    public static <T> Set<T> toSet(T... objects) {
        List<T> list = Arrays.asList(objects);

        return new HashSet<>(list);
    }

    @SafeVarargs
    public static <T> T[] concatenate2Arrays(T[] array1, T... array2) {
        List<T> result = new ArrayList<>();
        result.addAll(Arrays.asList(array1));
        result.addAll(Arrays.asList(array2));

        return result.toArray(array1);
    }

    public static <K, V> String toLog(Map<K, V> map) {
        // using StringBuffer instead of String because expect there are many append operation
        StringBuilder sb = new StringBuilder();

        if (map == null) {
            return null;
        }
        if (map.isEmpty()) {
            return map.toString();
        }

        sb.append("{");

        for (Iterator<K> iterator = map.keySet().iterator(); iterator.hasNext(); ) {
            K key = iterator.next();
            Object value = map.get(key);
            sb.append(key).append("=");
            sb.append(toString4Log(value));
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }

        sb.append("}");

        return sb.toString();
    }

    public static <E> String toLog(Collection<E> collec) {
        // using StringBuffer instead of String because expect there are many append operation
        StringBuilder sb = new StringBuilder();

        if (collec == null) {
            return null;
        }
        if (collec.isEmpty()) {
            return collec.toString();
        }
        sb.append("[");
        for (Iterator<E> iterator = collec.iterator(); iterator.hasNext(); ) {
            E value = iterator.next();
            sb.append(toString4Log(value));
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    private static String toString4Log(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof String) {
            return (String) value;
        } else if (value instanceof Short) {
            return "" + value;
        } else if (value instanceof Integer) {
            return "" + value;
        } else if (value instanceof Long) {
            return "" + value;
        } else if (value instanceof Float) {
            return "" + value;
        } else if (value instanceof Double) {
            return "" + value;
        } else if (value instanceof Object[]) {
            Object[] objs = (Object[]) value;
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (int i = 0; i < objs.length; i++) {
                sb.append(toString4Log(objs[i]));

                if (i != objs.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            return sb.toString();
        } else
            return value.toString();
    }

    public static <T> boolean areAllUnique(List<T> list) {
        return list.stream().allMatch(new HashSet<>()::add);
    }

    public static boolean isNotEmpty(Object[] args) {

        return args != null && args.length != 0;
    }
}
