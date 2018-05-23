package parser.sql.parser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import parser.utils.StringUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zkq on 2018/1/11 18:05.
 */
public class SqlParserBuilder {
    private SqlParserBuilder() {
    }

    private static final Pattern SQL_PREFIX = Pattern.compile("==>( *)Preparing:");
    private static final Pattern PARAM_PREFIX = Pattern.compile("==>( *)Parameters:");

    @NotNull
    public static SqlParser getParser(@Nullable String s) {
        Optional<List<String>> strings = Optional.ofNullable(s)
                .map(s1 -> s1.split("\n"))
                .filter(strs -> strs.length > 0)
                .map(Arrays::asList);
        return getSqlParser(strings.orElse(Collections.emptyList()));
    }

    private static SqlParser getSqlParser(List<String> strings) {
        SqlParser sqlParser = new SqlParser();
        for (int i = 0; i < strings.size(); i++) {
            String string = strings.get(i);
            Matcher matcher = SQL_PREFIX.matcher(string);
            if (matcher.find()) {
                sqlParser.setNativeSql(string.substring(matcher.end()));
                sqlParser.setParam(getParamStr(strings, i + 1));
                break;
            }
        }
        return sqlParser;
    }

    private static String getParamStr(List<String> strings, int start) {
        for (int i = start; i < strings.size(); i++) {
            String string = strings.get(i);
            Matcher matcher = PARAM_PREFIX.matcher(string);
            if (matcher.find()) {
                String str = string.substring(matcher.end());
                return StringUtil.removePrefix(str, " ");
            }
        }
        return null;
    }
}