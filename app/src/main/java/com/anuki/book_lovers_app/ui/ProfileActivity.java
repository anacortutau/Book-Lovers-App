package com.anuki.book_lovers_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.model.User;
import com.anuki.book_lovers_app.shared.SharedResources;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView userName;
    TextView email;
    User user;
    Button btMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user =  SharedResources.getInstance(getApplicationContext()).getUser();
        userName = findViewById(R.id.userNameText);
        email  = findViewById(R.id.emailText);
        btMenu = findViewById(R.id.btMenu);

        userName.setText(user.getNombre());
        email.setText(user.getEmail());

        btMenu.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MenuActivity.class)));
    }
}