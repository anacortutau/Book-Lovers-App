package com.anuki.book_lovers_app.ui.comics;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.model.Chapter;
import com.anuki.book_lovers_app.model.Comic;
import com.anuki.book_lovers_app.model.Comment;
import com.anuki.book_lovers_app.ui.chapters.ChapterDetailsActivity;
import com.anuki.book_lovers_app.ui.menu.MenuActivity;

public class CommentComicDetailActivity extends AppCompatActivity {

    TextView title;
    TextView user;
    TextView commentString;
    TextView note;
    Comment comment;
    Comic comic;
    Button btMenu;
    Button btReturnToComicDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_comic_detail);

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
        btReturnToComicDetail = findViewById(R.id.btReturnToComicDetail);
    }

    private void displayCommentDetails() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            comment = (Comment) intent.getSerializableExtra("comment");
            if (comment != null) {
                String titleData = comment.getTitle();
                String userData = "";
                String noteData = comment.getNote().toString();
                if (comment.getUser() != null) {
                    userData = comment.getUser().getNombre();
                }
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
        btReturnToComicDetail.setOnClickListener(v -> returnToComicDetail());
    }

    private void openMenuActivity() {
        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
    }

    private void returnToComicDetail() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            comic = (Comic) intent.getSerializableExtra("comic");
            if (comic != null) {
                startActivity(new Intent(getApplicationContext(), ComicDetailsActivity.class).putExtra("comic", comic));
            }
        }
    }
}
