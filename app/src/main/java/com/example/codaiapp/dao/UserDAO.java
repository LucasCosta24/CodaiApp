package com.example.codaiapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.codaiapp.model.User;
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public boolean registerUser(User user) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.COLUMN_NOME, user.getNome());
        values.put(DatabaseHelper.COLUMN_EMAIL, user.getEmail());

        String hashedPassword = BCrypt.hashpw(user.getSenha(), BCrypt.gensalt());
        values.put(DatabaseHelper.COLUMN_PASSWORD, hashedPassword);

        values.put(DatabaseHelper.COLUMN_BIO, user.getBio());
        values.put(DatabaseHelper.COLUMN_ARTICLES_READ_COUNT, user.getArticlesReadCount());
        values.put(DatabaseHelper.COLUMN_FORUM_POSTS_COUNT, user.getForumPostsCount());
        values.put(DatabaseHelper.COLUMN_COMMENTS_COUNT, user.getCommentsCount());

        long result = db.insert(DatabaseHelper.TABLE_USERS, null, values);
        db.close();

        return result != -1;
    }

    public User login(String email, String passwordTextPlano) {
        db = dbHelper.getReadableDatabase();
        User userEncontrado = null;

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USERS,
                null,
                DatabaseHelper.COLUMN_EMAIL + "=?",
                new String[]{email},
                null, null, null
        );

        if (cursor.moveToFirst()) {
            String hashDoBanco = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PASSWORD));

            if (BCrypt.checkpw(passwordTextPlano, hashDoBanco)) {
                userEncontrado = new User();
                userEncontrado.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
                userEncontrado.setNome(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOME)));
                userEncontrado.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL)));
            }
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return userEncontrado;
    }

    public boolean updateProfile(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Campos que podem ser atualizados pelo usuário na tela de edição
        values.put(DatabaseHelper.COLUMN_NOME, user.getNome());
        values.put(DatabaseHelper.COLUMN_BIO, user.getBio());

        // Usamos o email como critério (WHERE clause)
        String whereClause = DatabaseHelper.COLUMN_EMAIL + " = ?";
        String[] whereArgs = {user.getEmail()};

        // O metodo update retorna o número de linhas afetadas
        int rowsAffected = db.update(
                DatabaseHelper.TABLE_USERS,
                values,
                whereClause,
                whereArgs
        );
        db.close();

        return rowsAffected > 0;
    }

    public User getUserDetails(String email) {
        db = dbHelper.getReadableDatabase();
        User user = null;

        String[] columns = {
                DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_NOME,
                DatabaseHelper.COLUMN_EMAIL,
                DatabaseHelper.COLUMN_PASSWORD,
                DatabaseHelper.COLUMN_BIO,
                DatabaseHelper.COLUMN_ARTICLES_READ_COUNT,
                DatabaseHelper.COLUMN_FORUM_POSTS_COUNT,
                DatabaseHelper.COLUMN_COMMENTS_COUNT
        };

        String selection = DatabaseHelper.COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USERS,
                columns,
                selection,
                selectionArgs,
                null, null, null
        );

        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
            user.setNome(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOME)));
            user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL)));

            user.setBio(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BIO)));
            user.setArticlesReadCount(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ARTICLES_READ_COUNT)));
            user.setForumPostsCount(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FORUM_POSTS_COUNT)));
            user.setCommentsCount(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_COMMENTS_COUNT)));
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return user;
    }
}