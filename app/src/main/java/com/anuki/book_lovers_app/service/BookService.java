package com.anuki.book_lovers_app.service;

import android.content.Context;
import android.util.Log;

import com.anuki.book_lovers_app.model.Book;
import com.anuki.book_lovers_app.model.Comment;
import com.anuki.book_lovers_app.web_client.WebService;
import com.anuki.book_lovers_app.web_client.WebServiceApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookService {

    private final WebServiceApi webServiceApi;

    public BookService(Context context) {
        webServiceApi = WebService.getInstance(context).createService(WebServiceApi.class);
    }

    public void getAllBooks(BookCallback callback) {
        Call<List<Book>> call = webServiceApi.getAllBooks();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    List<Book> books = response.body();
                    callback.onSuccess(books);
                } else {
                    callback.onError("Error obteniendo la lista de libros");
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                callback.onError("Error de red: " + t.getMessage());
            }
        });
    }

    public void createBook(Book book, BookCreateCallback callback) {
        Call<Book> call = webServiceApi.createBook(book);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG1", "Libro creado " + " id " + response.body().getId()
                            + " email: " + response.body().getTitle());
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al crear el libro");
                    Log.d("TAG1", "Error Desconocido");
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Log.d("TAG1", "Error Desconocido");
                callback.onError("Error al crear el libro: " + t.getMessage());
            }
        });
    }

    public void createCommentBook(Comment comment, int bookId, CommentCreateCallback callback) {
        Call<Comment> call = webServiceApi.createCommentBook(comment, bookId);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al crear el comentario");
                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                callback.onError("Error de red: " + t.getMessage());
            }
        });
    }

    public void getAllCommentsByBookId(int bookId, CommentCallback callback) {
        Call<List<Comment>> call = webServiceApi.getAllCommentsByBookId(bookId);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener comentarios");
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                callback.onError("Error de red: " + t.getMessage());
            }
        });
    }

    public interface BookCallback {
        void onSuccess(List<Book> books);
        void onError(String message);
    }

    public interface BookCreateCallback {
        void onSuccess(Book book);
        void onError(String message);
    }

    public interface CommentCreateCallback {
        void onSuccess(Comment comment);
        void onError(String message);
    }

    public interface CommentCallback {
        void onSuccess(List<Comment> comments);
        void onError(String message);
    }
}
