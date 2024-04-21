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
import com.anuki.book_lovers_app.model.BookAdapter;
import com.anuki.book_lovers_app.shared.SharedResources;
import com.anuki.book_lovers_app.web_client.WebService;
import com.anuki.book_lovers_app.web_client.WebServiceApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListBookActivity extends AppCompatActivity implements BookAdapter.ClickedItem {

    Toolbar toolbar;
    RecyclerView recyclerView;
    BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        bookAdapter = new BookAdapter(this::ClickedBook);
        getAllBooks();
    }

    public void getAllBooks(){

        String token = SharedResources.getInstance(getApplicationContext()).getUser().getToken();
        String authHeader = "Bearer " + token;

        Call<List<Book>> booklist = WebService
                .getInstance()
                .createService(WebServiceApi.class)
                .getAllBook(authHeader);

        booklist.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.code() == 200) {
                    List<Book> bookResponse = response.body();
                    Log.d("TAG1", "Lista de libros " + response.body().toString());
                    bookAdapter.setData(bookResponse);
                    recyclerView.setAdapter(bookAdapter);
                } else {
                    Log.d("TAG1", "Error Desconocido");
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());

            }
        });
    }

    @Override
    public void ClickedBook(Book book) {
        startActivity(new Intent(this,BookDetailsActivity.class).putExtra("book", book));
    }
}