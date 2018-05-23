package parser.utils;

/**
 * Project sql-parser
 * Created by zkq on 18-5-22 下午7:03.
 */
public final class StringUtil {

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
