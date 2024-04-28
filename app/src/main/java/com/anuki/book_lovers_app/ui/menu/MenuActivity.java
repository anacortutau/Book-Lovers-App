package com.anuki.book_lovers_app.ui.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.shared.SharedResources;
import com.anuki.book_lovers_app.ui.comics.ComicsMenuActivity;
import com.anuki.book_lovers_app.ui.books.BooksMenuActivity;
import com.anuki.book_lovers_app.ui.login.LoginActivity;
import com.anuki.book_lovers_app.ui.login.ProfileActivity;

public class MenuActivity extends AppCompatActivity {

    private Button btBooks;
    private Button btComics;
    private Button btProfile;
    private TextView btLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setUpView();
    }

    private void setUpView(){
        btBooks = findViewById(R.id.btBooks);
        btComics = findViewById(R.id.btComics);
        btProfile = findViewById(R.id.btProfile);
        btLogout = findViewById(R.id.tvLogout);

        btBooks.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), BooksMenuActivity.class)));

        btComics.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ComicsMenuActivity.class)));

        btProfile.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ProfileActivity.class)));

        btLogout.setOnClickListener(v -> {
            SharedResources.getInstance(getApplicationContext()).logOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

    }
}