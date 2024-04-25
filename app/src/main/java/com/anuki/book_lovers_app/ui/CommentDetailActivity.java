package com.anuki.book_lovers_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import com.anuki.book_lovers_app.R;

import com.anuki.book_lovers_app.model.Book;
import com.anuki.book_lovers_app.model.Comment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class CommentDetailActivity extends AppCompatActivity {

    TextView title;
    TextView user;
    TextView commentString;
    TextView note;
    Comment comment;
    Book book;
    Button btMenu;
    Button btReturnToBookDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detail);

        title = findViewById(R.id.titleText);
        user = findViewById(R.id.userText);
        note = findViewById(R.id.noteTextComment);
        commentString = findViewById(R.id.commentText);
        btMenu = findViewById(R.id.btMenu);
        btReturnToBookDetail = findViewById(R.id.btReturnToBookDetail);

        Intent intent = getIntent();
        if(intent.getExtras() !=null){
            comment = (Comment) intent.getSerializableExtra("comment");
            String titleData = comment.getTitle();
            String noteData = comment.getNote().toString();
            String userData = comment.getUser().getNombre();
            String commentData = comment.getComment();
            note.setText(noteData);
            title.setText(titleData);
            user.setText(userData);
            commentString.setText(commentData);
        }

        btMenu.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MenuActivity.class)));

        btReturnToBookDetail.setOnClickListener(v -> {
            if(intent.getExtras() !=null){
                book = (Book) intent.getSerializableExtra("book");
            }
            startActivity(new Intent(getApplicationContext(), BookDetailsActivity.class).putExtra("book", book));
        });
    }
}