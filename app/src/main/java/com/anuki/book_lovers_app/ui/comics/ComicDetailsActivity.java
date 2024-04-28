package com.anuki.book_lovers_app.ui.comics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.widget.TextView;
import android.widget.RatingBar;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.model.Comic;
import com.anuki.book_lovers_app.service.ComicService;
import com.anuki.book_lovers_app.web_client.WebService;
import com.anuki.book_lovers_app.web_client.WebServiceApi;


public class ComicDetailsActivity extends AppCompatActivity {

    TextView title;
    TextView author;
    TextView theme;
    TextView sinopsis;
    RatingBar rating;
    Comic comic;
    Comic comicDetail;
    Button btComment;
    Button btListComment;
    Button btListChapters;
    Button btCreateChapter;

    private ComicService comicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_details);

        Context context = this;
        WebServiceApi webServiceApi = WebService.getInstance(context).createService(WebServiceApi.class);
        comicService = new ComicService(webServiceApi);

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
                getComicDetail(comic.getId());
            }
        }
    }

    private void populateComicDetails() {
        if (comicDetail != null) {
            title.setText(comicDetail.getTitle());
            author.setText(comicDetail.getWritter());
            sinopsis.setText(comicDetail.getSinopsis());
            theme.setText(comicDetail.getTheme());
            if (comicDetail.getNote() != null) {
                rating.setRating(comicDetail.getNote().floatValue() / 2.0f);
            }
        }
    }

    private void configureButtons() {
        btComment.setOnClickListener(v -> {
            if (comic != null) {
                startActivity(new Intent(getApplicationContext(), CreateComicCommentActivity.class).putExtra("comic", comic));
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
        comicService.getComic(id, new ComicService.ComicDetailCallback() {
            @Override
            public void onSuccess(Comic fetchedComic) {
                comicDetail = fetchedComic;
                populateComicDetails();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(ComicDetailsActivity.this, "Error fetching comic details", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
