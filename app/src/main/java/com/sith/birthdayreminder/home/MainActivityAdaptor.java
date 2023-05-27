package com.sith.birthdayreminder.home;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sith.birthdayreminder.R;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
//        mainActivityViewHolder.age.setText(profileModel.getAge());


        // CALCULATE AGE OF PERSON USING CURRENT DATE AND DOB.
        String dateofBirth = profileModel.getDob();
        int currentAge = 0;
        DateTimeFormatter dobFormatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dobFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
            LocalDate dobLocalDate = LocalDate.parse(dateofBirth, dobFormatter);
            currentAge = getAge(dobLocalDate.getYear(), dobLocalDate.getMonth(), dobLocalDate.getDayOfMonth());
            mainActivityViewHolder.age.setText(String.valueOf(currentAge));
        }


        // SHOW HOW MANY DAYS LEFT FOR BIRTHDAY
        String stringDate= profileModel.getDob();
        int daysLeft = 0;

        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
            LocalDate date1=LocalDate.parse(stringDate, formatter);
            daysLeft = daysRemain(date1);
            if (daysLeft == 0){
                mainActivityViewHolder.daysLeft.setText("Today is Birthday!");
            }
            else {
                mainActivityViewHolder.daysLeft.setText("Days left: "+ String.valueOf(daysLeft));
            }
        }

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

    public static int daysRemain(LocalDate dob){
        LocalDate today = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            today = LocalDate.now(ZoneId.of("Asia/Kolkata"));
            long age = ChronoUnit.YEARS.between(dob, today);
            LocalDate nextBirthday = dob.plusYears(age);
            if (nextBirthday.isBefore(today)) {
                nextBirthday = dob.plusYears(age + 1);
            }
            long daysUntilNextBirthday = ChronoUnit.DAYS.between(today, nextBirthday);
            return Math.toIntExact(daysUntilNextBirthday);
        }
        return 0;
    }

    public int getAge(int year, Month month, int dayOfMonth) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Period.between(
                    LocalDate.of(year, month, dayOfMonth),
                    LocalDate.now()
            ).getYears();
        }
        return 0;
    }

}
