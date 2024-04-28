package com.anuki.book_lovers_app.ui.comics;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.model.Comic;
import com.anuki.book_lovers_app.model.Comment;
import com.anuki.book_lovers_app.service.ComicService;
import com.anuki.book_lovers_app.ui.books.BookDetailsActivity;
import com.anuki.book_lovers_app.ui.menu.MenuActivity;
import com.anuki.book_lovers_app.web_client.WebService;
import com.anuki.book_lovers_app.web_client.WebServiceApi;


public class CreateComicCommentActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etComment;
    private EditText etNote;
    private Button btCreate;
    private Button btCancel;
    private Button btMenu;
    private Comment comment;
    private Comic comic;
    private ComicService comicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comment);

        setUpView();
    }

    private void setUpView() {
        initializeViews();
        configureButtons();
        Context context = this;
        WebServiceApi webServiceApi = WebService.getInstance(context).createService(WebServiceApi.class);
        comicService = new ComicService(webServiceApi);
    }

    private void initializeViews() {
        etTitle = findViewById(R.id.etTitleComment);
        etComment = findViewById(R.id.etCommentComment);
        etNote = findViewById(R.id.etNoteComment);
        btCreate = findViewById(R.id.btCreateComment);
        btCancel = findViewById(R.id.btCancel);
        btMenu = findViewById(R.id.btMenu);
    }

    private void configureButtons() {
        btCreate.setOnClickListener(v -> createCommentCheck());
        btCancel.setOnClickListener(v -> cleanForm());
        btMenu.setOnClickListener(v -> openMenuActivity());
    }

    private void cleanForm() {
        etTitle.setText(null);
        etComment.setText(null);
        etNote.setText(null);
        etTitle.requestFocus();
    }

    private void createCommentCheck() {
        String titleString = etTitle.getText().toString().trim();
        String commentString = etComment.getText().toString().trim();
        String noteString = etNote.getText().toString().trim();

        if (titleString.isEmpty()) {
            etTitle.setError(getResources().getString(R.string.title_error));
            etTitle.requestFocus();
            return;
        }

        if (commentString.isEmpty()) {
            etComment.setError(getResources().getString(R.string.comment_error));
            etComment.requestFocus();
            return;
        }

        if (noteString.isEmpty()) {
            etNote.setError(getResources().getString(R.string.note_error));
            etNote.requestFocus();
            return;
        }

        comment = new Comment();
        comment.setTitle(titleString);
        comment.setComment(commentString);
        comment.setNote(Integer.parseInt(noteString));
        createCommentComic();
    }

    private void createCommentComic() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            comic = (Comic) intent.getSerializableExtra("comic");
            if (comic != null) {
                comicService.createCommentComic(comment, comic.getId(), new ComicService.CommentCreateCallback() {
                    @Override
                    public void onSuccess(Comment responseComment) {
                        Toast.makeText(CreateComicCommentActivity.this, "El comentario se ha creado correctamente", Toast.LENGTH_LONG).show();
                        Log.d("TAG1", "Comentario creado " + " id " + responseComment.getId()
                                + " titulo: " + responseComment.getTitle());
                        startActivity(new Intent(getApplicationContext(), ComicDetailsActivity.class).putExtra("comic", comic));
                    }

                    @Override
                    public void onError(String message) {
                        Log.d("TAG1", "Error al crear el comentario: " + message);
                    }
                });
            }
        }
    }

    private void openMenuActivity() {
        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
    }
}
