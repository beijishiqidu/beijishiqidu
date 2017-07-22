package personal.blog.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class PageSplitTag extends BodyTagSupport {

    private static final long serialVersionUID = 3970690214273325656L;

    private String action;

    private PageSplitUtil<?> pageUtil;

    private Map<String, Object> paramMap;

    private String targetId;

    public PageSplitTag() {}

    @Override
    public int doStartTag() throws JspException {
        try {
            String txt = getText().toString();
            pageContext.getOut().write(txt);
            return SKIP_BODY;
        } catch (IOException e) {
            throw new JspException("输出错误" + e.getMessage());
        }
    }

    private String getQueryParams() {
        if (null == paramMap) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        Iterator<Map.Entry<String, Object>> iterator = paramMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> obj = iterator.next();
            if (null != obj.getValue()) {
                sb.append("&").append(obj.getKey()).append("=").append(obj.getValue());
            }
        }
        return sb.toString();
    }

    private StringBuffer getText() {
        if (pageUtil == null) {
            return new StringBuffer("初始化分页失败");
        }

        // 判断action是否设置
        if (action == null || "".equals(action)) {
            action = pageUtil.getAction();
        }

        StringBuffer txt = new StringBuffer("<form action=" + action + " method=\"get\" class=\"pagination\">");
        // txt.append("共有<font color=\"red\">" + pageUtil.getTotalCount() +
        // "</font>条记录&nbsp;&nbsp;");
        // txt.append("<font color=\"red\">" + pageUtil.getPageSize() + "</font>条/页&nbsp;&nbsp;");

        // 判断是否有上一页
        if (pageUtil.isHasPreIndex()) {
            String params = "firstResult=" + pageUtil.getFirstIndex() + "&maxResults=" + pageUtil.getPageSize() + getQueryParams();
            txt.append("<span onclick=\"javascript:Common.pagination('" + action + "','" + params + "','" + targetId
                    + "')\" class=\"button\">首</span>");

            params = "firstResult=" + pageUtil.getPreIndex() * pageUtil.getPageSize() + "&maxResults=" + pageUtil.getPageSize() + getQueryParams();
            txt.append("<span onclick=\"javascript:Common.pagination('" + action + "','" + params + "','" + targetId
                    + "')\" class=\"button\">&lt;</span>");
        } else {
            txt.append("<span class=\"button\">首</span>");
            txt.append("<span class=\"button\">&lt;</span>");
        }

        // 小的分页
        int[] indexs = pageUtil.getIndexs();
        int currentIndex = pageUtil.getCurrentIndex();
        int splitCount = pageUtil.getSplitCount();
        int count = 0;
        // 每次显示从当前页开始的后splitCount页
        if (pageUtil.getLastIndex() - (currentIndex + splitCount) >= 0) {
            count = currentIndex + splitCount;
        } else {
            count = indexs.length;
        }

        int beginIndex = currentIndex - splitCount / 2 >= 0 ? currentIndex - splitCount / 2 : 0;

        int endIndex = 0;
        if (currentIndex <= 5) {
            endIndex = pageUtil.getLastIndex() - (splitCount) >= 0 ? (splitCount) : pageUtil.getLastIndex();
        } else {
            endIndex = pageUtil.getLastIndex() - (currentIndex + splitCount / 2) >= 0 ? (currentIndex + splitCount / 2) : pageUtil.getLastIndex();
        }

        if (endIndex == pageUtil.getLastIndex()) {
            beginIndex -= 10 - (endIndex - beginIndex);
        }

        if (beginIndex < 0) {
            beginIndex = 0;
        }

        int index = beginIndex;
        count = endIndex;
        for (; index <= count; index++) {
            if (currentIndex + 1 == indexs[index]) {
                txt.append("<span class=\"button\" style=\"background-color:green;color:#ffffff\">" + indexs[index] + "</span>");
            } else {
                String params =
                        "firstResult=" + (indexs[index] - 1) * pageUtil.getPageSize() + "&maxResults=" + pageUtil.getPageSize() + getQueryParams();
                txt.append("<span onclick=\"javascript:Common.pagination('" + action + "','" + params + "','" + targetId + "')\" class=\"button\">"
                        + indexs[index] + "</span>");
            }
        }

        // 判断是否还有下一页
        if (pageUtil.isHasNextIndex()) {
            String params =
                    "firstResult=" + pageUtil.getNextIndex() * pageUtil.getPageSize() + "&maxResults=" + pageUtil.getPageSize() + getQueryParams();
            txt.append("<span onclick=\"javascript:Common.pagination('" + action + "','" + params + "','" + targetId
                    + "')\" class=\"button\">&gt;</span>");

            params = "firstResult=" + (pageUtil.getLastIndex()) * pageUtil.getPageSize() + "&maxResults=" + pageUtil.getPageSize() + getQueryParams();
            txt.append("<span onclick=\"javascript:Common.pagination('" + action + "','" + params + "','" + targetId
                    + "')\" class=\"button\">末</span>");
        } else {
            txt.append("<span class=\"button\">&gt;</span>");
            txt.append("<span class=\"button\">末</span>");
        }

        txt.append("</form>");

        return txt;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public PageSplitUtil<?> getPageUtil() {
        return pageUtil;
    }

    public void setPageUtil(PageSplitUtil<?> pageUtil) {
        this.pageUtil = pageUtil;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }



}
