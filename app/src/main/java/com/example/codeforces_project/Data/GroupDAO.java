package com.example.codeforces_project.Data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.codeforces_project.Model.Group;
import com.example.codeforces_project.Utils.Util;

import java.util.ArrayList;
import java.util.List;

public class GroupDAO {
    private final SQLiteDatabase db;

    public GroupDAO(DatabaseHelper databaseHelper) {
        db = databaseHelper.getReadableDatabase();
    }

    // Добавление группы
    public long addGroup(String name) {
        ContentValues values = new ContentValues();
        values.put(Util.GROUP_TABLE_NAME, name);
        return db.insert(Util.GROUP_TABLE_NAME, null, values);
    }

    // Получение всех групп
    public List<Group> getAllGroups() {
        List<Group> groups = new ArrayList<>();
        String selectQuery =  "SELECT * FROM \"" + Util.GROUP_TABLE_NAME + "\"";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Group group = new Group();
                int idIndex = cursor.getColumnIndex(Util.GROUP_KEY_ID);
                if (idIndex != -1) {
                    group.setId(cursor.getInt(idIndex));
                }

                int nameIndex = cursor.getColumnIndex(Util.GROUP_NAME);
                if (nameIndex != -1) {
                    group.setName(cursor.getString(nameIndex));
                }
                groups.add(group);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return groups;
    }

    // Обновление группы
    public int updateGroup(Group group) {
        ContentValues values = new ContentValues();
        values.put(Util.GROUP_NAME, group.getName());

        return db.update(Util.GROUP_TABLE_NAME, values, Util.GROUP_KEY_ID + " = ?",
                new String[]{String.valueOf(group.getId())});
    }

    // Удаление группы
    public int deleteGroup(int groupId) {
        return db.delete(Util.GROUP_TABLE_NAME, Util.GROUP_KEY_ID + " = ?",
                new String[]{String.valueOf(groupId)});
    }
}
