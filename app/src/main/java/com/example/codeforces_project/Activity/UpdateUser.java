package com.example.codeforces_project.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.codeforces_project.API.CodeforcesApiService;
import com.example.codeforces_project.API.ModelResponse.UserAPI;
import com.example.codeforces_project.Data.DatabaseHelper;
import com.example.codeforces_project.Data.UserDao;
import com.example.codeforces_project.Model.User;
import com.example.codeforces_project.R;

import java.util.List;

public class UpdateUser extends AppCompatActivity {

    EditText userNameEdit;
    Button btnUpdate, btnDeleteUser;

    String userName;
    int userId, rating, maxRating, groupId;

    ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_user);

        userNameEdit = findViewById(R.id.userNameInputUpdate);
        btnUpdate = findViewById(R.id.update_user_btn);
        btnDeleteUser = findViewById(R.id.delete_user_button);


        getAndSetIntentData();
        ab = getSupportActionBar();
        setActionBarTitle();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String handle = userNameEdit.getText().toString().trim();

                CodeforcesApiService apiService = new CodeforcesApiService();

                apiService.getUserInfo(handle, new CodeforcesApiService.OnUserResponseListener() {
                    @Override
                    public void onUserResponse(List<UserAPI> users) {
                        if (users.isEmpty()) {
                            Toast.makeText(UpdateUser.this, getString(R.string.noSuchUser), Toast.LENGTH_SHORT).show();
                        } else {

                            UserDao userDao = new UserDao(new DatabaseHelper(UpdateUser.this));
                            UserAPI curUser = users.get(0);

                            if (userDao.doesUserExist(curUser.getHandle(), groupId)) {
                                Toast.makeText(UpdateUser.this, getString(R.string.alreadySuchUser), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            User user = new User(userId, groupId, curUser.getRating(), curUser.getMaxRating(), curUser.getHandle());
                            long result = userDao.updateUser(user);

                            if (result == -1) {
                                Toast.makeText(UpdateUser.this, getString(R.string.failedToUpdate), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UpdateUser.this,  getString(R.string.updatedSuccessfully), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });

        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

    void updateUserData(User user) {
        userName = user.getName();
    }

    void getAndSetIntentData() {
        if(getIntent().hasExtra(getString(R.string.intentExtraUserId))
                && getIntent().hasExtra(getString(R.string.intentExtraUserName))
                && getIntent().hasExtra(getString(R.string.intentExtraGroupId))
                && getIntent().hasExtra(getString(R.string.intentExtraUserRating))
                && getIntent().hasExtra(getString(R.string.intentExtraUserMaxRating))) {
            //Getting Data from Intent
            userId = getIntent().getIntExtra(getString(R.string.intentExtraUserId), -1);
            userName = getIntent().getStringExtra(getString(R.string.intentExtraUserName));
            groupId = getIntent().getIntExtra(getString(R.string.intentExtraGroupId), -1);
            rating = getIntent().getIntExtra(getString(R.string.intentExtraUserRating), -1);
            maxRating = getIntent().getIntExtra(getString(R.string.intentExtraUserMaxRating),- 1);
            //Setting Intent Data
            userNameEdit.setText(userName);
        } else{
            Toast.makeText(this, getString(R.string.noData), Toast.LENGTH_SHORT).show();
        }
    }

    void setActionBarTitle() {
        if (ab != null) {
            ab.setTitle(userName);
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(String.format(getString(R.string.askToDelete), userName));
        builder.setMessage(String.format(getString(R.string.askToSureDelete), userName));

        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                UserDao userDao = new UserDao(new DatabaseHelper(UpdateUser.this));
                long result = userDao.deleteUser(userId);
                if (result == -1) {
                    Toast.makeText(UpdateUser.this,  getString(R.string.failedToDelete), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateUser.this, getString(R.string.deleteSuccessfully), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}