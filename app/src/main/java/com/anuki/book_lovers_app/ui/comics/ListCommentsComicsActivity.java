package com.anuki.book_lovers_app.ui.comics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.model.Chapter;
import com.anuki.book_lovers_app.model.Comic;
import com.anuki.book_lovers_app.model.Comment;
import com.anuki.book_lovers_app.model.CommentAdapter;
import com.anuki.book_lovers_app.service.ComicService;
import com.anuki.book_lovers_app.ui.books.CommentBookDetailActivity;
import com.anuki.book_lovers_app.ui.chapters.CommentChapterDetailActivity;
import com.anuki.book_lovers_app.ui.chapters.ListCommentsChapterActivity;
import com.anuki.book_lovers_app.web_client.WebService;
import com.anuki.book_lovers_app.web_client.WebServiceApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ListCommentsComicsActivity extends AppCompatActivity implements CommentAdapter.ClickedItem, SwipeRefreshLayout.OnRefreshListener {

    private Comic comic;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private CommentAdapter commentAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ComicService comicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_comments_books);

        initializeViews();
        configureRecyclerView();
        configureSwipeRefreshLayout();

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            comic = (Comic) intent.getSerializableExtra("comic");
            comicService = new ComicService(this);
            getAllCommentsById(comic.getId());
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
        comicService.getAllCommentsByComicId(id, new ComicService.CommentsCallback() {
            @Override
            public void onSuccess(List<Comment> comments) {
                commentAdapter.setData(comments);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(ListCommentsComicsActivity.this, message, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void ClickedComment(Comment comment) {
        startActivity(new Intent(this, CommentComicDetailActivity.class)
                .putExtra("comment", comment)
                .putExtra("comic", comic)
        );
    }

    @Override
    public void onRefresh() {
        getAllCommentsById(comic.getId());
    }


}
