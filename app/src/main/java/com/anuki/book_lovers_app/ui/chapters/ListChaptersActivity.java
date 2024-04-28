package com.anuki.book_lovers_app.ui.chapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.model.Chapter;
import com.anuki.book_lovers_app.model.ChapterAdapter;
import com.anuki.book_lovers_app.model.Comic;
import com.anuki.book_lovers_app.service.ComicService;
import com.anuki.book_lovers_app.web_client.WebService;
import com.anuki.book_lovers_app.web_client.WebServiceApi;

import java.util.List;

public class ListChaptersActivity extends AppCompatActivity implements ChapterAdapter.ClickedItem, SwipeRefreshLayout.OnRefreshListener {

    private Comic comic;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ChapterAdapter chapterAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ComicService comicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chapters_comic);

        initializeViews();
        configureRecyclerView();
        configureSwipeRefreshLayout();

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            comic = (Comic) intent.getSerializableExtra("comic");
            Context context = this;
            WebServiceApi webServiceApi = WebService.getInstance(context).createService(WebServiceApi.class);
            comicService = new ComicService(webServiceApi);
            getAllChaptersById(comic.getId());
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
        chapterAdapter = new ChapterAdapter(this::ClickedChapter);
        recyclerView.setAdapter(chapterAdapter);
    }

    private void configureSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    public void getAllChaptersById(Integer id){
        swipeRefreshLayout.setRefreshing(true);
        comicService.getAllChaptersByComicId(id, new ComicService.ChaptersCallback() {
            @Override
            public void onSuccess(List<Chapter> chapters) {
                chapterAdapter.setData(chapters);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(ListChaptersActivity.this, message, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void ClickedChapter(Chapter chapter) {
        startActivity(new Intent(this, ChapterDetailsActivity.class)
                .putExtra("chapter", chapter)
                .putExtra("comic", comic)
        );
    }

    @Override
    public void onRefresh() {
        getAllChaptersById(comic.getId());
    }


}
