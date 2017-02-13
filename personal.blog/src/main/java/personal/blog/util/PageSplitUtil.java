package personal.blog.util;

import java.util.List;

public class PageSplitUtil<T> {

    // 存放记录集合
    private List<T> items;

    // 每页记录数
    private int pageSize = 10;

    private int[] indexs;

    private int splitCount = 10;

    // 总记录数
    // 初始化其值,为了判断缓存
    private long totalCount = -1;

    // 开始索引值
    private final int firstIndex = 0;

    // 当前页索引
    private int currentIndex;

    // 最后一页
    private int lastIndex;

    // 存放url
    private String action;

    public PageSplitUtil(List<T> items, int current, long totalCount) {
        this.items = items;
        this.totalCount = totalCount;
        this.currentIndex = current / this.pageSize;
        calculateTotalPage();
    }

    public PageSplitUtil(List<T> items, int current, int pageSize, long totalCount) {
        this.items = items;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.currentIndex = current / this.pageSize;
        calculateTotalPage();
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex < 1 ? 1 : currentIndex;
    }

    public boolean isHasNextIndex() {
        return currentIndex >= lastIndex ? false : true;
    }

    public boolean isHasPreIndex() {
        return currentIndex <= firstIndex ? false : true;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public int getLastIndex() {

        return lastIndex;
    }

    public int getNextIndex() {
        return currentIndex >= lastIndex ? lastIndex : currentIndex + 1;
    }

    public int getPreIndex() {
        return currentIndex <= firstIndex ? firstIndex : currentIndex - 1;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setSplitCount(int splitCount) {
        this.splitCount = splitCount;
    }

    // 计算总页数
    private void calculateTotalPage() {
        if (totalCount > 0) {
            lastIndex = (int) Math.ceil((double) totalCount / pageSize) - 1;
        } else {
            lastIndex = 0;
        }
    }

    public int[] getIndexs() {
        indexs = new int[lastIndex + 1];
        for (int i = 0; i < lastIndex + 1; i++) {
            indexs[i] = i + 1;
        }
        return indexs;
    }

    public int getSplitCount() {
        return splitCount;
    }



}
