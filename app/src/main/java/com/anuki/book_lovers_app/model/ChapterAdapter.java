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
import java.util.Locale;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterAdapterVH>{

    private List<Chapter> chapterList;

    private Context context;

    private ClickedItem clickedItem;

    public ChapterAdapter(ClickedItem clickedItem) {this.clickedItem = clickedItem;}

    public void setData (List<Chapter> chapterList){
        this.chapterList = chapterList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChapterAdapterVH onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        context = parent.getContext();
        return new ChapterAdapter.ChapterAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_chapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterAdapter.ChapterAdapterVH holder, int position) {

        Chapter chapter = chapterList.get(position);

        String title = chapter.getTitle();
        String sinopsis = chapter.getSinopsis();
        Integer number = chapter.getNumber();

        holder.title.setText(title);
        holder.sinopsis.setText(sinopsis);
        holder.number.setText(String.format(Locale.getDefault(), "%d", number));

        holder.imageMore.setOnClickListener(view -> clickedItem.ClickedChapter(chapter));

    }

    public interface ClickedItem{
        void ClickedChapter(Chapter chapter);
    }

    @Override
    public int getItemCount() {
        return chapterList != null ? chapterList.size() : 0;
    }

    public class ChapterAdapterVH extends RecyclerView.ViewHolder {

        TextView title;
        TextView sinopsis;
        ImageView imageMore;
        TextView number;

        public ChapterAdapterVH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleText);
            sinopsis = itemView.findViewById(R.id.authorText);
            imageMore = itemView.findViewById(R.id.imageMore);
            number = itemView.findViewById(R.id.number);
        }
    }


}
