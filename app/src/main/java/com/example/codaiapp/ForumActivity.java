package com.example.codaiapp;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ForumActivity extends AppCompatActivity {

    private RecyclerView rvPosts;
    private List<Post> postList;
    private LinearLayout emptyStateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        rvPosts = findViewById(R.id.rvPosts);
        emptyStateLayout = findViewById(R.id.emptyStateLayout);

        setupRecyclerView();
        loadPosts();
    }

    private void setupRecyclerView() {
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        // O adapter será configurado em loadPosts
    }

    private void loadPosts() {
        postList = new ArrayList<>();
        // Adicione posts de exemplo
        postList.add(new Post("Dúvida com Loop em Python", "Estou com dificuldades para entender como usar o loop 'for' com dicionários.", "Ana", "2 min atrás", "#Python", 5));
        postList.add(new Post("Como centralizar uma div?", "Qual é a forma mais moderna e eficiente de centralizar uma div com CSS?", "Carlos", "15 min atrás", "#CSS", 12));

        // Destaque um post como exemplo
        Post highlightedPost = new Post("Melhores práticas em Kotlin", "Discussão sobre as melhores práticas para desenvolvimento Android com Kotlin.", "Mariana", "1 hora atrás", "#Kotlin", 28);
        highlightedPost.setHighlighted(true);
        postList.add(highlightedPost);
        
        PostAdapter postAdapter = new PostAdapter(postList);
        rvPosts.setAdapter(postAdapter);

        checkEmptyState();
    }

    private void checkEmptyState() {
        if (postList.isEmpty()) {
            rvPosts.setVisibility(View.GONE);
            emptyStateLayout.setVisibility(View.VISIBLE);
        } else {
            rvPosts.setVisibility(View.VISIBLE);
            emptyStateLayout.setVisibility(View.GONE);
        }
    }
}
