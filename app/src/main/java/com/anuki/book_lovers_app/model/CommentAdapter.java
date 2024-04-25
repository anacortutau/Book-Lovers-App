package com.anuki.book_lovers_app.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anuki.book_lovers_app.R;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentAdapterVH> {

    private List<Comment> commentList;
    private Context context;
    private ClickedItem clickedItem;

    public CommentAdapter(ClickedItem clickedItem) {
        this.clickedItem = clickedItem;
    }

    public void setData(List<Comment> commentList) {
        this.commentList = commentList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new CommentAdapter.CommentAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_comment,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapterVH holder, int position) {

        Comment comment = commentList.get(position);

        String title = comment.getTitle();
        String userName = comment.getUser().getNombre();

        holder.useNameText.setText(userName);
        holder.title.setText(title);
        holder.imageMore.setOnClickListener(view -> clickedItem.ClickedComment(comment));

    }

    public interface ClickedItem{
        public void ClickedComment(Comment comment);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class CommentAdapterVH extends RecyclerView.ViewHolder {

        TextView title;
        TextView useNameText;
        ImageView imageMore;

        public CommentAdapterVH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleText);
            useNameText = itemView.findViewById(R.id.useNameText);
            imageMore = itemView.findViewById(R.id.imageMore);
        }
    }
}
