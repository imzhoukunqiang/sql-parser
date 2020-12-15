package com.github.imzhoukunqiang.parser.sql.parser;

import com.github.imzhoukunqiang.parser.sql.ParseResult;
import com.github.imzhoukunqiang.parser.utils.Commons;
import com.github.imzhoukunqiang.parser.utils.MessageUtil;


import java.text.MessageFormat;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zkq on 2018/1/9 17:17.
 */
public class SqlParser {
    private String nativeSql;
    private String param;
    private int paramCount;
    private static final Pattern PATTERN = Pattern.compile("\\?");
    public static final SqlParser EMPTY_PARSER = new SqlParser();

    /**
     * 转换sql * @return
     */
    public ParseResult parse() {
        String sql = Optional.ofNullable(parseSql()).orElse("");
        Object[] params = ParamParser.getParams(param);
        if (paramCount != params.length) {
            return new ParseResult(nativeSql, false);
        }
        String result = MessageFormat.format(sql, params);
        return new ParseResult(result, true);
    }

    /**
     * 判断能否转换 * @return
     */
    public boolean canParse() {
        return Commons.isNotBlank(param) && Commons.isNotBlank(nativeSql);
    }

    /**
     * 判断是否有原sql * @return
     */
    public boolean hasNativeSql() {
        return Commons.isNotBlank(nativeSql);
    }

    /**
     * 使用 {0} , {1} , {2} 代替 ? * @return 转换后的结果
     */
    private String parseSql() {
        paramCount = 0;
        if (nativeSql == null) {
            return null;
        }
        String resultSql = MessageUtil.messageFormatEscape(nativeSql);
        Matcher matcher = PATTERN.matcher(resultSql);
        while (matcher.find()) {
            resultSql = matcher.replaceFirst("{" + paramCount + "}");
            matcher = PATTERN.matcher(resultSql);
            paramCount++;
        }
        return resultSql;
    }

    public SqlParser() {
    }


    public String getNativeSql() {
        return nativeSql;
    }

    public void setNativeSql(String nativeSql) {
        this.nativeSql = nativeSql;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}