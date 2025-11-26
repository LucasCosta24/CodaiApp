package com.example.codaiapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private List<Article> articles;

    public ArticleAdapter(List<Article> articles) {
        this.articles = articles;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.tvArticleTitle.setText(article.getTitle());
        holder.tvArticleExcerpt.setText(article.getExcerpt());
        holder.tvArticleCategoryTag.setText(article.getCategory());
        holder.tvArticleAuthor.setText(article.getAuthor());
        holder.tvArticleDate.setText(article.getDate());
        holder.tvArticleReadTime.setText(String.format("%d min", article.getReadTime()));
        holder.tvArticleViews.setText(String.format("%d visualizações", article.getViewCount()));

        if (article.isFeatured()) {
            // TODO: Adicionar estilo de destaque
        } else {
            // TODO: Remover estilo de destaque
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView tvArticleTitle, tvArticleExcerpt, tvArticleCategoryTag, tvArticleAuthor, tvArticleDate, tvArticleReadTime, tvArticleViews;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvArticleTitle = itemView.findViewById(R.id.tvArticleTitle);
            tvArticleExcerpt = itemView.findViewById(R.id.tvArticleExcerpt);
            tvArticleCategoryTag = itemView.findViewById(R.id.tvArticleCategoryTag);
            tvArticleAuthor = itemView.findViewById(R.id.tvArticleAuthor);
            tvArticleDate = itemView.findViewById(R.id.tvArticleDate);
            tvArticleReadTime = itemView.findViewById(R.id.tvArticleReadTime);
            tvArticleViews = itemView.findViewById(R.id.tvArticleViews);
        }
    }
}
