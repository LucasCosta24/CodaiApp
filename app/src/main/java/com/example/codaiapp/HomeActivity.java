package com.example.codaiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.activity.OnBackPressedCallback;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.card.MaterialCardView;

import com.example.codaiapp.utils.SessionManager;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar topAppBar;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(this);

        if (!sessionManager.isLoggedIn()) {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_home);

        drawerLayout   = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        topAppBar      = findViewById(R.id.topAppBar);
        MaterialButton btnStart = findViewById(R.id.btnStart);

        topAppBar.setNavigationOnClickListener(
                v -> drawerLayout.openDrawer(GravityCompat.START)
        );

        btnStart.setVisibility(View.GONE);

        MaterialCardView cardArticle = findViewById(R.id.cardArticle);
        cardArticle.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ArticlesActivity.class);
            startActivity(intent);
        });

        MaterialCardView cardLastPost = findViewById(R.id.cardLastPost);
        cardLastPost.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ForumActivity.class);
            startActivity(intent);
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            boolean closeDrawer = true;

            if (id == R.id.nav_home) {
            } else if (id == R.id.nav_profile) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_articles) {
                Intent intent = new Intent(HomeActivity.this, ArticlesActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_forum) {
                Intent intent = new Intent(HomeActivity.this, ForumActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_logout) {
                sessionManager.logoutUser();

                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                closeDrawer = false;
            }

            if (closeDrawer) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            return true;
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    this.remove();
                    HomeActivity.super.onBackPressed();
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}