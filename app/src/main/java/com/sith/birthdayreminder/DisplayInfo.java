package com.sith.birthdayreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DisplayInfo extends AppCompatActivity {
    private Context context=this;
    private FloatingActionButton btnEditProfileDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_info);
        btnEditProfileDetails = findViewById(R.id.btnEditProfileDetails);

        btnEditProfileDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,AddPerson.class));
            }
        });
    }
}