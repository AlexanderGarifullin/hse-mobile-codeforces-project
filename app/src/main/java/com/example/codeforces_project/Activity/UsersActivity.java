package com.example.codeforces_project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeforces_project.Data.DatabaseHelper;
import com.example.codeforces_project.Data.GroupDAO;
import com.example.codeforces_project.Data.UserDao;
import com.example.codeforces_project.Model.Group;
import com.example.codeforces_project.Model.User;
import com.example.codeforces_project.R;
import com.example.codeforces_project.Utils.CustomAdapter;
import com.example.codeforces_project.Utils.UserCustomAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity {

    RecyclerView recyclerViewUsers;
    FloatingActionButton addBtnUsers;

    ArrayList<User> users;
    UserCustomAdapter customAdapter;

    int groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_users);

        groupId = getIntent().getIntExtra("groupId", -1);

        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        addBtnUsers = findViewById(R.id.addUserBtn);

        addBtnUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsersActivity.this, AddUser.class);
                intent.putExtra("groupId", groupId);
                startActivity(intent);
            }
        });

        storeData();

        customAdapter = new UserCustomAdapter(UsersActivity.this, this, users);
        recyclerViewUsers.setAdapter(customAdapter);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(UsersActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    private void storeData() {
        UserDao userDao = new UserDao(new DatabaseHelper(UsersActivity.this));
        users = userDao.getAllUsers(groupId);
    }

    public void updateData() {
        UserDao userDao = new UserDao(new DatabaseHelper(UsersActivity.this));
        users = userDao.getAllUsers(groupId);
        customAdapter.setUsers(users);
        customAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateData();
    }
}