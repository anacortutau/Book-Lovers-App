package com.anuki.book_lovers_app.ui.comics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.shared.SharedResources;
import com.anuki.book_lovers_app.ui.comics.CreateComicActivity;
import com.anuki.book_lovers_app.ui.comics.ListComicActivity;
import com.anuki.book_lovers_app.ui.login.LoginActivity;
import com.anuki.book_lovers_app.ui.menu.MenuActivity;

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
        initializeViews();
        configureButtons();
    }

    private void initializeViews() {
        btCreateComic = findViewById(R.id.btCreateComic);
        btListComics = findViewById(R.id.btListComics);
        btMenu = findViewById(R.id.btMenu);
        btLogout = findViewById(R.id.tvLogout);
    }

    private void configureButtons() {
        btCreateComic.setOnClickListener(v -> openCreateComicActivity());
        btListComics.setOnClickListener(v -> openListComicActivity());
        btMenu.setOnClickListener(v -> openMenuActivity());
        btLogout.setOnClickListener(v -> logOut());
    }

    private void openCreateComicActivity() {
        startActivity(new Intent(getApplicationContext(), CreateComicActivity.class));
    }

    private void openListComicActivity() {
        startActivity(new Intent(getApplicationContext(), ListComicActivity.class));
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


