package com.anuki.book_lovers_app.web_client;

import com.anuki.book_lovers_app.model.Book;
import com.anuki.book_lovers_app.model.Comment;
import com.anuki.book_lovers_app.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface WebServiceApi {

    /*
    USUARIOS
     */
    @POST("book-lovers/auth/sign-up")
    Call<Void> registrarUsuario(@Body User user);

    @POST("book-lovers/auth/sign-in")
    Call<User> login(@Body User user);


    /*
    LIBROS
     */
    @POST("/book-lovers/books")
    Call<Book> createBook(@Body Book book, @Header("Authorization")String authHeader);

    @GET("/book-lovers/books")
    Call<List<Book>> getAllBook(@Header("Authorization")String authHeader);

    @GET("/book-lovers/books/{id}")
    Call<List<Comment>> getAllCommentsByBookId(@Header("Authorization")String authHeader, @Path("id") Integer id );
}
