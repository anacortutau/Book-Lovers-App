package com.anuki.book_lovers_app.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
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
        String author = book.getAuthor();
        Double note = book.getNote();

        holder.authorText.setText(author);
        holder.titleText.setText(title);

        if (note != null) {
            float normalizedRating = (float) (note * 5.0 / 10.0);
            holder.ratingBar.setProgress(Math.round(normalizedRating));
        }

        holder.imageMore.setOnClickListener(view -> clickedItem.ClickedBook(book));

    }


    public interface ClickedItem{
        public void ClickedBook(Book book);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class BookAdapterVH extends RecyclerView.ViewHolder {

        TextView titleText;
        TextView authorText;
        ImageView imageMore;
        RatingBar ratingBar;

        public BookAdapterVH(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
            authorText = itemView.findViewById(R.id.authorText);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            imageMore = itemView.findViewById(R.id.imageMore);


        }
    }
}
