package com.anuki.book_lovers_app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Chapter implements Serializable{

    @SerializedName("id")
    private Integer id;

    @SerializedName("title")
    private String title;

    @SerializedName("author")
    private String author;

    @SerializedName("drawer")
    private String drawer;

    @SerializedName("sinopsis")
    private String sinopsis;

    @SerializedName("theme")
    private String theme;

    @SerializedName("note")
    private Double note;

    public Chapter(){

    }

    public Chapter(Integer id, String title, String writter, String drawer, String sinopsis, String theme, Double note) {
        this.id = id;
        this.title = title;
        this.author = writter;
        this.drawer = drawer;
        this.sinopsis = sinopsis;
        this.theme = theme;
        this.note = note;
    }

    public Integer getId(){return id;}

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Double getNote() {
        return note;
    }

    public void setNote(Double note) {
        this.note = note;
    }

    public String getDrawer() {
        return drawer;
    }

    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }
}
