package com.anuki.book_lovers_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.model.Book;

public class BookDetailsActivity extends AppCompatActivity {

    TextView title;
    TextView author;
    TextView theme;
    TextView sinopsis;
    TextView note;
    RatingBar rating;
    Book book;
    Button btComment;
    Button btListComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        rating = findViewById(R.id.ratingStars);
        title  = findViewById(R.id.titleText);
        author  = findViewById(R.id.authorText);
        sinopsis  = findViewById(R.id.sinopsisText);
        theme = findViewById(R.id.themeText);
        btComment = findViewById(R.id.btComment);
        btListComment = findViewById(R.id.btListComments);

        Intent intent = getIntent();
        if(intent.getExtras() !=null){
            book = (Book) intent.getSerializableExtra("book");

            String titleData = book.getTitle();
            String authorData = book.getAuthor();
            String sinopsisData = book.getSinopsis();
            String themeData = book.getTheme();

            rating.setRating(book.getNote().floatValue());
            title.setText(titleData);
            author.setText(authorData);
            theme.setText(themeData);
            sinopsis.setText(sinopsisData);

        }

        btComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreateCommentActivity.class).putExtra("book", book));
            }
        });

        btListComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListCommentsActivity.class).putExtra("book", book));
            }
        });

    }

}