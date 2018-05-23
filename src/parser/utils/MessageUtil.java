package parser.utils;

/**
 * Project sql-parser
 * Created by zkq on 18-5-14 下午9:54.
 */
public class MessageUtil {

    public static String messageFormatEscape(String string) {
        return string
                .replace("'", "''")
                .replace("{", "'{'")
                .replace("}", "'}'");
    }
}
