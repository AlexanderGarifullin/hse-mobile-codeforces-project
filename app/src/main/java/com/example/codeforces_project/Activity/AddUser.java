package com.example.codeforces_project.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.codeforces_project.API.CodeforcesQueryAPI;
import com.example.codeforces_project.API.ModelResponse.UserAPI;
import com.example.codeforces_project.Data.DatabaseHelper;
import com.example.codeforces_project.Data.GroupDAO;
import com.example.codeforces_project.Data.UserDao;
import com.example.codeforces_project.R;

import java.util.List;

public class AddUser extends AppCompatActivity {
    EditText userNameInput;
    Button addBtnUser;
    int groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_user);
        groupId = getIntent().getIntExtra("groupId", -1);
        userNameInput = findViewById(R.id.UserNameInput);
        addBtnUser = findViewById(R.id.saveUserBtn);

        addBtnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info", "put on btn");
                String handle = userNameInput.getText().toString().trim();

                CodeforcesQueryAPI userInfoFetcher = new CodeforcesQueryAPI();
                userInfoFetcher.fetchUserInfo(handle, new CodeforcesQueryAPI.UserInfoCallback() {
                    @Override
                    public void onSuccess(List<UserAPI> users) {
                        Log.i("info", "good api");
                        UserAPI currentCodeforcesUser = users.get(0);

                        UserDao userDao = new UserDao(new DatabaseHelper(AddUser.this));
                        long result = userDao.addUser(handle, groupId, currentCodeforcesUser.getRating(),
                                currentCodeforcesUser.getMaxRating());

                        if (result == -1) {
                            Toast.makeText(AddUser.this, "Failed to add", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddUser.this, "Added Successfully!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Log.i("info", "error api");
                        Toast.makeText(AddUser.this, "Failed to add", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}