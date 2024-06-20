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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.codeforces_project.Data.DatabaseHelper;
import com.example.codeforces_project.Data.GroupDAO;
import com.example.codeforces_project.Data.UserDao;
import com.example.codeforces_project.Model.Group;
import com.example.codeforces_project.Model.User;
import com.example.codeforces_project.R;

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
                UserDao userDao = new UserDao(new DatabaseHelper(UpdateUser.this));
                String userName = userNameEdit.getText().toString().trim();
                User user = new User(userId, groupId, rating, maxRating, userName);
                long result = userDao.updateUser(user);
                if (result == -1) {
                    Toast.makeText(UpdateUser.this, "Failed to update", Toast.LENGTH_SHORT).show();
                } else {
                    updateUserData(user);
                    setActionBarTitle();
                    Toast.makeText(UpdateUser.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                }
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
        if(getIntent().hasExtra("userId") && getIntent().hasExtra("userName")
                && getIntent().hasExtra("groupId") && getIntent().hasExtra("rating")
                && getIntent().hasExtra("maxRating")){
            //Getting Data from Intent
            userId = getIntent().getIntExtra("userId", -1);
            userName = getIntent().getStringExtra("userName");
            groupId = getIntent().getIntExtra("groupId", -1);
            rating = getIntent().getIntExtra("rating", -1);
            maxRating = getIntent().getIntExtra("maxRating",- 1);
            //Setting Intent Data
            userNameEdit.setText(userName);

        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void setActionBarTitle() {
        if (ab != null) {
            ab.setTitle(userName);
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + userName + " ?");
        builder.setMessage("Are you sure you want to delete " + userName + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                UserDao userDao = new UserDao(new DatabaseHelper(UpdateUser.this));
                long result = userDao.deleteUser(userId);
                if (result == -1) {
                    Toast.makeText(UpdateUser.this, "Failed to delete", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateUser.this, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}