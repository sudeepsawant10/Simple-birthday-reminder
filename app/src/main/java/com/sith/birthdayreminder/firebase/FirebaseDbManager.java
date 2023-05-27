package com.sith.birthdayreminder.firebase;

import android.content.Context;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sith.birthdayreminder.home.ProfileModel;

public class FirebaseDbManager {
    private static final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private static final DatabaseReference ref = firebaseDatabase.getReference();

    public static DatabaseReference getReference(String reference){
        return firebaseDatabase.getReference(reference);
    }

    public static void addBirthdayProfile(Context context, ProfileModel profile, FirebaseCallbackInterface firebaseCallbackInterface) {
        getReference("profiles").child(String.valueOf(profile.getProfileId())).setValue((ProfileModel)profile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "profile saved", Toast.LENGTH_SHORT).show();
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText(context, "Failed to save", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Failed to save", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void retrieveBirthdayProfiles(Context context, FirebaseCallbackInterface firebaseCallbackInterface) {
        getReference("profiles").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long count = snapshot.getChildrenCount();
                if (count > 0){
                    firebaseCallbackInterface.onComplete(snapshot.getChildren());
                }
                else {
                    firebaseCallbackInterface.onComplete(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                firebaseCallbackInterface.onCancel();
            }
        });
    }

    public interface FirebaseCallbackInterface{
        public void onComplete(Object object);
        public void onError();
        public void onCancel();
    }
}
