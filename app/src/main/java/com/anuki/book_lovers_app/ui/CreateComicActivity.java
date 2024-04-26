package com.anuki.book_lovers_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.model.Comic;
import com.anuki.book_lovers_app.shared.SharedResources;
import com.anuki.book_lovers_app.web_client.WebService;
import com.anuki.book_lovers_app.web_client.WebServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateComicActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etAuthor;
    private EditText etSinopsis;
    private Button btCreate;
    private Button btCancel;
    private Button btMenu;
    private TextView tvLogut;
    private Spinner spinner;
    private Comic comic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comic);

        setUpView();
    }

    private void setUpView() {
        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        etSinopsis = findViewById(R.id.etSinopsis);
        btCreate = findViewById(R.id.btCreateComic);
        btCancel = findViewById(R.id.btCancel);
        btMenu = findViewById(R.id.btMenu);
        tvLogut = findViewById(R.id.tvLogout);
        spinner = findViewById(R.id.spinner);

        String[] themes = {"Select a Theme", "MYSTERY", "TERROR", "ADVENTURE", "ROMANCE", "HISTORY", "FANTASY"};
        ArrayAdapter <String> themesAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, themes);
        spinner.setAdapter(themesAdapter);

        btCreate.setOnClickListener(v -> createComicCheck());

        btCancel.setOnClickListener(v -> cleanForm());

        btMenu.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MenuActivity.class)));

        tvLogut.setOnClickListener(v -> {
            SharedResources.getInstance(getApplicationContext()).logOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void cleanForm() {
        etTitle.setText(null);
        etAuthor.setText(null);
        etSinopsis.setText(null);
        spinner.setSelection(0);
        etTitle.requestFocus();
    }

    private void createComicCheck(){
        String title = etTitle.getText().toString().trim();
        String author = etAuthor.getText().toString().trim();
        String sinopsis = etSinopsis.getText().toString().trim();
        String theme = spinner.getSelectedItem().toString().trim();

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

        if(theme.isEmpty() || theme.equals("Selecciona un tema")){
            Toast.makeText(this,"Debes seleccionar un tema", Toast.LENGTH_LONG).show();
            spinner.requestFocus();
            return;
        }

        comic = new Comic();
        comic.setAuthor(author);
        comic.setSinopsis(sinopsis);
        comic.setTheme(theme);
        comic.setTitle(title);
        createComic();
    }

    private void createComic() {

        Call<Comic> call = WebService
                .getInstance(this)
                .createService(WebServiceApi.class)
                .createComic(comic);

        call.enqueue(new Callback<Comic>() {
            @Override
            public void onResponse(Call<Comic> call, Response<Comic> response) {
                if(response.code() == 201){
                    Toast.makeText(CreateComicActivity.this, "El comic se ha creado correctamente", Toast.LENGTH_LONG).show();
                    Log.d("TAG1", "Comic creado " + " id " + response.body().getId()
                            + " email: " + response.body().getTitle());
                    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                }else{
                    Log.d("TAG1", "Error Desconocido");
                }
            }

            @Override
            public void onFailure(Call<Comic> call, Throwable t) {
                Log.d("TAG1", "Error Desconocido");
            }
        });

    }


    }
