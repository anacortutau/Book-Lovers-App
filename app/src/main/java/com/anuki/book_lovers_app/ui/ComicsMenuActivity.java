package com.anuki.book_lovers_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.shared.SharedResources;

public class ComicsMenuActivity extends AppCompatActivity {

    private Button btCreateComic;
    private Button btListComics;
    private Button btMenu;
    private TextView btLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comics_menu);

        setUpView();
    }

        private void setUpView() {
            btCreateComic = findViewById(R.id.btCreateComic);
            btListComics = findViewById(R.id.btListComics);
            btMenu = findViewById(R.id.btMenu);
            btLogout = findViewById(R.id.tvLogout);

            btCreateComic.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), CreateComicActivity.class)));

            btListComics.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ListComicActivity.class)));

            btMenu.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MenuActivity.class)));

            btLogout.setOnClickListener(v -> {
                SharedResources.getInstance(getApplicationContext()).logOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            });
        }
    }


