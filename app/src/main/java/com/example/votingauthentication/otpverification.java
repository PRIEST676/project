package com.example.votingauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class otpverification extends AppCompatActivity {


    private Button btnGenerateOTP, btnSignIn;
    public TextView phoneTxtView;
    public String phoneNumber = login.phone;
    public String otp;
    EditText etOTP;
    FirebaseAuth auth;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private String verificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otppage);

        phoneTxtView = findViewById(R.id.first);
        phoneTxtView.setText(phoneNumber);
        etOTP = findViewById(R.id.pinview);
        btnGenerateOTP = findViewById(R.id.send_otp);
        btnSignIn = findViewById(R.id.verify_otp);

        StartFirebaseLogin();

        btnGenerateOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                        phoneNumber,                     // Phone number to verify
//                        60,                           // Timeout duration
//                        TimeUnit.SECONDS,                // Unit of timeout
//                        otpverification.this,        // Activity (for callback binding)
//                        mCallback);                      // OnVerificationStateChangedCallbacks

                PhoneAuthProvider.verifyPhoneNumber(
                        PhoneAuthOptions
                                .newBuilder(FirebaseAuth.getInstance())
                                .setActivity(otpverification.this)
                                .setPhoneNumber(phoneNumber)
                                .setTimeout(60L, TimeUnit.SECONDS)
                                .setCallbacks(mCallback)
                                .build());
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp=etOTP.getText().toString();

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);

                SigninWithPhone(credential);
            }
        });
    }

    private void SigninWithPhone(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(otpverification.this,camerapage.class));
                            finish();
                        } else {
                            Toast.makeText(otpverification.this,"Incorrect OTP",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void StartFirebaseLogin() {

        auth = FirebaseAuth.getInstance();
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(otpverification.this,"verification completed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(otpverification.this,"verification fialed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationCode = s;
                Toast.makeText(otpverification.this,"Code sent",Toast.LENGTH_SHORT).show();
            }
        };
    }


}
