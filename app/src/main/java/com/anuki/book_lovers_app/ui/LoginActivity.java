package com.anuki.book_lovers_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anuki.book_lovers_app.model.User;
import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.shared.SharedResources;
import com.anuki.book_lovers_app.web_client.WebService;
import com.anuki.book_lovers_app.web_client.WebServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etPassword;
    private EditText etUserName;
    private Button btLogin;
    private TextView tvSignUp;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUpView();
    }

    private void setUpView(){
        etUserName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        btLogin = findViewById(R.id.btLogin);
        tvSignUp = findViewById(R.id.tvSignUp);

        btLogin.setOnClickListener(v -> userLogin());

        tvSignUp.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SignUpActivity.class)));
    }

    private void userLogin(){
        String userName = etUserName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(userName.isEmpty()){
            etUserName.setError(getResources().getString(R.string.name_error));
            etUserName.requestFocus();
            return;
        }

        if(password.isEmpty()){
            etPassword.setError(getResources().getString(R.string.password_error));
            etPassword.requestFocus();
            return;
        }

        if(password.length()<4){
            etPassword.setError(getResources().getString(R.string.password_error_less_than));
            etPassword.requestFocus();
            return;
        }

        user = new User();
        user.setNombre(userName);
        user.setPassword(password);
        login();
    }

    private void login(){
        Call<User> call = WebService
                .getInstance(this)
                .createService(WebServiceApi.class)
                .login(user);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    User user = response.body();
                    if (user != null) {
                        Log.d("TAG1", "Usuario logeado " + " id " + user.getId() + " email: " + user.getEmail());
                        Log.d("LoginResponse", "Token saved: " + user.getToken());
                        saveUserAndToken(user);
                        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                    }
                } else if (response.code() == 404) {
                    Toast.makeText(LoginActivity.this, "El usuario o contraseña son incorrectos", Toast.LENGTH_LONG).show();
                    Log.d("TAG1", "Usuario no existe");
                } else {
                    Log.d("TAG1", "Error Desconocido");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void saveUserAndToken(User user) {
        SharedPreferences sharedPreferences = getSharedPreferences("appPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("USER_ID", user.getId());
        editor.putString("USER_NAME", user.getNombre());
        editor.putString("USER_EMAIL", user.getEmail());
        editor.putString("TOKEN", user.getToken());
        editor.apply();
        Log.d("SharedResources", "Token saved: " + user.getToken());
    }
}