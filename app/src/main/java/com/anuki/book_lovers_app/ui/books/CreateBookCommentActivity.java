package com.anuki.book_lovers_app.ui.books;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.model.Book;
import com.anuki.book_lovers_app.model.Comment;
import com.anuki.book_lovers_app.service.BookService;
import com.anuki.book_lovers_app.ui.menu.MenuActivity;
import com.anuki.book_lovers_app.web_client.WebService;
import com.anuki.book_lovers_app.web_client.WebServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateBookCommentActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etComment;
    private EditText etNote;
    private Button btCreate;
    private Button btCancel;
    private Button btMenu;
    private Comment comment;
    private Book book;

    private BookService bookService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comment);

        setUpView();
    }

    private void setUpView() {
        initializeViews();
        configureButtons();
        bookService = new BookService(this);
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
        createCommentBook();
    }

    private void createCommentBook() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            book = (Book) intent.getSerializableExtra("book");
            if (book != null) {
                bookService.createCommentBook(comment, book.getId(), new BookService.CommentCreateCallback() {
                    @Override
                    public void onSuccess(Comment comment) {
                        Toast.makeText(CreateBookCommentActivity.this, "El comentario se ha creado correctamente", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), BookDetailsActivity.class).putExtra("book", book));
                    }

                    @Override
                    public void onError(String message) {
                        Log.d("TAG1", message);
                        Toast.makeText(CreateBookCommentActivity.this, "Ha habido un error creando el comentario", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    private void openMenuActivity() {
        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
    }
}