package com.example.pocketdoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class Login extends AppCompatActivity {
EditText inputmobilenumber;
Button Continue;
private FirebaseAuth mAuth;
private String mVerificationId = "";
Button  Verifycode;
PinView getotp;
LinearLayout phone_lay;
LinearLayout otp_lay;

private PhoneAuthProvider.ForceResendingToken mResendToken;
private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        phone_lay = findViewById(R.id.phone_lay);

        Continue = findViewById(R.id.btn_getotp);
        Verifycode = findViewById(R.id.login);
        inputmobilenumber = findViewById(R.id.et_phone);
        getotp = findViewById(R.id.et_otp);
        otp_lay = findViewById(R.id.otp_layout);

        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String phoneno = inputmobilenumber.getText().toString().trim();


                if (!phoneno.isEmpty()){

                    sendVerificationcode("+91"+phoneno);

                }
                else {

                    Toast.makeText(getApplicationContext(),"Please Enter Phone number",Toast.LENGTH_LONG).show();

                }

            }
        });

        Verifycode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String otp = getotp.getText().toString().trim();

                if (!otp.isEmpty()){

                    verifyVerificationcode(otp);

                }
                else {

                    Toast.makeText(getApplicationContext(),"Please Enter otp number",Toast.LENGTH_LONG).show();

                }

            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                String code = credential.getSmsCode();
                if (code!=null){

                    getotp.setText(code);

                }

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.

                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                phone_lay.setVisibility(View.GONE);
                otp_lay.setVisibility(View.VISIBLE);

            }
        };

    }

    private void sendVerificationcode(String phoneno){

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneno)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void verifyVerificationcode(String code){

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        login(credential);

    }

    public void login(PhoneAuthCredential credential){

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(getApplicationContext(),LoginPage.class);
                            startActivity(intent);
                            finish();

                            // Update UI
                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                Toast.makeText(getApplicationContext(),"Code entered is Incorrect",Toast.LENGTH_LONG).show();
                                getotp.setText("");

                            }
                        }
                    }
                });
    }

}