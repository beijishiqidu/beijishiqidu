package personal.blog.vo;

import java.util.HashMap;
import java.util.Map;

public class ExecResult {

    private boolean result;

    private String message;

    private Map<String, Object> append = new HashMap<String, Object>();

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getAppend() {
        return append;
    }

    public void setAppend(Map<String, Object> append) {
        this.append = append;
    }
}
