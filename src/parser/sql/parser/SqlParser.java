package parser.sql.parser;

import org.apache.commons.lang.StringUtils;
import parser.sql.ParseResult;
import parser.utils.MessageUtil;

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
    private static Pattern compile = Pattern.compile("\\?");
    public static final SqlParser EMPTY_PARSER = new SqlParser();

    /**
     * 转换sql * @return
     */
    public ParseResult parse() {
        String sql = Optional.ofNullable(parseSql()).orElse("");
        Object[] params = Optional.ofNullable(ParamParser.getParams(param)).orElse(new String[]{});
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
        return StringUtils.isNotBlank(param) && StringUtils.isNotBlank(nativeSql);
    }

    /**
     * 判断是否有原sql * @return
     */
    public boolean hasNativeSql() {
        return StringUtils.isNotBlank(nativeSql);
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
        Matcher matcher = compile.matcher(resultSql);
        while (matcher.find()) {
            resultSql = matcher.replaceFirst("{" + paramCount + "}");
            matcher = compile.matcher(resultSql);
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