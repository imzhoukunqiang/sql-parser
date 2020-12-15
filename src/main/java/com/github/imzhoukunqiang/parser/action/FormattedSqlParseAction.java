package com.github.imzhoukunqiang.parser.action;

/**
 * Date:2020/12/15 10:02
 *
 * @author zhoukq
 */
public class FormattedSqlParseAction extends SqlParseAction {
    public FormattedSqlParseAction() {
        super();
        super.beautify = true;
    }
}