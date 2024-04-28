package com.anuki.book_lovers_app.ui.books;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.model.Book;
import com.anuki.book_lovers_app.model.Comment;
import com.anuki.book_lovers_app.model.CommentAdapter;
import com.anuki.book_lovers_app.service.BookService;
import com.anuki.book_lovers_app.web_client.WebService;
import com.anuki.book_lovers_app.web_client.WebServiceApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCommentsBooksActivity extends AppCompatActivity implements CommentAdapter.ClickedItem, SwipeRefreshLayout.OnRefreshListener {

    private Book book;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private CommentAdapter commentAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BookService bookService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_comments_books);

        initializeViews();
        configureRecyclerView();
        configureSwipeRefreshLayout();

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            book = (Book) intent.getSerializableExtra("book");
            Context context = this;
            WebServiceApi webServiceApi = WebService.getInstance(context).createService(WebServiceApi.class);
            bookService = new BookService(webServiceApi);
            getAllCommentsById(book.getId());
        }
    }

    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerviewComment);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
    }

    private void configureRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        commentAdapter = new CommentAdapter(this::ClickedComment);
        recyclerView.setAdapter(commentAdapter);
    }

    private void configureSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    public void getAllCommentsById(Integer id){
        swipeRefreshLayout.setRefreshing(true);
        bookService.getAllCommentsByBookId(id, new BookService.CommentCallback() {
            @Override
            public void onSuccess(List<Comment> comments) {
                commentAdapter.setData(comments);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(ListCommentsBooksActivity.this, message, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
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

    @Override
    public void onRefresh() {
        getAllCommentsById(book.getId());
    }
}