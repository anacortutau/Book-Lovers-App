package com.anuki.book_lovers_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.model.Book;
import com.anuki.book_lovers_app.model.BookAdapter;
import com.anuki.book_lovers_app.model.Comment;
import com.anuki.book_lovers_app.model.CommentAdapter;
import com.anuki.book_lovers_app.shared.SharedResources;
import com.anuki.book_lovers_app.web_client.WebService;
import com.anuki.book_lovers_app.web_client.WebServiceApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailsActivity extends AppCompatActivity implements CommentAdapter.ClickedItem {

    TextView title;
    TextView author;
    TextView theme;
    TextView sinopsis;
    Book book;

    Toolbar toolbar;
    RecyclerView recyclerView;
    CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        title  = findViewById(R.id.titleText);
        author  = findViewById(R.id.authorText);
        sinopsis  = findViewById(R.id.sinopsisText);
        theme = findViewById(R.id.themeText);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerview);

        Intent intent = getIntent();
        if(intent.getExtras() !=null){
            book = (Book) intent.getSerializableExtra("data");

            String titleData = book.getTitle();
            String authorData = book.getAuthor();
            String sinopsisData = book.getSinopsis();
            String themeData = book.getTheme();


            title.setText(titleData);
            author.setText(authorData);
            theme.setText(themeData);
            sinopsis.setText(sinopsisData);

            getAllCommentsById(book.getId());
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

            commentAdapter = new CommentAdapter(this::ClickedComment);

        }
    }

    public void getAllCommentsById(Integer id){

        String token = SharedResources.getInstance(getApplicationContext()).getUser().getToken();
        String authHeader = "Bearer " + token;

        Call<List<Comment>> commentList = WebService
                .getInstance()
                .createService(WebServiceApi.class)
                .getAllCommentsByBookId(authHeader, id);

        commentList.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(response.code() == 200){
                    List<Comment> bookResponse = response.body();
                    Log.d("TAG1", "Lista de Comentarios " + response.body().toString());
                    commentAdapter.setData(bookResponse);
                    recyclerView.setAdapter(commentAdapter);
                }else{
                    Log.d("TAG1", "Error Desconocido");
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage());

            }
        });
    }

    @Override
    public void ClickedComment(Comment comment) {
        startActivity(new Intent(this,CommentDetailActivity.class)
                .putExtra("dataComment", comment)
                .putExtra("data", book)
        );
    }
}