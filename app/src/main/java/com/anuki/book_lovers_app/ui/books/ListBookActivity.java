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
import android.widget.Toast;

import com.anuki.book_lovers_app.R;
import com.anuki.book_lovers_app.model.Book;
import com.anuki.book_lovers_app.model.BookAdapter;
import com.anuki.book_lovers_app.service.BookService;
import com.anuki.book_lovers_app.web_client.WebService;
import com.anuki.book_lovers_app.web_client.WebServiceApi;

import java.util.List;

public class ListBookActivity extends AppCompatActivity implements BookAdapter.ClickedItem, SwipeRefreshLayout.OnRefreshListener {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BookService bookService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book);

        initializeViews();

        configureRecyclerView();

        configureSwipeRefreshLayout();

        Context context = this;
        WebServiceApi webServiceApi = WebService.getInstance(context).createService(WebServiceApi.class);
        bookService = new BookService(webServiceApi);

        getAllBooks();
    }

    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerview);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
    }

    private void configureRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        bookAdapter = new BookAdapter(this::ClickedBook);
        recyclerView.setAdapter(bookAdapter);
    }

    private void configureSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void getAllBooks() {
        bookService.getAllBooks(new BookService.BookCallback() {
            @Override
            public void onSuccess(List<Book> books) {
                bookAdapter.setData(books);
                recyclerView.setAdapter(bookAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(ListBookActivity.this, message, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void ClickedBook(Book book) {
        startActivity(new Intent(this, BookDetailsActivity.class).putExtra("book", book));
    }

    @Override
    public void onRefresh() {
        getAllBooks();
    }
}