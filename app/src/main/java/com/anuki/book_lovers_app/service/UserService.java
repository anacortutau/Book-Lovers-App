package com.anuki.book_lovers_app.service;

import com.anuki.book_lovers_app.model.User;
import com.anuki.book_lovers_app.web_client.WebServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {

    private final WebServiceApi webServiceApi;

    public UserService(WebServiceApi webServiceApi) {
        this.webServiceApi = webServiceApi;
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

    public void register(User user, UserRegisterCallback callback) {
        Call<Void> call = webServiceApi.registrarUsuario(user);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()) {
                    case 201:
                        callback.onSuccess();
                        break;
                    case 409:
                        callback.onError("El usuario ya existe");
                        break;
                    default:
                        callback.onError("Error no definido: " + response.code());
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError("Error de red: " + t.getMessage());
            }
        });
    }


    public interface UserLoginCallback {
        void onSuccess(User user);

        void onError(String message);
    }

    public interface UserRegisterCallback {
        void onSuccess();
        void onError(String message);
    }
}