package com.example.codeforces_project.Activity;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.codeforces_project.Model.Group;
import com.example.codeforces_project.R;

public class UpdateGroup extends AppCompatActivity {

    EditText groupNameEdit;
    Button btnUpdate, btnDeleteGroup, btnUsers;

    String groupName;
    int groupId;

    ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_group);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        groupNameEdit = findViewById(R.id.groupNameInputUpdate);
        btnUpdate = findViewById(R.id.update_group_btn);
        btnDeleteGroup = findViewById(R.id.delete_button);
        btnUsers = findViewById(R.id.user_btn);

        getAndSetIntentData();

        ab = getSupportActionBar();
        setActionBarTitle();

        btnUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateGroup.this, UsersActivity.class);
                intent.putExtra(getString(R.string.intentExtraGroupId), groupId);
                startActivity(intent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupDAO groupDAO = new GroupDAO(new DatabaseHelper(UpdateGroup.this));
                String groupName = groupNameEdit.getText().toString().trim();
                int grId = groupId;
                Group group = new Group(grId, groupName);
                long result = groupDAO.updateGroup(group);
                if (result == -1) {
                    Toast.makeText(UpdateGroup.this, getString(R.string.failedToUpdate), Toast.LENGTH_SHORT).show();
                } else {
                    updateGroupData(group);
                    setActionBarTitle();
                    Toast.makeText(UpdateGroup.this, getString(R.string.updatedSuccessfully), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDeleteGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

    void updateGroupData(Group group) {
        groupName = group.getName();
    }

    void getAndSetIntentData() {
        if(getIntent().hasExtra(getString(R.string.intentExtraGroupId))
                && getIntent().hasExtra(getString(R.string.intentExtraGroupName))){
            //Getting Data from Intent
            groupId = getIntent().getIntExtra(getString(R.string.intentExtraGroupId), -1);
            groupName = getIntent().getStringExtra(getString(R.string.intentExtraGroupName));

            //Setting Intent Data
            groupNameEdit.setText(groupName);

        }else{
            Toast.makeText(this, getString(R.string.noData), Toast.LENGTH_SHORT).show();
        }
    }

    void setActionBarTitle() {
        if (ab != null) {
            ab.setTitle(groupName);
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(String.format(getString(R.string.askToDelete), groupName));

        builder.setMessage(String.format(getString(R.string.askToSureDelete), groupName));

        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                GroupDAO groupDAO = new GroupDAO(new DatabaseHelper(UpdateGroup.this));
                long result = groupDAO.deleteGroup(groupId);
                if (result == -1) {
                    Toast.makeText(UpdateGroup.this, getString(R.string.failedToDelete), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateGroup.this, getString(R.string.deleteSuccessfully), Toast.LENGTH_SHORT).show();
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