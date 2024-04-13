package com.anuki.book_lovers_app.web_client;

import com.anuki.book_lovers_app.model.Book;
import com.anuki.book_lovers_app.model.Comment;
import com.anuki.book_lovers_app.model.User;
import com.anuki.book_lovers_app.model.Comic;

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

    @GET("/book-lovers/books/{id}/comments")
    Call<List<Comment>> getAllCommentsByBookId(@Header("Authorization")String authHeader, @Path("id") Integer id );

    @POST("/book-lovers/books/{id}/comments")
    Call<Comment> createCommentBook(@Body Comment comment, @Header("Authorization")String authHeader, @Path("id") Integer id );

     /*
    COMICS
     */
    @POST("/book-lovers/comics")
    Call<Comic> createComic (@Body Comic comic, @Header("Authorization") String authHeader);

    @GET("/book-lovers/comics")
    Call<List<Comic>> getAllComic(@Header("Authorization")String authHeader);

    @GET("/book-lovers/comics/{id}/comments")
    Call<List<Comment>> getAllCommentsByComicId(@Header("Authorization")String authHeader, @Path("id") Integer id );

    @POST("/book-lovers/comics/{id}/comments")
    Call<Comment> createCommentComic(@Body Comment comment, @Header("Authorization")String authHeader, @Path("id") Integer id );

}
