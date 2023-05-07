package com.example.votingauthentication;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

public class login extends AppCompatActivity {

    private EditText aadharnumber;
    public static String phone,aadhar;
    private Button proceedB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);
        aadharnumber = (EditText)findViewById(R.id.aadharnumber);
        proceedB = findViewById(R.id.proceed);

        proceedB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceed();
            }
        });
    }



    public void proceed()
    {
        aadhar = aadharnumber.getText().toString();
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        DocumentReference docIdRef = rootRef.collection("USERS").document(aadhar.toString());
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //Log.d(TAG, "Document exists!");
                        phone = document.getString("PHONE");
                        phone = "+91"+phone;
                        Intent intent = new Intent(login.this,otpverification.class);
                        startActivity(intent);

                    } else {
                        Log.d(TAG, "Document does not exist!");
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });

    }
}