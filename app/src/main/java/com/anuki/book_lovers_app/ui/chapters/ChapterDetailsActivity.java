package com.anuki.book_lovers_app.ui.chapters;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.model.Book;
import com.anuki.book_lovers_app.ui.books.CreateBookCommentActivity;
import com.anuki.book_lovers_app.ui.books.ListCommentsBooksActivity;
import com.anuki.book_lovers_app.ui.menu.MenuActivity;

public class ChapterDetailsActivity extends AppCompatActivity {

    TextView title;
    TextView author;
    TextView theme;
    TextView sinopsis;
    RatingBar rating;
    Book book;
    Button btComment;
    Button btListComment;
    Button mainMenu;

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
        mainMenu = findViewById(R.id.btGoToMainMenu);

        Intent intent = getIntent();
        if(intent.getExtras() !=null){
            book = (Book) intent.getSerializableExtra("book");

            String titleData = book.getTitle();
            String authorData = book.getAuthor();
            String sinopsisData = book.getSinopsis();
            String themeData = book.getTheme();

            if (book.getNote() != null) {
                rating.setRating(book.getNote().floatValue() / 2.0f);
            }

            title.setText(titleData);
            author.setText(authorData);
            theme.setText(themeData);
            sinopsis.setText(sinopsisData);

        }

        btComment.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), CreateBookCommentActivity.class).putExtra("book", book)));

        btListComment.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ListCommentsBooksActivity.class).putExtra("book", book)));

        mainMenu.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MenuActivity.class)));

    }

}