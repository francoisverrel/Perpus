package com.example.perpus.model;

public class CartItem {
    public CartItem(){}

    private Integer id;
    private Integer book_id;
    private Integer jumlah_item;
    private Integer transaction_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public Integer getJumlah_item() {
        return jumlah_item;
    }

    public void setJumlah_item(Integer jumlah_item) {
        this.jumlah_item = jumlah_item;
    }

    public Integer getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(Integer transaction_id) {
        this.transaction_id = transaction_id;
    }
}
