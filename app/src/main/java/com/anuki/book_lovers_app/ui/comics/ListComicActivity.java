package com.anuki.book_lovers_app.ui.comics;

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
import com.anuki.book_lovers_app.model.Comic;
import com.anuki.book_lovers_app.model.ComicAdapter;
import com.anuki.book_lovers_app.service.BookService;
import com.anuki.book_lovers_app.service.ComicService;
import com.anuki.book_lovers_app.ui.books.ListBookActivity;
import com.anuki.book_lovers_app.web_client.WebService;
import com.anuki.book_lovers_app.web_client.WebServiceApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ListComicActivity extends AppCompatActivity implements ComicAdapter.ClickedItem, SwipeRefreshLayout.OnRefreshListener {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ComicAdapter comicAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ComicService comicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_comic);

        initializeViews();
        configureRecyclerView();
        configureSwipeRefreshLayout();
        Context context = this;
        WebServiceApi webServiceApi = WebService.getInstance(context).createService(WebServiceApi.class);
        comicService = new ComicService(webServiceApi);
        getAllComics();
    }

    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerview);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
    }

    private void configureRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        comicAdapter = new ComicAdapter(this::ClickedComic);
        recyclerView.setAdapter(comicAdapter);
    }

    private void configureSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    public void getAllComics(){

        comicService.getAllComics(new ComicService.ComicCallback() {
            @Override
            public void onSuccess(List<Comic> comics) {
                comicAdapter.setData(comics);
                recyclerView.setAdapter(comicAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(ListComicActivity.this, message, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    @Override
    public void ClickedComic(Comic comic) {
        startActivity(new Intent(this, ComicDetailsActivity.class).putExtra("comic", comic));
    }

    @Override
    public void onRefresh() {
        getAllComics();
    }
}
