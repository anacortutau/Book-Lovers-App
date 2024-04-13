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

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ComicAdapterVH>{

    private List<Comic> comicList;

    private Context context;

    private ClickedItem clickedItem;

    public ComicAdapter(ClickedItem clickedItem) {this.clickedItem = clickedItem;}

    public void setData (List<Comic> comicList){
        this.comicList = comicList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ComicAdapterVH onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        context = parent.getContext();
        return new ComicAdapter.ComicAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_comic, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ComicAdapter.ComicAdapterVH holder, int position) {

        Comic comic = comicList.get(position);

        String title = comic.getTitle();
        String author = comic.getAuthor();

        holder.author.setText(author);
        holder.title.setText(title);
        holder.imageMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedItem.ClickedComic(comic);
            }
        });

    }

    public interface ClickedItem{
        public void ClickedComic(Comic comic);
    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }

    public class ComicAdapterVH extends RecyclerView.ViewHolder {

        TextView title;
        TextView author;
        ImageView imageMore;

        public ComicAdapterVH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleComicText);
            author = itemView.findViewById(R.id.authorComicText);
            /*imageMore = itemView.findViewById(R.id.imageMoreComic);*/


        }
    }


}
