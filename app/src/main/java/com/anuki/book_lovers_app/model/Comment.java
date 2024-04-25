package com.anuki.book_lovers_app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Comment implements Serializable {

    @SerializedName("id")
    private Integer id;
    @SerializedName("title")
    private String title;
    @SerializedName("userEntity")
    private User user;
    @SerializedName("comment")
    private String comment;
    @SerializedName("note")
    private Integer note;

    public Comment() {
    }

    public Comment(Integer id, String title, User user, String comment, Integer note) {
        this.id = id;
        this.title = title;
        this.user = user;
        this.comment = comment;
        this.note = note;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }
}
