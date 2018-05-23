package parser.sql;

/**
 * Created by zkq on 2018/1/9 16:43.
 */
public class Param {
    private String value;
    private String type;

    public Param() {
    }

    public Param(String value, String type) {
        this.value = value;
        this.type = type;
    }

    public static Param getInstance(String value, String type) {
        if (type == null || value == null) {
            return new Param(value, type);
        } else if (type.equals("null")) {
            return new Param("null", ParamType.NULL);
        } else {
            return new Param(value, type);
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"value\":\"").append(value).append('\"');
        sb.append(",\"type\":\"").append(type).append('\"');
        sb.append('}');
        return sb.toString();
    }
}