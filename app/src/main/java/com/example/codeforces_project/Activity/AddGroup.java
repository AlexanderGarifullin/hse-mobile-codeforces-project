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
import com.example.codeforces_project.R;

public class AddGroup extends AppCompatActivity {

    EditText groupNameInput;
    Button addBtnGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        groupNameInput = findViewById(R.id.groupNameInput);
        addBtnGroup = findViewById(R.id.saveGroupBtn);

        addBtnGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupDAO groupDAO = new GroupDAO(new DatabaseHelper(AddGroup.this));
                long result = groupDAO.addGroup(groupNameInput.getText().toString().trim());
                if (result == -1) {
                    Toast.makeText(AddGroup.this, "Failed to add", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddGroup.this, "Added Successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}