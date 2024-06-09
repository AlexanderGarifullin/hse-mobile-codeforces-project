package com.example.codeforces_project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeforces_project.Data.DatabaseHelper;
import com.example.codeforces_project.Data.GroupDAO;
import com.example.codeforces_project.Model.Group;
import com.example.codeforces_project.R;
import com.example.codeforces_project.Utils.CustomAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewGroups;
    FloatingActionButton addBtnGroups;

    ArrayList<Group> groups;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewGroups = findViewById(R.id.recyclerViewGroups);
        addBtnGroups = findViewById(R.id.addGroupBtn);
        addBtnGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddGroup.class);
                startActivity(intent);
            }
        });

        storeData();

        customAdapter = new CustomAdapter(MainActivity.this, this, groups);
        recyclerViewGroups.setAdapter(customAdapter);
        recyclerViewGroups.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    private void storeData() {
        GroupDAO groupDAO = new GroupDAO(new DatabaseHelper(MainActivity.this));
        groups = groupDAO.getAllGroups();
    }
}