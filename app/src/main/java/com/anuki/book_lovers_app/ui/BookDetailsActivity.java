package com.anuki.book_lovers_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.model.Book;

public class BookDetailsActivity extends AppCompatActivity {

    TextView title;
    TextView author;
    TextView sinopsis;
    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        title  = findViewById(R.id.titleText);
        author  = findViewById(R.id.authorText);
        sinopsis  = findViewById(R.id.sinopsisText);

        Intent intent = getIntent();
        if(intent.getExtras() !=null){
            book = (Book) intent.getSerializableExtra("data");

            String titleData = book.getTitle();
            String authorData = book.getAuthor();
            String sinopsisData = book.getSinopsis();


            title.setText(titleData);
            author.setText(authorData);
            sinopsis.setText(sinopsisData);


        }
    }
}