package parser.sql;

/**
 * Created by zkq on 2018/1/22 14:51.
 */
public class ParseResult {
    private String result;
    private boolean success;

    public ParseResult() {
    }

    public ParseResult(String result, boolean success) {
        this.result = result;
        this.success = success;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"result\":\"").append(result).append('\"');
        sb.append(",\"success\":").append(success);
        sb.append('}');
        return sb.toString();
    }
}