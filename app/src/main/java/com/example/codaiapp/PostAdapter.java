package com.example.codaiapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> posts;

    public PostAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.tvPostTitle.setText(post.getTitle());
        holder.tvPostContent.setText(post.getContent());
        holder.tvCategoryTag.setText(post.getCategory());
        holder.tvAuthor.setText(post.getAuthor());
        holder.tvDate.setText(post.getDate());
        holder.tvComments.setText(String.format("%d comentário(s)", post.getCommentCount()));

        // Lógica de destaque
        if (post.isHighlighted()) {
            // TODO: Adicionar estilo de destaque (ex: mudar cor de fundo do card)
        } else {
            // TODO: Remover estilo de destaque
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvPostTitle, tvPostContent, tvCategoryTag, tvAuthor, tvDate, tvComments;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPostTitle = itemView.findViewById(R.id.tvPostTitle);
            tvPostContent = itemView.findViewById(R.id.tvPostContent);
            tvCategoryTag = itemView.findViewById(R.id.tvCategoryTag);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvComments = itemView.findViewById(R.id.tvComments);
        }
    }
}
