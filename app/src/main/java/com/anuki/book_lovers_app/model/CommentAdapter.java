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
        View itemView = LayoutInflater.from(context).inflate(R.layout.comment_row_layout, parent, false);
        return new CommentAdapterVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapterVH holder, int position) {

        Comment comment = commentList.get(position);

        String userName = comment.getUser().getNombre();
        String title = comment.getTitle();
        Integer note = comment.getNote();
        String commentPreview;
        if (comment.getComment().length() <= 30) {
            commentPreview = comment.getComment();
        } else {
            commentPreview = comment.getComment().substring(0, 30) + "...";
        }

        if (note != null) {
            float normalizedRating = note / 10.0f * 5.0f;
            holder.ratingBar.setProgress((int) Math.ceil(normalizedRating));
        }

        holder.useNameText.setText(userName);
        holder.titleText.setText(title);
        holder.commentPreviewText.setText(commentPreview);
        holder.imageMore.setOnClickListener(view -> clickedItem.ClickedComment(comment));

    }

    public interface ClickedItem {
        void ClickedComment(Comment comment);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class CommentAdapterVH extends RecyclerView.ViewHolder {

        TextView titleText;
        TextView useNameText;
        TextView commentPreviewText;
        ImageView imageMore;
        RatingBar ratingBar;

        public CommentAdapterVH(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
            useNameText = itemView.findViewById(R.id.userNameText);
            commentPreviewText = itemView.findViewById(R.id.commentPreviewText);
            imageMore = itemView.findViewById(R.id.imageMore);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}

