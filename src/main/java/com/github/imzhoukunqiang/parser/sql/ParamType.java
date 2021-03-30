package com.github.imzhoukunqiang.parser.sql;

/**
 * Created by zkq on 2018/1/9 17:17.
 */
public interface ParamType {
    String STRING = "(String)";
    String INTEGER = "(Integer)";
    String TIMESTAMP = "(Timestamp)";
    String DATE = "(Date)";
    String TIME = "(Time)";
    String LONG = "(Long)";
    String NULL = "(null)";
}