package com.anuki.book_lovers_app.ui.books;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.shared.SharedResources;
import com.anuki.book_lovers_app.ui.login.LoginActivity;
import com.anuki.book_lovers_app.ui.menu.MenuActivity;

public class BooksMenuActivity extends AppCompatActivity {

    private Button btCreateBook;
    private Button btListBooks;
    private Button btMenu;
    private TextView btLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_menu);

        setUpView();
    }

    private void setUpView(){
        initializeViews();
        configureButtons();
    }

    private void initializeViews() {
        btCreateBook = findViewById(R.id.btCreateBook);
        btListBooks = findViewById(R.id.btListBooks);
        btMenu = findViewById(R.id.btMenu);
        btLogout = findViewById(R.id.tvLogout);
    }

    private void configureButtons() {
        btCreateBook.setOnClickListener(v -> openCreateBookActivity());
        btListBooks.setOnClickListener(v -> openListBookActivity());
        btMenu.setOnClickListener(v -> openMenuActivity());
        btLogout.setOnClickListener(v -> logOut());
    }

    private void openCreateBookActivity() {
        startActivity(new Intent(getApplicationContext(), CreateBookActivity.class));
    }

    private void openListBookActivity() {
        startActivity(new Intent(getApplicationContext(), ListBookActivity.class));
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