package com.anuki.book_lovers_app.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anuki.book_lovers_app.R;

import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ComicAdapterVH>{

    private List<Comic> comicList;

    private Context context;

    private ClickedItem clickedItem;

    public ChapterAdapter(ClickedItem clickedItem) {this.clickedItem = clickedItem;}

    public void setData (List<Comic> comicList){
        this.comicList = comicList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ComicAdapterVH onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        context = parent.getContext();
        return new ChapterAdapter.ComicAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_comic, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterAdapter.ComicAdapterVH holder, int position) {

        Comic comic = comicList.get(position);

        String title = comic.getTitle();
        String author = comic.getAuthor();
        Double note = comic.getNote();

        holder.author.setText(author);
        holder.title.setText(title);
        if (note != null) {
            float normalizedRating = (float) (note * 5.0 / 10.0);
            holder.ratingBar.setProgress(Math.round(normalizedRating));
        }


        holder.imageMore.setOnClickListener(view -> clickedItem.ClickedComic(comic));

    }

    public interface ClickedItem{
        public void ClickedComic(Comic comic);
    }

    @Override
    public int getItemCount() {
        return comicList != null ? comicList.size() : 0;
    }

    public class ComicAdapterVH extends RecyclerView.ViewHolder {

        TextView title;
        TextView author;
        ImageView imageMore;
        RatingBar ratingBar;

        public ComicAdapterVH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleText);
            author = itemView.findViewById(R.id.authorText);
            imageMore = itemView.findViewById(R.id.imageMore);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }


}
