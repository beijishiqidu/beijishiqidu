package personal.blog.bean;

/**
 * 页面中间的导航面包屑.
 * 
 * @author Jack Mao
 * 
 */

public class LocationIndex {

    /**
     * 是否是根节点.
     */
    private LocationIndex nextNodex;

    /**
     * 节点名称.
     */
    private String indexName;

    /**
     * 超链接.
     */
    private String href;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public LocationIndex getNextNodex() {
        return nextNodex;
    }

    public void setNextNodex(LocationIndex nextNodex) {
        this.nextNodex = nextNodex;
    }
}
