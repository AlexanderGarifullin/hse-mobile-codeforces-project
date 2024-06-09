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
import com.example.codeforces_project.Model.Group;
import com.example.codeforces_project.R;

public class UpdateGroup extends AppCompatActivity {

    EditText groupNameEdit;
    Button btnUpdate, btnDeleteGroup;

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

        getAndSetIntentData();

        ab = getSupportActionBar();
        setActionBarTitle();


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupDAO groupDAO = new GroupDAO(new DatabaseHelper(UpdateGroup.this));
                String groupName = groupNameEdit.getText().toString().trim();
                int grId = groupId;
                Group group = new Group(grId, groupName);
                long result = groupDAO.updateGroup(group);
                if (result == -1) {
                    Toast.makeText(UpdateGroup.this, "Failed to update", Toast.LENGTH_SHORT).show();
                } else {
                    updateGroupData(group);
                    setActionBarTitle();
                    Toast.makeText(UpdateGroup.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
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
        if(getIntent().hasExtra("groupId") && getIntent().hasExtra("groupName")){
            //Getting Data from Intent
            groupId = getIntent().getIntExtra("groupId", -1);
            groupName = getIntent().getStringExtra("groupName");

            //Setting Intent Data
            groupNameEdit.setText(groupName);

        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void setActionBarTitle() {
        if (ab != null) {
            ab.setTitle(groupName);
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + groupName + " ?");
        builder.setMessage("Are you sure you want to delete " + groupName + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                GroupDAO groupDAO = new GroupDAO(new DatabaseHelper(UpdateGroup.this));
                long result = groupDAO.deleteGroup(groupId);
                if (result == -1) {
                    Toast.makeText(UpdateGroup.this, "Failed to delete", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateGroup.this, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
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