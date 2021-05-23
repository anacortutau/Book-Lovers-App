package com.anuki.book_lovers_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import com.anuki.book_lovers_app.R;

import com.anuki.book_lovers_app.model.Comment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CommentDetailActivity extends AppCompatActivity {

    TextView title;
    TextView user;
    TextView commentString;
    Comment comment;
    Button btMenu;
    Button btReturnToBookDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detail);

        title = findViewById(R.id.titleText);
        user = findViewById(R.id.userText);
        commentString = findViewById(R.id.commentText);
        btMenu = findViewById(R.id.btMenu);
        btReturnToBookDetail = findViewById(R.id.btReturnToBookDetail);

        Intent intent = getIntent();
        if(intent.getExtras() !=null){
            comment = (Comment) intent.getSerializableExtra("dataComment");
            String titleData = comment.getTitle();
            String userData = comment.getUserName();
            String commentData = comment.getComment();
            title.setText(titleData);
            user.setText(userData);
            commentString.setText(commentData);
        }

        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            }
        });

        btReturnToBookDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BookDetailsActivity.class));
            }
        });
    }
}