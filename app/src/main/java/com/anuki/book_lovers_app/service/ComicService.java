package com.anuki.book_lovers_app.service;

import android.content.Context;
import android.util.Log;

import com.anuki.book_lovers_app.model.Chapter;
import com.anuki.book_lovers_app.model.Comic;
import com.anuki.book_lovers_app.model.Comment;
import com.anuki.book_lovers_app.web_client.WebService;
import com.anuki.book_lovers_app.web_client.WebServiceApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicService {

    private final WebServiceApi webServiceApi;

    public ComicService(WebServiceApi webServiceApi) {
        this.webServiceApi = webServiceApi;
    }

    public void getAllComics(ComicCallback callback) {
        Call<List<Comic>> call = webServiceApi.getAllComics();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                if (response.isSuccessful()) {
                    List<Comic> comics = response.body();
                    callback.onSuccess(comics);
                } else {
                    callback.onError("Error obteniendo la lista de libros");
                }
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable t) {
                callback.onError("Error de red: " + t.getMessage());
            }
        });
    }

    public void getComic(int comicId, ComicDetailCallback callback) {
        Call<Comic> call = webServiceApi.getComic(comicId);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Comic> call, Response<Comic> response) {
                if (response.isSuccessful()) {
                    Comic comic = response.body();
                    callback.onSuccess(comic);
                } else {
                    callback.onError("Error obteniendo la lista de libros");
                }
            }

            @Override
            public void onFailure(Call<Comic> call, Throwable t) {
                callback.onError("Error de red: " + t.getMessage());
            }
        });
    }

    public void createComic(Comic comic, ComicService.ComicCreateCallback callback) {
        Call<Comic> call = webServiceApi.createComic(comic);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Comic> call, Response<Comic> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG1", "Comic creado " + " id " + response.body().getId()
                            + " email: " + response.body().getTitle());
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al crear el Comic");
                    Log.d("TAG1", "Error Desconocido");
                }
            }

            @Override
            public void onFailure(Call<Comic> call, Throwable t) {
                Log.d("TAG1", "Error Desconocido");
                callback.onError("Error al crear el Comic: " + t.getMessage());
            }
        });
    }

    public void createCommentComic(Comment comment, int comicId, ComicService.CommentCreateCallback callback) {
        Call<Comment> call = webServiceApi.createCommentComic(comment, comicId);
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

    public void getAllCommentsByComicId(int comicId, ComicService.CommentsCallback callback) {
        Call<List<Comment>> call = webServiceApi.getAllCommentsByComicId(comicId);
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

    public void getAllChaptersByComicId(int comicId, ComicService.ChaptersCallback callback) {
        Call<List<Chapter>> call = webServiceApi.getAllChaptersByComicId(comicId);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener comentarios");
                }
            }

            @Override
            public void onFailure(Call<List<Chapter>> call, Throwable t) {
                callback.onError("Error de red: " + t.getMessage());
            }
        });
    }

    public void createChapter(Chapter chapter, int comicId, ComicService.ChapterCreateCallback callback) {
        Call<Chapter> call = webServiceApi.createChapter(chapter, comicId);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Chapter> call, Response<Chapter> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al crear el capitulo");
                }
            }

            @Override
            public void onFailure(Call<Chapter> call, Throwable t) {
                callback.onError("Error de red: " + t.getMessage());
            }
        });
    }

    public void createCommentChapter(Comment comment, int comicId, int chapterId, ComicService.CommentCreateCallback callback) {
        Call<Comment> call = webServiceApi.createCommentChapter(comment, comicId, chapterId);
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

    public void getAllCommentsByChapterId(int chapterId, ComicService.CommentsCallback callback) {
        Call<List<Comment>> call = webServiceApi.getAllCommentsByComicId(chapterId);
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

    public interface ComicDetailCallback {
        void onSuccess(Comic comic);

        void onError(String message);
    }

    public interface ComicCallback {
        void onSuccess(List<Comic> comics);

        void onError(String message);
    }

    public interface ComicCreateCallback {
        void onSuccess(Comic comic);
        void onError(String message);
    }

    public interface CommentCreateCallback {
        void onSuccess(Comment comment);
        void onError(String message);
    }

    public interface CommentsCallback {
        void onSuccess(List<Comment> comments);
        void onError(String message);
    }

    public interface ChaptersCallback {
        void onSuccess(List<Chapter> chatpers);
        void onError(String message);
    }

    public interface ChapterCreateCallback {
        void onSuccess(Chapter chapter);
        void onError(String message);
    }

}
