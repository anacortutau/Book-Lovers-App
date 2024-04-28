package com.anuki.book_lovers_app.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anuki.book_lovers_app.model.User;
import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.service.UserService;
import com.anuki.book_lovers_app.ui.menu.MenuActivity;
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

    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();
        configureListeners();

        userService = new UserService(this);
    }

    private void initializeViews(){
        etUserName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        btLogin = findViewById(R.id.btLogin);
        tvSignUp = findViewById(R.id.tvSignUp);
    }

    private void configureListeners(){
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

        User user = new User();
        user.setNombre(userName);
        user.setPassword(password);
        login(user);
    }

    private void login(User user){
        userService.login(user, new UserService.UserLoginCallback() {
            @Override
            public void onSuccess(User user) {
                saveUserAndToken(user);
                Toast.makeText(LoginActivity.this, "Usuario logado correctamente", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            }

            @Override
            public void onError(String message) {
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
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