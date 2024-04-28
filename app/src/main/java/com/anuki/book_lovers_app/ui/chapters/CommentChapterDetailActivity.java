package com.anuki.book_lovers_app.ui.chapters;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.model.Book;
import com.anuki.book_lovers_app.model.Chapter;
import com.anuki.book_lovers_app.model.Comment;
import com.anuki.book_lovers_app.ui.books.BookDetailsActivity;
import com.anuki.book_lovers_app.ui.menu.MenuActivity;

public class CommentChapterDetailActivity extends AppCompatActivity {

    TextView title;
    TextView user;
    TextView commentString;
    TextView note;
    Comment comment;
    Chapter chapter;
    Button btMenu;
    Button btReturnToBookDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_book_detail);

        initializeViews();
        displayCommentDetails();
        configureButtons();
    }

    private void initializeViews() {
        title = findViewById(R.id.titleText);
        user = findViewById(R.id.userText);
        note = findViewById(R.id.noteTextComment);
        commentString = findViewById(R.id.commentText);
        btMenu = findViewById(R.id.btMenu);
        btReturnToBookDetail = findViewById(R.id.btReturnToBookDetail);
    }

    private void displayCommentDetails() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            comment = (Comment) intent.getSerializableExtra("comment");
            if (comment != null) {
                String titleData = comment.getTitle();
                String noteData = comment.getNote().toString();
                String userData = comment.getUser().getNombre();
                String commentData = comment.getComment();
                note.setText(noteData);
                title.setText(titleData);
                user.setText(userData);
                commentString.setText(commentData);
            }
        }
    }

    private void configureButtons() {
        btMenu.setOnClickListener(v -> openMenuActivity());
        btReturnToBookDetail.setOnClickListener(v -> returnToChapterDetail());
    }

    private void openMenuActivity() {
        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
    }

    private void returnToChapterDetail() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            chapter = (Chapter) intent.getSerializableExtra("chapter");
            if (chapter != null) {
                startActivity(new Intent(getApplicationContext(), ChapterDetailsActivity.class).putExtra("chapter", chapter));
            }
        }
    }
}
