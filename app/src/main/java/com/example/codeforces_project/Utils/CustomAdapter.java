package com.example.codeforces_project.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeforces_project.Activity.UpdateGroup;
import com.example.codeforces_project.Model.Group;
import com.example.codeforces_project.Model.User;
import com.example.codeforces_project.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Group> groups;

    Activity activity;

    public CustomAdapter(Activity activity, Context context, ArrayList groups) {
        this.activity = activity;
        this.context  = context;
        this.groups = groups;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.mygrouprow, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        holder.groupNameTxt.setText(String.valueOf(groups.get(position).getName()));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateGroup.class);
                intent.putExtra("groupId", groups.get(position).getId());
                intent.putExtra("groupName", String.valueOf(groups.get(position).getName()));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView groupNameTxt;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            groupNameTxt = itemView.findViewById(R.id.groupNameTxt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }
}
