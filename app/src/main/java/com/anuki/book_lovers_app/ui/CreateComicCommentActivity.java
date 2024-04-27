package com.anuki.book_lovers_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.model.Book;
import com.anuki.book_lovers_app.model.Comic;
import com.anuki.book_lovers_app.model.Comment;
import com.anuki.book_lovers_app.web_client.WebService;
import com.anuki.book_lovers_app.web_client.WebServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateComicCommentActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etComment;
    private EditText etNote;
    private Button btCreate;
    private Button btCancel;
    private Button btMenu;
    private Comment comment;
    private Comic comic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comment);

        setUpView();
    }

    private void setUpView(){

        etTitle = findViewById(R.id.etTitleComment);
        etComment = findViewById(R.id.etCommentComment);
        etNote = findViewById(R.id.etNoteComment);

        btCreate = findViewById(R.id.btCreateComment);
        btCancel = findViewById(R.id.btCancel);
        btMenu = findViewById(R.id.btMenu);

        btCreate.setOnClickListener(v -> createCommentCheck());

        btCancel.setOnClickListener(v -> cleanForm());

        btMenu.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MenuActivity.class)));
    }

    private void cleanForm() {
        etTitle.setText(null);
        etComment.setText(null);
        etNote.setText(null);
        etTitle.requestFocus();
    }

    private void createCommentCheck(){
        String titleString = etTitle.getText().toString().trim();
        String commentString = etComment.getText().toString().trim();
        String noteString = etNote.getText().toString().trim();

        if(titleString.isEmpty()){
            etTitle.setError(getResources().getString(R.string.title_error));
            etTitle.requestFocus();
            return;
        }

        if(commentString.isEmpty()){
            etComment.setError(getResources().getString(R.string.comment_error));
            etComment.requestFocus();
            return;
        }

        if(noteString.isEmpty()){
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
        if(intent.getExtras() !=null){
            comic = (Comic) intent.getSerializableExtra("book");

            Call<Comment> call = WebService
                    .getInstance(this)
                    .createService(WebServiceApi.class)
                    .createCommentBook(comment, comic.getId());


            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<Comment> call, Response<Comment> response) {
                    if (response.code() == 201) {
                        Toast.makeText(CreateComicCommentActivity.this, "El comentario se ha creado correctamente", Toast.LENGTH_LONG).show();
                        Log.d("TAG1", "Comentario creado " + " id " + response.body().getId()
                                + " titulo: " + response.body().getTitle());
                        startActivity(new Intent(getApplicationContext(), BookDetailsActivity.class).putExtra("comic", comic));
                    } else {
                        Log.d("TAG1", "Error Desconocido");
                    }
                }

                @Override
                public void onFailure(Call<Comment> call, Throwable t) {
                    Log.d("TAG1", "Error Desconocido");
                }
            });
        }
    }

}