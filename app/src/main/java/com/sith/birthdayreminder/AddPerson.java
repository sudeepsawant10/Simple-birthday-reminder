package com.sith.birthdayreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sith.birthdayreminder.firebase.FirebaseDbManager;
import com.sith.birthdayreminder.home.ProfileModel;

public class AddPerson extends AppCompatActivity {
    private Context context=this;
    private Button saveBtn;
    private EditText etPersonName, etPersonDob, etPersonPhone;
    private Spinner spinnerPersonZodiac;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        String[] zodiacs= new String[]{"Aquarius", "Capricorn","Scorpio","Leo", "Ophiuchus", "Sagittarius", "Gemini", "Virgo"};

        // here you can use array or list
        ArrayAdapter adapter= new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, zodiacs);
        final Spinner itemsSpinner= (Spinner) findViewById(R.id.spinnerPersonZodiac);
        itemsSpinner.setAdapter(adapter);

        etPersonName = findViewById(R.id.etPersonName);
        etPersonDob = findViewById(R.id.etPersonDob);
        etPersonPhone = findViewById(R.id.etPersonPhone);
        spinnerPersonZodiac = findViewById(R.id.spinnerPersonZodiac);

        saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ref.child("demo").setValue("Hello world");
                ProfileModel profile = new ProfileModel();
                profile.setName(etPersonName.getText().toString());
                profile.setDob(etPersonDob.getText().toString());
                profile.setPhoneNo(etPersonPhone.getText().toString());
                profile.setZodiacSign(spinnerPersonZodiac.getSelectedItem().toString());

                long randomId = System.currentTimeMillis();
                profile.setProfileId(randomId);

                FirebaseDbManager.addBirthdayProfile(context, profile, new FirebaseDbManager.FirebaseCallbackInterface() {
                    @Override
                    public void onComplete(Object object) {

                    }

                    @Override
                    public void onError() {

                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });

    }
}