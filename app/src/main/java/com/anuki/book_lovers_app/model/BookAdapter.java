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

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> bookList;
    private final Context context;
    private final ClickedItem clickedItem;

    public BookAdapter(ClickedItem clickedItem) {
        this.clickedItem = clickedItem;
        this.context = clickedItem instanceof Context ? (Context) clickedItem : null;
    }

    public void setData(List<Book> bookList) {
        this.bookList = bookList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_book, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        if (bookList != null && position < bookList.size()) {
            holder.bind(bookList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return bookList != null ? bookList.size() : 0;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleText;
        private final TextView authorText;
        private final ImageView imageMore;
        private final RatingBar ratingBar;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
            authorText = itemView.findViewById(R.id.authorText);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            imageMore = itemView.findViewById(R.id.imageMore);

            imageMore.setOnClickListener(view -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION && clickedItem != null) {
                    clickedItem.ClickedBook(bookList.get(position));
                }
            });
        }

        public void bind(Book book) {
            titleText.setText(book.getTitle());
            authorText.setText(book.getAuthor());

            Double note = book.getNote();
            if (note != null) {
                float normalizedRating = (float) (note * 5.0 / 10.0);
                ratingBar.setProgress(Math.round(normalizedRating));
            }
        }
    }

    public interface ClickedItem {
        void ClickedBook(Book book);
    }
}