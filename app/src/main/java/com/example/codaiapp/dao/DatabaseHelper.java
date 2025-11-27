package com.example.codaiapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "codaiapp.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_BIO = "bio";
    public static final String COLUMN_ARTICLES_READ_COUNT = "articlesReadCount";
    public static final String COLUMN_FORUM_POSTS_COUNT = "forumPostsCount";
    public static final String COLUMN_COMMENTS_COUNT = "commentsCount";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOME + " TEXT, " +
                    COLUMN_EMAIL + " TEXT UNIQUE, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_BIO + " TEXT DEFAULT '', " +
                    COLUMN_ARTICLES_READ_COUNT + " INTEGER DEFAULT 0, " +
                    COLUMN_FORUM_POSTS_COUNT + " INTEGER DEFAULT 0, " +
                    COLUMN_COMMENTS_COUNT + " INTEGER DEFAULT 0" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_BIO + " TEXT DEFAULT '';");
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_ARTICLES_READ_COUNT + " INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_FORUM_POSTS_COUNT + " INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_COMMENTS_COUNT + " INTEGER DEFAULT 0;");
        } else {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            onCreate(db);
        }
    }
}