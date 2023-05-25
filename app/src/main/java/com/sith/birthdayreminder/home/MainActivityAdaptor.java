package com.sith.birthdayreminder.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sith.birthdayreminder.R;

import java.util.ArrayList;

public class MainActivityAdaptor extends RecyclerView.Adapter {
    Context context;
    ArrayList<ProfileModel> profileModelArrayList;

    public MainActivityAdaptor(Context context, ArrayList<ProfileModel> profileModelArrayList) {
        this.context = context;
        this.profileModelArrayList = profileModelArrayList;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_main_activity_profile_design, parent,false);
        return new MainActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProfileModel profileModel = profileModelArrayList.get(position);
        MainActivityViewHolder mainActivityViewHolder = (MainActivityViewHolder) holder;
        mainActivityViewHolder.name.setText(profileModel.getName());
        mainActivityViewHolder.dob.setText(profileModel.getDob());
        mainActivityViewHolder.daysLeft.setText(profileModel.getAge());
        mainActivityViewHolder.age.setText(profileModel.getAge());
    }

    @Override
    public int getItemCount() {
        return profileModelArrayList.size();
    }
    public class MainActivityViewHolder extends RecyclerView.ViewHolder {
        private TextView name, dob, daysLeft, age;
        public MainActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.profileName);
            dob = itemView.findViewById(R.id.profileDob);
            daysLeft = itemView.findViewById(R.id.profileDaysLeft);
            age = itemView.findViewById(R.id.profileAge);
        }
    }

}
