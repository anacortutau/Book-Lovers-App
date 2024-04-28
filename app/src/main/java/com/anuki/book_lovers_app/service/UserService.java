package com.anuki.book_lovers_app.service;

import android.content.Context;

import com.anuki.book_lovers_app.model.Book;
import com.anuki.book_lovers_app.model.User;
import com.anuki.book_lovers_app.web_client.WebService;
import com.anuki.book_lovers_app.web_client.WebServiceApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {

    private final WebServiceApi webServiceApi;

    public UserService(Context context) {
        webServiceApi = WebService.getInstance(context).createService(WebServiceApi.class);
    }

    public void login(User user, UserLoginCallback callback) {
        Call<User> call = webServiceApi.login(user);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User loggedInUser = response.body();
                    if (loggedInUser != null) {
                        callback.onSuccess(loggedInUser);
                    } else {
                        callback.onError("Error desconocido al iniciar sesión");
                    }
                } else {
                    if (response.code() == 404 || response.code() == 403) {
                        callback.onError("El usuario o contraseña son incorrectos");
                    } else {
                        callback.onError("Error desconocido al iniciar sesión");
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onError("Error de red: " + t.getMessage());
            }
        });
    }

    public interface UserLoginCallback {
        void onSuccess(User user);

        void onError(String message);
    }
}