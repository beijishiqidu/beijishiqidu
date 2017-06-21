package personal.blog.util;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import personal.blog.bean.LocationIndex;

public class NavigationTag extends BodyTagSupport {

    private LocationIndex index;

    /**
     * 
     */
    private static final long serialVersionUID = 7743842481289221217L;

    @Override
    public int doStartTag() throws JspException {

        if (null == index) {
            throw new JspException("面包屑为空");
        }
        StringBuilder sb = new StringBuilder();
        printNavigationStr(true, sb, index);
        try {
            pageContext.getOut().write(sb.toString());
        } catch (IOException e) {
            throw new JspException("输出面包屑抛异常了");
        }

        return SKIP_BODY;
    }

    private static void printNavigationStr(boolean first, StringBuilder hrefStr, LocationIndex index) {
        if (null != index) {
            if (!first) {
                hrefStr.append(" > ");
            }

            LocationIndex next = index.getNextNodex();
            if (next != null) {
                hrefStr.append("<a href=\"").append(index.getHref()).append("\">").append(index.getIndexName()).append("</a>");
                first = false;
                printNavigationStr(first, hrefStr, next);
            } else {
                hrefStr.append("<span>").append(index.getIndexName()).append("</span>");
            }
        }
    }

    public static void main(String[] args) {
        LocationIndex index = new LocationIndex();
        index.setHref("/");
        index.setIndexName("主页");

        LocationIndex subIndex = new LocationIndex();
        subIndex.setHref("/type");
        subIndex.setIndexName("编程语言");
        index.setNextNodex(subIndex);


        StringBuilder sb = new StringBuilder();

        printNavigationStr(true, sb, index);

        System.out.println(sb);

    }

    public LocationIndex getIndex() {
        return index;
    }

    public void setIndex(LocationIndex index) {
        this.index = index;
    }

}
