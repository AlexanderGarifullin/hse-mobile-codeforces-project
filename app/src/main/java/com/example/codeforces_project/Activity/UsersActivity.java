package com.example.codeforces_project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeforces_project.API.CodeforcesApiService;
import com.example.codeforces_project.API.ModelResponse.UserAPI;
import com.example.codeforces_project.Data.DatabaseHelper;
import com.example.codeforces_project.Data.UserDao;
import com.example.codeforces_project.Model.User;
import com.example.codeforces_project.R;
import com.example.codeforces_project.Utils.UserCustomAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {

    RecyclerView recyclerViewUsers;
    FloatingActionButton addBtnUsers, refreshBtnUsers;

    ArrayList<User> groupUsers;

    UserCustomAdapter customAdapter;

    int groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        groupId = getIntent().getIntExtra(getString(R.string.intentExtraGroupId), -1);

        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        addBtnUsers = findViewById(R.id.addUserBtn);
        refreshBtnUsers = findViewById(R.id.userRefreshBtn);

        addBtnUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsersActivity.this, AddUser.class);
                intent.putExtra(getString(R.string.intentExtraGroupId), groupId);
                startActivity(intent);
            }
        });

        refreshBtnUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < groupUsers.size(); i++) {
                    User user = groupUsers.get(i);
                    sb.append(user.getName());
                    if (i < groupUsers.size() - 1) {
                        sb.append(";");
                    }
                }

                String handles = sb.toString();

                CodeforcesApiService apiService = new CodeforcesApiService();

                apiService.getUserInfo(handles, new CodeforcesApiService.OnUserResponseListener() {
                    @Override
                    public void onUserResponse(List<UserAPI> users) {
                        UserDao userDao = new UserDao(new DatabaseHelper(UsersActivity.this));
                        for (int i = 0; i < users.size(); ++i) {
                            User user = new User(groupUsers.get(i).getId(), groupId,
                                    users.get(i).getRating(), users.get(i).getMaxRating(), users.get(i).getHandle());
                            long result = userDao.updateUser(user);
                        }
                        updateData();
                        Toast.makeText(UsersActivity.this, getString(R.string.updatedSuccessfully), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        storeData();

        customAdapter = new UserCustomAdapter(UsersActivity.this, this, groupUsers);
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
        groupUsers = userDao.getAllUsers(groupId);
    }

    public void updateData() {
        UserDao userDao = new UserDao(new DatabaseHelper(UsersActivity.this));
        groupUsers = userDao.getAllUsers(groupId);
        customAdapter.setUsers(groupUsers);
        customAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateData();
    }
}