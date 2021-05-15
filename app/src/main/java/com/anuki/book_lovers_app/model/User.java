package com.anuki.book_lovers_app.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    private Integer id;
    @SerializedName("username")
    private String nombre;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("accessToken")
    private String token;

    public User(){

    }

    public User(Integer id, String nombre, String email, String token) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
