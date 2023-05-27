package com.sith.birthdayreminder.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.sith.birthdayreminder.AddPerson;
import com.sith.birthdayreminder.DisplayInfo;
import com.sith.birthdayreminder.R;
import com.sith.birthdayreminder.firebase.FirebaseDbManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyViewList;
    private Context context = this;
    private LinearLayout llProfile1;
    private Button addBirthdayBtn;
    private RecyclerView homeProfileRecyclerView;
    private MainActivityAdaptor mainActivityAdaptor;
    private ArrayList<ProfileModel> profileModelArrayList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addBirthdayBtn = findViewById(R.id.addBirthdayBtn);

        addBirthdayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, AddPerson.class));
            }
        });

//      Recycler View
        profileModelArrayList = new ArrayList<ProfileModel>();
        homeProfileRecyclerView = findViewById(R.id.homeProfileRecyclerView);
//
//        ProfileModel p1 = new ProfileModel("sudeep sawant", "10-11-2000", "Lion", "29");
//        ProfileModel p2 = new ProfileModel("shubham sawant", "10-11-2000", "Lion", "29");
//        ProfileModel p3 = new ProfileModel("Neel sawant", "10-11-2000", "Lion", "29");
//        ProfileModel p4 = new ProfileModel("duru sawant", "10-11-2000", "Lion", "29");


//        profileModelArrayList.add(p1);
//        profileModelArrayList.add(p2);
//        profileModelArrayList.add(p3);
//        profileModelArrayList.add(p4);

        mainActivityAdaptor = new MainActivityAdaptor(context, profileModelArrayList);
        homeProfileRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        homeProfileRecyclerView.setAdapter(mainActivityAdaptor);


    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshProfiles();
    }

    private void refreshProfiles() {
//        get profiles for db
        FirebaseDbManager.retrieveBirthdayProfiles(context, new FirebaseDbManager.FirebaseCallbackInterface() {
            @Override
            public void onComplete(Object object) {
                if (object == null) {
                    Toast.makeText(context, "No birthday profiles saved", Toast.LENGTH_SHORT).show();
                }
                else {
                    ArrayList<ProfileModel> profileArray = new ArrayList<>();
                    Iterable<DataSnapshot> profileChildrens = (Iterable<DataSnapshot>) object;

                    for (DataSnapshot singleChildren: profileChildrens){
                        ProfileModel profileModel =singleChildren.getValue(ProfileModel.class);
                        profileArray.add(profileModel);
                    }

                    profileModelArrayList.clear();
                    profileModelArrayList.addAll(profileArray);
                    mainActivityAdaptor.notifyDataSetChanged();
                    homeProfileRecyclerView.scrollToPosition(profileModelArrayList.size()-1);

                }
            }

            @Override
            public void onError() {

            }

            @Override
            public void onCancel() {

            }
        });
    }
}