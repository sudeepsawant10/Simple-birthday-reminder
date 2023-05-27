package com.sith.birthdayreminder;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sith.birthdayreminder.firebase.FirebaseDbManager;
import com.sith.birthdayreminder.home.MainActivity;
import com.sith.birthdayreminder.home.ProfileModel;
import com.sith.birthdayreminder.utils.ValidationHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddPerson extends AppCompatActivity {
    private Context context=this;
    private Button saveBtn;
    private EditText etPersonName, etPersonPhone;
    private Spinner spinnerPersonZodiac;
    private TextView tvPersonDob;
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
        tvPersonDob = findViewById(R.id.tvPersonDob);
        etPersonPhone = findViewById(R.id.etPersonPhone);
        spinnerPersonZodiac = findViewById(R.id.spinnerPersonZodiac);

        saveBtn = findViewById(R.id.saveBtn);

        final Calendar calendar = Calendar.getInstance();
        tvPersonDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int yr, int mnt, int d) {
                        calendar.set(yr,mnt,d);
                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                        String formattedDate = df.format(calendar.getTime());
//                        etPersonDob.setText(SimpleDateFormat.getDateInstance("dd-MMM-yyyy").format(calendar.getTime()));
                        tvPersonDob.setText(formattedDate);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ref.child("demo").setValue("Hello world");
                ProfileModel profile = new ProfileModel();
                String personName = etPersonName.getText().toString();
                String personDob = tvPersonDob.getText().toString();
                String personPhone = etPersonPhone.getText().toString();
                String personZodiacSign = spinnerPersonZodiac.getSelectedItem().toString();

                if (ValidationHelper.isTextEmpty(personName)){
                    Toast.makeText(context, "Please enter profile Name", Toast.LENGTH_SHORT).show();
                }
                else if (ValidationHelper.isTextEmpty(personDob)){
                    Toast.makeText(context, "Please enter Birth Date", Toast.LENGTH_SHORT).show();
                }
                else if (ValidationHelper.isTextEmpty(personPhone)){
                    Toast.makeText(context, "Please enter phone number", Toast.LENGTH_SHORT).show();
                }
                else {
                    profile.setName(personName);
                    profile.setDob(personDob);
                    profile.setPhoneNo(personPhone);
                    profile.setZodiacSign(personZodiacSign);

//                    int profileAge = calculateAge(calendar);
                    int profileAge = getAge(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

//                    profile.setAge(String.valueOf(profileAge));

                    long randomId = System.currentTimeMillis();
                    profile.setProfileId(randomId);

//                  Add profile to db
                    FirebaseDbManager.addBirthdayProfile(context, profile, new FirebaseDbManager.FirebaseCallbackInterface() {
                        public void onComplete(Object object) {
                            Intent intent = new Intent(context, MainActivity.class);
                            finish();
                            startActivity(intent);
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
        });

    }

    public int getAge(int year, int month, int dayOfMonth) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Period.between(
                    LocalDate.of(year, month, dayOfMonth),
                    LocalDate.now()
            ).getYears();
        }
        return 0;
    }

}