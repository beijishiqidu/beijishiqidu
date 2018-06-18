package personal.blog.util;

import org.apache.commons.lang.StringUtils;

public class HtmlFilterUtil {

    public static final String filterHtml(String htmlContent) {

        if (StringUtils.isEmpty(htmlContent)) {
            return htmlContent;
        }

        return htmlContent.replaceAll("<[a-zA-Z]+[1-9]?[^><]*>", "").replaceAll("</[a-zA-Z]+[1-9]?>", "").replaceAll("&nbsp;", "");
    }

    public static final String fileterLine(String htmlContent) {
        if (StringUtils.isEmpty(htmlContent)) {
            return htmlContent;
        }

        return htmlContent.replaceAll("\r\n", "<br/>");
    }
}
