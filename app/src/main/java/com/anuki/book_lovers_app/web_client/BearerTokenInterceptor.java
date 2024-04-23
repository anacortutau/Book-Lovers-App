package com.anuki.book_lovers_app.web_client;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.anuki.book_lovers_app.shared.SharedResources;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BearerTokenInterceptor implements Interceptor {
    private final SharedResources sharedResources;

    public BearerTokenInterceptor(Context context) {
        this.sharedResources = SharedResources.getInstance(context);
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        String token = sharedResources.getToken();

        Log.d("BearerTokenInterceptor", "Token: " + token);

        if (token != null && !token.isEmpty()) {
            Request.Builder builder = original.newBuilder()
                    .header("Authorization", "Bearer " + token)
                    .method(original.method(), original.body());
            Request request = builder.build();
            return chain.proceed(request);
        }

        return chain.proceed(original);
    }
}
