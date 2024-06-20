package com.example.codeforces_project.Data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.codeforces_project.Model.Group;
import com.example.codeforces_project.Model.User;
import com.example.codeforces_project.Utils.Util;

import java.util.ArrayList;

public class UserDao {
    private final SQLiteDatabase db;

    public UserDao(DatabaseHelper databaseHelper) {
        db = databaseHelper.getReadableDatabase();
    }

    // Добавление пользователя
    public long addUser(String name, int groupId, int rating, int maxRating) {
        ContentValues values = new ContentValues();
        values.put(Util.USER_NAME, name);
        values.put(Util.GROUP_ID, groupId);
        values.put(Util.USER_RATING, rating);
        values.put(Util.USER_MAX_RATING, maxRating);
        return db.insert(Util.USER_TABLE_NAME, null, values);
    }

    // Получение всех пользователей
    public ArrayList<User> getAllUsers(int groupId) {
        ArrayList<User> users = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Util.USER_TABLE_NAME + " WHERE " + Util.GROUP_ID + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(groupId)});

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                int idIndex = cursor.getColumnIndex(Util.ID);
                if (idIndex != -1) {
                    user.setId(cursor.getInt(idIndex));
                }

                int nameIndex = cursor.getColumnIndex(Util.USER_NAME);
                if (nameIndex != -1) {
                    user.setName(cursor.getString(nameIndex));
                }

                int groupIndex = cursor.getColumnIndex(Util.GROUP_ID);
                if (groupIndex != -1) {
                    user.setGroupId(cursor.getInt(groupIndex));
                }

                int ratingIndex = cursor.getColumnIndex(Util.USER_RATING);
                if (ratingIndex != -1) {
                    user.setRating(cursor.getInt(ratingIndex));
                }

                int maxRatingIndex = cursor.getColumnIndex(Util.USER_MAX_RATING);
                if (maxRatingIndex != -1) {
                    user.setMaxRating(cursor.getInt(maxRatingIndex));
                }

                users.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return users;
    }

    // Обновление пользователя
    public int updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put(Util.USER_NAME, user.getName());
        values.put(Util.USER_RATING, user.getRating());
        values.put(Util.USER_MAX_RATING, user.getMaxRating());

        return db.update(Util.USER_TABLE_NAME, values, Util.ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }

    // Удаление пользователя
    public int deleteUser(int userId) {
        return db.delete(Util.USER_TABLE_NAME, Util.ID + " = ?",
                new String[]{String.valueOf(userId)});
    }

}
