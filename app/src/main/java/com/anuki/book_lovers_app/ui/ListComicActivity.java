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
import com.anuki.book_lovers_app.model.Comic;
import com.anuki.book_lovers_app.model.ComicAdapter;
import com.anuki.book_lovers_app.shared.SharedResources;
import com.anuki.book_lovers_app.web_client.WebService;
import com.anuki.book_lovers_app.web_client.WebServiceApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ListComicActivity extends AppCompatActivity implements ComicAdapter.ClickedItem {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ComicAdapter comicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_comic);

        toolbar = findViewById(R.id.toolbar);
        /*recyclerView = findViewById(R.id.recyclerview);*/

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        comicAdapter = new ComicAdapter(this::ClickedComic);
        getAllComics();
    }

    public void getAllComics(){

        String token = SharedResources.getInstance(getApplicationContext()).getUser().getToken();
        String authHeader = "Bearer " + token;

        Call<List<Comic>> comiclist = WebService
                .getInstance()
                .createService(WebServiceApi.class)
                .getAllComic(authHeader);

        comiclist.enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                if(response.code() == 200){
                    List<Comic> comicResponse = response.body();
                    Log.d("TAG1", "Lista de comics " + response.body().toString());
                    comicAdapter.setData(comicResponse);
                    recyclerView.setAdapter(comicAdapter);
                }else{
                    Log.d("TAG1", "Error Desconocido");
                }
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage());

            }
        });
    }
    @Override
    public void ClickedComic(Comic comic) {
        startActivity(new Intent(this,ComicDetailsActivity.class).putExtra("comic", comic));
    }
}
