package personal.blog.vo;

public class Article {

    private String title;

    private String content;
    
    private String typeStr;

    private long createDate;

    private long updateDate;
    
    private long scanTimes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public long getScanTimes() {
        return scanTimes;
    }

    public void setScanTimes(long scanTimes) {
        this.scanTimes = scanTimes;
    }
}
