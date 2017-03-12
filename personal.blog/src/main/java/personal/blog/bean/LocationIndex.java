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
    private boolean root;
    
    /**
     * 节点名称.
     */
    private String indexName;
    
    /**
     * 是否有下层节点.
     */
    private boolean next;
    
    /**
     * 超链接.
     */
    private String href;

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public boolean hasNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
