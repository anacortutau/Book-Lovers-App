package com.anuki.book_lovers_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.model.User;
import com.anuki.book_lovers_app.shared.SharedResources;
import com.anuki.book_lovers_app.web_client.WebService;
import com.anuki.book_lovers_app.web_client.WebServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etPassword;
    private EditText etEmail;

    private Button btSignUp;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setUpView();
    }

    private void setUpView(){
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btSignUp = findViewById(R.id.btSignUp);
        tvLogin = findViewById(R.id.tvLogin);

        btSignUp.setOnClickListener(v -> userSignUp());
        tvLogin.setOnClickListener(v -> navigateToLogin());
    }

    private void userSignUp(){
        String email = etEmail.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(!validateInputs(name, email, password)) return;

        User user = new User();
        user.setNombre(name);
        user.setEmail(email);
        user.setPassword(password);
        crearUsuario(user);
    }

    private boolean validateInputs(String name, String email, String password) {
        if(name.isEmpty()){
            etName.setError(getResources().getString(R.string.name_error));
            etName.requestFocus();
            return false;
        }

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError(getResources().getString(R.string.email_doesnt_match));
            etEmail.requestFocus();
            return false;
        }

        if(password.isEmpty() || password.length() < 4){
            etPassword.setError(getResources().getString(R.string.password_error_less_than));
            etPassword.requestFocus();
            return false;
        }
        return true;
    }

    private void crearUsuario(User user){
        Call<Void> call = WebService.getInstance(this)
                .createService(WebServiceApi.class)
                .registrarUsuario(user);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()) {
                    case 201:
                        Log.d("TAG1", "Usuario guardado correctamente");
                        navigateToLogin();
                        break;
                    case 409:
                        Log.d("TAG1", "El Usuario ya existe");
                        break;
                    default:
                        Log.d("TAG1", "Error no definido: " + response.code());
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("TAG1 Error: ", t.toString());
            }
        });
    }

    private void navigateToLogin(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish(); // Finaliza esta actividad para evitar regresar a ella con el botón atrás.
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(SharedResources.getInstance(this).isLoggedIn()){
            Log.d("TAG1", "Usuario logado, enviando a Menu Activity");
            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            finish(); // Finaliza esta actividad para evitar regresar a ella.
        }
    }
}
