package com.anuki.book_lovers_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;
import android.widget.RatingBar;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.model.Comic;


public class ComicDetailsActivity extends AppCompatActivity {

    TextView title;

    TextView author;

    TextView theme;

    TextView sinopsis;

    TextView note;

    RatingBar rating;

    Comic comic;

    Button btComment;
    Button btListComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_details);

        rating = findViewById(R.id.ratingStars);
        title = findViewById(R.id.titleText);
        author = findViewById(R.id.authorText);
        sinopsis = findViewById(R.id.sinopsisText);
        theme = findViewById(R.id.themeText);
        btComment = findViewById(R.id.btComment);
        btListComment = findViewById(R.id.btListComments);

        Intent intent = getIntent();
        if(intent.getExtras() !=null) {
            comic = (Comic) intent.getSerializableExtra("comic");

            String titleData = comic.getTitle();
            String authorData = comic.getAuthor();
            String sinopsisData = comic.getSinopsis();
            String themeData = comic.getTheme();

            rating.setRating(comic.getNote().floatValue());
            title.setText(titleData);
            author.setText(authorData);
            theme.setText(themeData);
            sinopsis.setText(sinopsisData);

        }

        btComment.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), CreateCommentActivity.class).putExtra("comic", comic)));

        btListComment.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ListCommentsComicsActivity.class).putExtra("comic", comic)));
    }
}
