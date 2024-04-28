package com.anuki.book_lovers_app.ui.chapters;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.model.Chapter;
import com.anuki.book_lovers_app.ui.books.CreateBookCommentActivity;
import com.anuki.book_lovers_app.ui.menu.MenuActivity;

public class ChapterDetailsActivity extends AppCompatActivity {

    TextView title;
    TextView sinopsis;
    TextView number;
    Chapter chapter;
    Button mainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_details);

        title  = findViewById(R.id.titleText);
        number  = findViewById(R.id.numberText);
        sinopsis  = findViewById(R.id.sinopsisText);

        mainMenu = findViewById(R.id.btGoToMainMenu);

        Intent intent = getIntent();
        if(intent.getExtras() !=null){
            chapter = (Chapter) intent.getSerializableExtra("chapter");

            String titleData = chapter.getTitle();
            String numberData = String.valueOf(chapter.getNumber());
            String sinopsisData = chapter.getSinopsis();

            title.setText(titleData);
            number.setText(numberData);
            sinopsis.setText(sinopsisData);

        }

        mainMenu.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MenuActivity.class)));

    }

}