package com.anuki.book_lovers_app.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anuki.book_lovers_app.R;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookAdapterVH> {

    private List<Book> bookList;
    private Context context;
    private ClickedItem clickedItem;

    public BookAdapter(ClickedItem clickedItem) {
        this.clickedItem = clickedItem;
    }

    public void setData(List<Book> bookList) {
        this.bookList = bookList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new BookAdapter.BookAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_book,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapterVH holder, int position) {

        Book book = bookList.get(position);

        String title = book.getTitle();
        String theme = book.getTheme();

        holder.theme.setText(theme);
        holder.title.setText(title);
        holder.imageMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedItem.ClickedBook(book);
            }
        });

    }

    public interface ClickedItem{
        public void ClickedBook(Book book);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class BookAdapterVH extends RecyclerView.ViewHolder {

        TextView title;
        TextView theme;
        ImageView imageMore;

        public BookAdapterVH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleText);
            theme = itemView.findViewById(R.id.themeText);
            imageMore = itemView.findViewById(R.id.imageMore);


        }
    }
}
