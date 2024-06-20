package com.example.codeforces_project.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeforces_project.Activity.UpdateGroup;
import com.example.codeforces_project.Activity.UpdateUser;
import com.example.codeforces_project.Model.Group;
import com.example.codeforces_project.Model.User;
import com.example.codeforces_project.R;

import java.util.ArrayList;

public class UserCustomAdapter extends RecyclerView.Adapter<UserCustomAdapter.MyUserViewHolder>{

    private Context context;
    private ArrayList<User> users;

    Activity activity;

    public UserCustomAdapter(Activity activity, Context context, ArrayList users) {
        this.activity = activity;
        this.context  = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserCustomAdapter.MyUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.myuserrow, parent, false);
        return new MyUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserCustomAdapter.MyUserViewHolder holder, int position) {
        holder.userNameTxt.setText(String.valueOf(users.get(position).getName()));
        holder.userRatingTxt.setText(String.valueOf(users.get(position).getRating()));
        holder.userMaxRatingTxt.setText(String.valueOf(users.get(position).getMaxRating()));

        setHolderColors(holder, users.get(position).getRating(), users.get(position).getMaxRating());

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateUser.class);
                intent.putExtra("userId", users.get(position).getId());
                intent.putExtra("userName", String.valueOf(users.get(position).getName()));
                intent.putExtra("groupId", users.get(position).getGroupId());
                intent.putExtra("rating", users.get(position).getRating());
                intent.putExtra("maxRating", users.get(position).getMaxRating());
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    private void setHolderColors(@NonNull UserCustomAdapter.MyUserViewHolder holder, int curRating,
                            int maxRating) {
        if (maxRating >= 3000) {
            setGrandMasterColor(holder.userMaxRatingTxt);
        } else {
            holder.userMaxRatingTxt.setTextColor(getColor(maxRating));
        }
        if (curRating >= 3000) {
            setGrandMasterColor(holder.userNameTxt);
            setGrandMasterColor(holder.userRatingTxt);
        } else {
            holder.userNameTxt.setTextColor(getColor(curRating));
            holder.userMaxRatingTxt.setTextColor(getColor(curRating));
        }
    }

    private int getColor(int rating) {
        if (rating >= 2400) {
            return context.getResources().getColor(R.color.grandmaster);
        } else if (rating >= 2200) {
            return context.getResources().getColor(R.color.master);
        } else if (rating >= 1900) {
            return context.getResources().getColor(R.color.candidateMaster);
        } else if (rating >= 1600) {
            return context.getResources().getColor(R.color.expert);
        } else if (rating >= 1400) {
            return context.getResources().getColor(R.color.specialist);
        } else if (rating >= 1200) {
            return context.getResources().getColor(R.color.newbie);
        } else if (rating >= 0) {
            return context.getResources().getColor(R.color.newbie);
        }
        return context.getResources().getColor(R.color.black);
    }

    private void setGrandMasterColor(TextView view) {
        String text = (String) view.getText();
        SpannableString spannableString = new SpannableString(text);

        int blackColor = ContextCompat.getColor(context, R.color.black);
        int redColor = ContextCompat.getColor(context, R.color.grandmaster);

        spannableString.setSpan(new ForegroundColorSpan(blackColor), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new ForegroundColorSpan(redColor), 1, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        view.setText(spannableString);
    }
    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyUserViewHolder extends RecyclerView.ViewHolder {

        TextView userNameTxt, userRatingTxt, userMaxRatingTxt;
        LinearLayout mainLayout;
        public MyUserViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTxt = itemView.findViewById(R.id.UserNameTxt);
            userRatingTxt = itemView.findViewById(R.id.UserRatingTxt);
            userMaxRatingTxt = itemView.findViewById(R.id.UserMaxRatingTxt);

            mainLayout = itemView.findViewById(R.id.mainUserLayout);
        }
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}