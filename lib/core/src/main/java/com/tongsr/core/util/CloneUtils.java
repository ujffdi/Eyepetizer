package com.tongsr.core.util;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author: Blankj
 *     blog  : <a href="http://blankj.com">http://blankj.com</a>
 *     time  : 2018/01/30
 *     desc  : utils about clone
 * </pre>
 */
public final class CloneUtils {

    private CloneUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Deep clone.
     *
     * @param data The data.
     * @param type The type.
     * @param <T>  The value type.
     * @return The object of cloned.
     */
    public static <T> T deepClone(final T data, final Type type) {
        try {
            return UtilsBridge.fromJson(UtilsBridge.toJson(data), type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
