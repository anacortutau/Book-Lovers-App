package com.anuki.book_lovers_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.model.Book;
import com.anuki.book_lovers_app.model.Comment;
import com.anuki.book_lovers_app.model.CommentAdapter;
import com.anuki.book_lovers_app.shared.SharedResources;
import com.anuki.book_lovers_app.web_client.WebService;
import com.anuki.book_lovers_app.web_client.WebServiceApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCommentsBooksActivity extends AppCompatActivity implements CommentAdapter.ClickedItem {

    Book book;
    Toolbar toolbar;
    RecyclerView recyclerView;
    CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_comments);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerviewComment);

        Intent intent = getIntent();
        if(intent.getExtras() !=null){
            book = (Book) intent.getSerializableExtra("book");

            getAllCommentsById(book.getId());
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
            recyclerView.setItemViewCacheSize(5);
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
                    List<Comment> commentsResponse = response.body();
                    Log.d("TAG1", "Lista de Comentarios " + response.body().toString());
                    commentAdapter.setData(commentsResponse);
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
        startActivity(new Intent(this, CommentBookDetailActivity.class)
                .putExtra("comment", comment)
                .putExtra("book", book)
        );
    }
}