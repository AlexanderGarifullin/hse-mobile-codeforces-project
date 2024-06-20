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

import com.example.codeforces_project.Activity.UpdateUser;
import com.example.codeforces_project.Model.User;
import com.example.codeforces_project.R;

import java.util.ArrayList;

public class UserCustomAdapter extends RecyclerView.Adapter<UserCustomAdapter.MyUserViewHolder>{

    private static final int RATING_LGM_GRANDMASTER = 3000;
    private static final int RATING_GRANDMASTER = 2400;
    private static final int RATING_MASTER = 2200;
    private static final int RATING_CANDIDATE_MASTER = 1900;
    private static final int RATING_EXPERT = 1600;
    private static final int RATING_SPECIALIST = 1400;
    private static final int RATING_PUPIL = 1200;
    private static final int RATING_NEWBIE = 0;
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
                String userId = context.getResources().getString(R.string.intentExtraUserId);
                String userName = context.getResources().getString(R.string.intentExtraUserName);
                String groupId = context.getResources().getString(R.string.intentExtraUserGroupId);
                String rating = context.getResources().getString(R.string.intentExtraUserRating);
                String maxRating = context.getResources().getString(R.string.intentExtraUserMaxRating);

                intent.putExtra(userId, users.get(position).getId());
                intent.putExtra(userName, String.valueOf(users.get(position).getName()));
                intent.putExtra(groupId, users.get(position).getGroupId());
                intent.putExtra(rating, users.get(position).getRating());
                intent.putExtra(maxRating, users.get(position).getMaxRating());
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    private void setHolderColors(@NonNull UserCustomAdapter.MyUserViewHolder holder, int curRating,
                            int maxRating) {
        if (maxRating >= RATING_LGM_GRANDMASTER) {
            setGrandMasterColor(holder.userMaxRatingTxt);
        } else {
            holder.userMaxRatingTxt.setTextColor(getColor(maxRating));
        }
        if (curRating >= RATING_LGM_GRANDMASTER) {
            setGrandMasterColor(holder.userNameTxt);
            setGrandMasterColor(holder.userRatingTxt);
        } else {
            holder.userNameTxt.setTextColor(getColor(curRating));
            holder.userRatingTxt.setTextColor(getColor(curRating));
        }
    }

    private int getColor(int rating) {
        if (rating >= RATING_GRANDMASTER) {
            return context.getResources().getColor(R.color.grandmaster);
        } else if (rating >= RATING_MASTER) {
            return context.getResources().getColor(R.color.master);
        } else if (rating >= RATING_CANDIDATE_MASTER) {
            return context.getResources().getColor(R.color.candidateMaster);
        } else if (rating >= RATING_EXPERT) {
            return context.getResources().getColor(R.color.expert);
        } else if (rating >= RATING_SPECIALIST) {
            return context.getResources().getColor(R.color.specialist);
        } else if (rating >= RATING_PUPIL) {
            return context.getResources().getColor(R.color.pupil);
        } else if (rating >= RATING_NEWBIE) {
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