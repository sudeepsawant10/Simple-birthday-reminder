package com.sith.birthdayreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddPerson extends AppCompatActivity {
    private Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        String[] zodiacs= new String[]{"Aquarius", "Capricorn","Scorpio","Leo", "Ophiuchus", "Sagittarius", "Gemini", "Virgo"};

        // here you can use array or list
        ArrayAdapter adapter= new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, zodiacs);
        final Spinner itemsSpinner= (Spinner) findViewById(R.id.zodiacSpinner);
        itemsSpinner.setAdapter(adapter);
    }
}