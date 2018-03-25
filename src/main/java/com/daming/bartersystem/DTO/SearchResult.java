package com.daming.bartersystem.DTO;

import com.daming.bartersystem.entitys.Item;
import com.github.pagehelper.PageInfo;

import java.util.List;

public class SearchResult {
    //item列表
    private List<Item> items;

    //分页信息
    private PageInfo pageInfo;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "items=" + items +
                ", pageInfo=" + pageInfo +
                '}';
    }
}
