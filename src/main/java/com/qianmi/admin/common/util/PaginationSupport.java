package com.qianmi.admin.common.util;

import java.util.Arrays;
import java.util.List;

/**
 * 分页
 * <pre>
 * ps.getItems()得到已分页好的结果集
 * ps.getIndexes()得到分页索引的数组
 * ps.getTotalCount()得到总结果数
 * ps.getStartIndex()当前分页索引
 * ps.getNextIndex()下一页索引
 * ps.getPreviousIndex()上一页索引
 * </pre>
 */
public class PaginationSupport<E> {


    public static final int PAGE_SIZE = 30;

    private int pageSize = PAGE_SIZE;

    private List<E> items;

    private int totalCount;

    private int[] indexes = new int[0];

    private int startIndex = 0;

    public PaginationSupport(List<E> items, int totalCount) {
        setPageSize(PAGE_SIZE);
        setTotalCount(totalCount);
        setItems(items);
        setStartIndex(0);
    }

    public PaginationSupport(List<E> items, int totalCount, int startIndex) {
        setPageSize(PAGE_SIZE);
        setTotalCount(totalCount);
        setItems(items);
        setStartIndex(startIndex);
    }

    public PaginationSupport(List<E> items, int totalCount, int pageSize, int startIndex) {
        setPageSize(pageSize);
        setTotalCount(totalCount);
        setItems(items);
        setStartIndex(startIndex);
    }

    public List<E> getItems() {
        return items;
    }

    public void setItems(List<E> items) {
        this.items = items;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        if (totalCount > 0) {
            this.totalCount = totalCount;
            int count = totalCount / pageSize;
            if (totalCount % pageSize > 0) {
                count++;
            }
            indexes = new int[count];
            for (int i = 0; i < count; i++) {
                indexes[i] = pageSize * i;
            }
        } else {
            this.totalCount = 0;
        }
    }

    public int[] getIndexes() {
        return indexes;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        if (totalCount <= 0) {
            this.startIndex = 0;
        } else if (startIndex >= totalCount) {
            this.startIndex = indexes[indexes.length - 1];
        } else if (startIndex < 0) {
            this.startIndex = 0;
        } else {
            this.startIndex = indexes[startIndex / pageSize];
        }
    }

    public int getNextIndex() {
        int nextIndex = getStartIndex() + pageSize;
        if (nextIndex >= totalCount) {
            return getStartIndex();
        } else {
            return nextIndex;
        }
    }

    public int getPreviousIndex() {
        int previousIndex = getStartIndex() - pageSize;
        if (previousIndex < 0) {
            return 0;
        } else {
            return previousIndex;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PaginationSupport{");
        sb.append("pageSize=").append(pageSize);
        sb.append(", items=").append(items);
        sb.append(", totalCount=").append(totalCount);
        sb.append(", indexes=").append(Arrays.toString(indexes));
        sb.append(", startIndex=").append(startIndex);
        sb.append('}');
        return sb.toString();
    }
}