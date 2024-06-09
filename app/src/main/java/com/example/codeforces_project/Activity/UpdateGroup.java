package com.example.codeforces_project.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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
    Button btnUpdate;

    String groupName;
    int groupId;

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

        getAndSetIntentData();

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
                    Toast.makeText(UpdateGroup.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });
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


}