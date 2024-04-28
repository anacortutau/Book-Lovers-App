package com.anuki.book_lovers_app.ui.chapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.model.Chapter;
import com.anuki.book_lovers_app.model.Comic;
import com.anuki.book_lovers_app.model.Comment;
import com.anuki.book_lovers_app.service.ComicService;
import com.anuki.book_lovers_app.shared.SharedResources;
import com.anuki.book_lovers_app.ui.comics.ComicDetailsActivity;
import com.anuki.book_lovers_app.ui.comics.CreateComicCommentActivity;
import com.anuki.book_lovers_app.ui.login.LoginActivity;
import com.anuki.book_lovers_app.ui.menu.MenuActivity;
import com.anuki.book_lovers_app.web_client.WebService;
import com.anuki.book_lovers_app.web_client.WebServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateChapterActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etNumber;
    private EditText etSinopsis;
    private Button btCreate;
    private Button btCancel;
    private Button btMenu;
    private TextView tvLogout;
    private Chapter chapter;
    private Comic comic;
    private ComicService comicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chapter);

        setUpView();
    }

    private void setUpView() {
        initializeViews();
        Context context = this;
        WebServiceApi webServiceApi = WebService.getInstance(context).createService(WebServiceApi.class);
        comicService = new ComicService(webServiceApi);
        configureButtons();
    }

    private void initializeViews() {
        etTitle = findViewById(R.id.etTitle);
        etNumber = findViewById(R.id.etNumber);
        etSinopsis = findViewById(R.id.etSinopsis);
        btCreate = findViewById(R.id.btCreateChapter);
        btCancel = findViewById(R.id.btCancel);
        btMenu = findViewById(R.id.btMenu);
        tvLogout = findViewById(R.id.tvLogout);
    }


    private void configureButtons() {
        btCreate.setOnClickListener(v -> createChapterCheck());
        btCancel.setOnClickListener(v -> cleanForm());
        btMenu.setOnClickListener(v -> openMenuActivity());
        tvLogout.setOnClickListener(v -> logOut());
    }

    private void cleanForm() {
        etTitle.setText(null);
        etNumber.setText(null);
        etSinopsis.setText(null);
        etTitle.requestFocus();
    }

    private void createChapterCheck(){
        String title = etTitle.getText().toString().trim();
        String number = etNumber.getText().toString().trim();
        String sinopsis = etSinopsis.getText().toString().trim();

        if(title.isEmpty()){
            etTitle.setError(getResources().getString(R.string.title_error));
            etTitle.requestFocus();
            return;
        }

        if(number.isEmpty()){
            etNumber.setError(getResources().getString(R.string.author_error));
            etNumber.requestFocus();
            return;
        }

        if(sinopsis.isEmpty()){
            etSinopsis.setError(getResources().getString(R.string.sinopsis_error));
            etSinopsis.requestFocus();
            return;
        }

        chapter = new Chapter();
        chapter.setNumber(Integer.valueOf(number));
        chapter.setSinopsis(sinopsis);
        chapter.setTitle(title);
        createChapter();
    }

    private void createChapter() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            comic = (Comic) intent.getSerializableExtra("comic");
            if (comic != null) {
                comicService.createChapter(chapter, comic.getId(), new ComicService.ChapterCreateCallback() {
                    @Override
                    public void onSuccess(Chapter responseChapter) {
                        Toast.makeText(CreateChapterActivity.this, "El capitulo se ha creado correctamente", Toast.LENGTH_LONG).show();
                        Log.d("TAG1", "capitulo creado " + " id " + responseChapter.getId()
                                + " titulo: " + responseChapter.getTitle());
                        startActivity(new Intent(getApplicationContext(), ComicDetailsActivity.class).putExtra("comic", comic));
                    }

                    @Override
                    public void onError(String message) {
                        Log.d("TAG1", "Error al crear el capitulo: " + message);
                    }
                });
            }
        }
    }

    private void openMenuActivity() {
        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
    }

    private void logOut() {
        SharedResources.getInstance(getApplicationContext()).logOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
