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
    private final Context context;
    private final ClickedItem clickedItem;

    public CommentAdapter(ClickedItem clickedItem) {
        this.clickedItem = clickedItem;
        this.context = clickedItem instanceof Context ? (Context) clickedItem : null;
    }

    public void setData(List<Comment> commentList) {
        this.commentList = commentList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_row_layout, parent, false);
        return new CommentAdapterVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapterVH holder, int position) {
        if (commentList != null && position < commentList.size()) {
            holder.bind(commentList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return commentList != null ? commentList.size() : 0;
    }

    public class CommentAdapterVH extends RecyclerView.ViewHolder {

        private final TextView titleText;
        private final TextView useNameText;
        private final TextView commentPreviewText;
        private final ImageView imageMore;
        private final RatingBar ratingBar;

        public CommentAdapterVH(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
            useNameText = itemView.findViewById(R.id.userNameText);
            commentPreviewText = itemView.findViewById(R.id.commentPreviewText);
            imageMore = itemView.findViewById(R.id.imageMore);
            ratingBar = itemView.findViewById(R.id.ratingBar);

            imageMore.setOnClickListener(view -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION && clickedItem != null) {
                    clickedItem.ClickedComment(commentList.get(position));
                }
            });
        }

        public void bind(Comment comment) {
            String userName = comment.getUser().getNombre();
            String title = comment.getTitle();
            Integer note = comment.getNote();
            String commentPreview = comment.getComment().length() <= 30 ? comment.getComment() : comment.getComment().substring(0, 30) + "...";

            if (note != null) {
                float normalizedRating = note / 10.0f * 5.0f;
                ratingBar.setProgress((int) Math.ceil(normalizedRating));
            }

            useNameText.setText(userName);
            titleText.setText(title);
            commentPreviewText.setText(commentPreview);
        }
    }

    public interface ClickedItem {
        void ClickedComment(Comment comment);
    }
}
