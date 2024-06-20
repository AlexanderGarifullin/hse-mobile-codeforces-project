package com.example.codeforces_project.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.codeforces_project.Utils.Util;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createGroupTableQuery  = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT NOT NULL UNIQUE);",
                Util.GROUP_TABLE_NAME, Util.ID, Util.GROUP_TABLE_NAME);

        String createUserTableQuery = String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s INTEGER, " +
                        "%s INTEGER, %s INTEGER, FOREIGN KEY(%s) REFERENCES %s(%s) ON DELETE CASCADE);",
                Util.USER_TABLE_NAME, Util.ID, Util.USER_NAME, Util.GROUP_ID,
                Util.USER_RATING, Util.USER_MAX_RATING, Util.GROUP_ID, Util.GROUP_TABLE_NAME, Util.ID);

        db.execSQL(createGroupTableQuery);
        db.execSQL(createUserTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", Util.GROUP_TABLE_NAME));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", Util.USER_TABLE_NAME));
        onCreate(db);
    }
}
