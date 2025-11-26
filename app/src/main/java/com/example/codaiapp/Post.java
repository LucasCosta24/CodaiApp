package com.example.codaiapp;

public class Post {
    private String title;
    private String content;
    private String author;
    private String date;
    private String category;
    private int commentCount;
    private boolean isHighlighted;

    public Post(String title, String content, String author, String date, String category, int commentCount) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.date = date;
        this.category = category;
        this.commentCount = commentCount;
        this.isHighlighted = false; // Default
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }
    public String getDate() { return date; }
    public String getCategory() { return category; }
    public int getCommentCount() { return commentCount; }
    public boolean isHighlighted() { return isHighlighted; }
    public void setHighlighted(boolean highlighted) { isHighlighted = highlighted; }
}
