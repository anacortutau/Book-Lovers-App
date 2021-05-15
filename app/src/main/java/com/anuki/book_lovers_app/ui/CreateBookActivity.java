package com.anuki.book_lovers_app.ui;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.model.Book;
import com.anuki.book_lovers_app.shared.SharedResources;
import com.anuki.book_lovers_app.web_client.WebService;
import com.anuki.book_lovers_app.web_client.WebServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateBookActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etAuthor;
    private EditText etSinopsis;
    private EditText etTheme;
    private Button btCreate;
    private Button btCancel;
    private Button btMenu;
    private TextView tvLogut;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book);

        setUpView();
    }

    private void setUpView(){
        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        etSinopsis = findViewById(R.id.etSinopsis);
        etTheme = findViewById(R.id.etTheme);
        btCreate = findViewById(R.id.btCreateBook);
        btCancel = findViewById(R.id.btCancel);
        btMenu = findViewById(R.id.btMenu);
        tvLogut = findViewById(R.id.tvLogout);

        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createBookCheck();
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanForm();
            }
        });

        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            }
        });

        tvLogut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedResources.getInstance(getApplicationContext()).logOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void cleanForm() {
        etTitle.setText(null);
        etAuthor.setText(null);
        etSinopsis.setText(null);
        etTheme.setText(null);
        etTitle.requestFocus();
    }

    private void createBookCheck(){
        String title = etTitle.getText().toString().trim();
        String author = etAuthor.getText().toString().trim();
        String sinopsis = etSinopsis.getText().toString().trim();
        String theme = etTheme.getText().toString().trim();

        if(title.isEmpty()){
            etTitle.setError(getResources().getString(R.string.title_error));
            etTitle.requestFocus();
            return;
        }

        if(author.isEmpty()){
            etAuthor.setError(getResources().getString(R.string.author_error));
            etAuthor.requestFocus();
            return;
        }

        if(sinopsis.isEmpty()){
            etSinopsis.setError(getResources().getString(R.string.sinopsis_error));
            etSinopsis.requestFocus();
            return;
        }

        if(theme.isEmpty()){
            etTheme.setError(getResources().getString(R.string.theme_error));
            etTheme.requestFocus();
            return;
        }

        book = new Book();
        book.setAuthor(author);
        book.setSinopsis(sinopsis);
        book.setTheme(theme);
        book.setTitle(title);
        createBook();
    }

    private void createBook() {
        String token = SharedResources.getInstance(getApplicationContext()).getUser().getToken();
        String authHeader = "Bearer " + token;

        Call<Book> call = WebService
                .getInstance()
                .createService(WebServiceApi.class)
                .createBook(book, authHeader);

        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if(response.code() == 201){
                    Log.d("TAG1", "Libro creado " + " id " + response.body().getId()
                            + " email: " + response.body().getTitle());
                    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                }else{
                    Log.d("TAG1", "Error Desconocido");
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Log.d("TAG1", "Error Desconocido");
            }
        });

    }
}