package com.sith.birthdayreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPerson extends AppCompatActivity {
    private Context context=this;

    private static final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private static final DatabaseReference ref = firebaseDatabase.getReference();
    private Button saveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        String[] zodiacs= new String[]{"Aquarius", "Capricorn","Scorpio","Leo", "Ophiuchus", "Sagittarius", "Gemini", "Virgo"};

        // here you can use array or list
        ArrayAdapter adapter= new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, zodiacs);
        final Spinner itemsSpinner= (Spinner) findViewById(R.id.zodiacSpinner);
        itemsSpinner.setAdapter(adapter);

        saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.child("demo").setValue("Hello world");
            }
        });

    }
}