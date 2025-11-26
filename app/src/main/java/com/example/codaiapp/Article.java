package com.example.codaiapp;

public class Article {
    private String title;
    private String excerpt;
    private String author;
    private String date;
    private String category;
    private int readTime;
    private int viewCount;
    private boolean isFeatured;

    public Article(String title, String excerpt, String author, String date, String category, int readTime, int viewCount, boolean isFeatured) {
        this.title = title;
        this.excerpt = excerpt;
        this.author = author;
        this.date = date;
        this.category = category;
        this.readTime = readTime;
        this.viewCount = viewCount;
        this.isFeatured = isFeatured;
    }

    // Getters
    public String getTitle() { return title; }
    public String getExcerpt() { return excerpt; }
    public String getAuthor() { return author; }
    public String getDate() { return date; }
    public String getCategory() { return category; }
    public int getReadTime() { return readTime; }
    public int getViewCount() { return viewCount; }
    public boolean isFeatured() { return isFeatured; }
}
