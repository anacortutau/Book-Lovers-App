package com.anuki.book_lovers_app.ui.comics;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.TextView;
import android.widget.RatingBar;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.model.Comic;
import com.anuki.book_lovers_app.ui.books.CreateBookCommentActivity;
import com.anuki.book_lovers_app.web_client.WebService;
import com.anuki.book_lovers_app.web_client.WebServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ComicDetailsActivity extends AppCompatActivity {

    TextView title;

    TextView author;

    TextView theme;

    TextView sinopsis;

    RatingBar rating;

    Comic comic;

    Button btComment;
    Button btListComment;
    Button btListChapters;
    Button btCreateChapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_details);

        initializeViews();

        configureButtons();

        getIntentData();
    }

    private void initializeViews() {
        title = findViewById(R.id.titleText);
        author = findViewById(R.id.authorText);
        sinopsis = findViewById(R.id.sinopsisText);
        theme = findViewById(R.id.themeText);
        rating = findViewById(R.id.ratingStars);
        btComment = findViewById(R.id.btComment);
        btListComment = findViewById(R.id.btListComments);
        btListChapters = findViewById(R.id.btListChapters);
        btCreateChapter = findViewById(R.id.btCreateChapter);
    }


    private void getIntentData() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            comic = (Comic) intent.getSerializableExtra("comic");
            if (comic != null) {
                populateComicDetails();
                getComicDetail(comic.getId());
            }
        }
    }

    private void populateComicDetails() {
        title.setText(comic.getTitle());
        author.setText(comic.getAuthor());
        sinopsis.setText(comic.getSinopsis());
        theme.setText(comic.getTheme());
        if (comic.getNote() != null) {
            rating.setRating(comic.getNote().floatValue() / 2.0f);
        }
    }

    private void configureButtons() {
        btComment.setOnClickListener(v -> {
            if (comic != null) {
                startActivity(new Intent(getApplicationContext(), CreateBookCommentActivity.class).putExtra("comic", comic));
            }
        });

        btListComment.setOnClickListener(v -> {
            if (comic != null) {
                startActivity(new Intent(getApplicationContext(), ListCommentsComicsActivity.class).putExtra("comic", comic));
            }
        });

        btListChapters.setOnClickListener(v -> {
            if (comic != null) {
                startActivity(new Intent(getApplicationContext(), ListCommentsComicsActivity.class).putExtra("comic", comic));
            }
        });

        btCreateChapter.setOnClickListener(v -> {
            if (comic != null) {
                startActivity(new Intent(getApplicationContext(), ListCommentsComicsActivity.class).putExtra("comic", comic));
            }
        });
    }

    public void getComicDetail(Integer id) {

        Call<Comic> comic = WebService
                .getInstance(this)
                .createService(WebServiceApi.class)
                .getComic(id);

        comic.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Comic> call, Response<Comic> response) {
                if (response.code() == 200) {
                    Comic comic = response.body();
                    Log.d("TAG1", "Lista de libros " + response.body().toString());
                    title.setText(comic.getTitle());
                    author.setText(comic.getAuthor());
                    theme.setText(comic.getTheme());
                    sinopsis.setText(comic.getSinopsis());
                    if (comic.getNote() != null) {
                        rating.setRating(comic.getNote().floatValue() / 2.0f);
                    }
                } else {
                    Toast.makeText(ComicDetailsActivity.this, "Error fetching comic details", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Comic> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
                Toast.makeText(ComicDetailsActivity.this, "Error fetching comic details", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
