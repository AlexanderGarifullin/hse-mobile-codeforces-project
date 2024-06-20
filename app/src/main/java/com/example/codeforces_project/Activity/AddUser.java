package com.example.codeforces_project.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.codeforces_project.API.CodeforcesApiService;
import com.example.codeforces_project.API.ModelResponse.UserAPI;
import com.example.codeforces_project.Data.DatabaseHelper;
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
        groupId = getIntent().getIntExtra(getString(R.string.intentExtraGroupId), -1);
        userNameInput = findViewById(R.id.UserNameInput);
        addBtnUser = findViewById(R.id.saveUserBtn);

        addBtnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String handle = userNameInput.getText().toString().trim();

                CodeforcesApiService apiService = new CodeforcesApiService();

                apiService.getUserInfo(handle, new CodeforcesApiService.OnUserResponseListener() {
                    @Override
                    public void onUserResponse(List<UserAPI> users) {
                        if (users.isEmpty()) {
                            Toast.makeText(AddUser.this, getString(R.string.noSuchUser), Toast.LENGTH_SHORT).show();
                        } else {
                            UserDao userDao = new UserDao(new DatabaseHelper(AddUser.this));
                            UserAPI curUser = users.get(0);

                            if (userDao.doesUserExist(curUser.getHandle(), groupId)) {
                                Toast.makeText(AddUser.this, getString(R.string.alreadySuchUser), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            long result = userDao.addUser(curUser.getHandle(), groupId, curUser.getRating(),
                                    curUser.getMaxRating());

                            if (result == -1) {
                                Toast.makeText(AddUser.this, getString(R.string.failedToAdd), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddUser.this, getString(R.string.addedSuccessfully), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
    }


}