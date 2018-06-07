package parser.utils;

import java.util.Objects;

/**
 * Project sql-parser
 * Created by zkq on 18-5-22 下午7:03.
 */
public final class StringUtil {

    public static String trimSuffix(String target) {
        Objects.requireNonNull(target);
        char[] chars = target.toCharArray();
        int len = chars.length;
        while ((0 < len) && (chars[len - 1] <= ' ')) {
            len--;
        }
        return len < chars.length ? new String(chars, 0, len) : target;
    }


    public static String removePrefix(String target, String prefix) {
        if (target == null || prefix == null) {
            return target;
        }

        if (target.startsWith(prefix)) {
            return target.substring(target.indexOf(prefix) + prefix.length());
        } else {
            return target;
        }


    }

    private StringUtil() {

    }

 }
