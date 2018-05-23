package parser.sql.parser;

import parser.sql.Param;
import parser.sql.ParamType;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zkq on 2018/1/9 17:22.
 */
public class ParamParser {
    private static final String REG = "(.*?)(\\(\\w+\\)|null)(, |$)";
    private static final HashSet<String> SINGLE_QUOTES_SET = new HashSet<>();

    static { /* * 需要加上引号的类型 */
        String[] strings = {ParamType.STRING, ParamType.TIMESTAMP};
        SINGLE_QUOTES_SET.addAll(Arrays.asList(strings));
    }

    public static String[] getParams(String params) {
        return Optional.ofNullable(params)
                .map(ParamParser::parseParam)
                .map(ParamParser::parseParamType)
                .orElse(new String[]{});
    }

    private static String[] parseParamType(List<Param> result) {
        String[] strings = new String[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Param param = result.get(i); //是否需要加上单引号
            strings[i] = SINGLE_QUOTES_SET.contains(param.getType()) ? "'" + param.getValue() + "'" : param.getValue();
        }
        return strings;
    }

    private static List<Param> parseParam(String string) {
        Pattern pattern = Pattern.compile(REG);
        Matcher matcher = pattern.matcher(string);
        List<Param> params = new ArrayList<>();
        while (matcher.find()) { //获取类型之前的参数
            String value = matcher.group(1);
            String type = matcher.group(2);
            params.add(Param.getInstance(value, type));
        }

        return params;
    }


}