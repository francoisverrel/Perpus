package com.example.perpus.model;

public class Book {
    private String judul;
    private String penulis;
    private Integer id;
    private Integer kategori_id;
    private Integer stok;

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKategori_id() {
        return kategori_id;
    }

    public void setKategori_id(Integer kategori_id) {
        this.kategori_id = kategori_id;
    }

    public Integer getStok() {
        return stok;
    }

    public void setStok(Integer stok) {
        this.stok = stok;
    }

    public Book(){}
}
