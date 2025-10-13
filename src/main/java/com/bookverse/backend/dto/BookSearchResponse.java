package com.bookverse.backend.dto;

public class BookSearchResponse {
    private String kind;
    private int totalItems;
    private Object items;

    public BookSearchResponse() {}

    public BookSearchResponse(String kind, int totalItems, Object items) {
        this.kind = kind;
        this.totalItems = totalItems;
        this.items = items;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public Object getItems() {
        return items;
    }

    public void setItems(Object items) {
        this.items = items;
    }
}
