package com.example.pdmedical_store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

public class Login extends AppCompatActivity {

    private static final String TAG = "Ayush";
    TextView Signup, t1, t2;
    Button loginEnter;
    EditText email, pass;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN=123;
    Button verify;
    private FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null)
        {
            Intent intent=new Intent(getApplicationContext(),Home.class);
            startActivity(intent);

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        Signup=findViewById(R.id.signup);
        email=findViewById(R.id.loginemail);
        pass=findViewById(R.id.loginPassword);
        //Login Button
        loginEnter=findViewById(R.id.btnlogin);

        //Data Base
        fAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);

        //animate textview
        t1=findViewById(R.id.ForgotPassword);
        t2=findViewById(R.id.noaccount);

        mAuth=FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        Signup.setTranslationY(300);
        email.setTranslationY(300);
        pass.setTranslationY(300);
        loginEnter.setTranslationY(300);
        t1.setTranslationY(300);
        t2.setTranslationY(300);


        Signup.setAlpha(0);
        email.setAlpha(0);
        pass.setAlpha(0);
        loginEnter.setAlpha(0);
        t1.setAlpha(0);
        t2.setAlpha(0);

        Signup.animate().translationY(0).alpha(1).setDuration(900).start();
        email.animate().translationY(0).alpha(1).setDuration(500).start();
        pass.animate().translationY(0).alpha(1).setDuration(600).start();
        loginEnter.animate().translationY(0).alpha(1).setDuration(700).start();
        t1.animate().translationY(0).alpha(1).setDuration(800).start();
        t2.animate().translationY(0).alpha(1).setDuration(900).start();



        //Login Button Implementation
        loginEnter.setOnClickListener(v -> {
            String email12 = email.getText().toString().trim();
            String password1 = pass.getText().toString().trim();



            if(TextUtils.isEmpty(email12)){
                email.setError("Email Field is Required");
                return;
            }


            if(TextUtils.isEmpty(password1)){
                pass.setError("Password is Required");
                return;
            }


            progressBar.setVisibility(View.VISIBLE);
            //authenticate the user
            fAuth.signInWithEmailAndPassword(email12,password1).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    FirebaseMessaging.getInstance().getToken()
                            .addOnCompleteListener(new OnCompleteListener<String>() {
                                @Override
                                public void onComplete(@NonNull Task<String> task) {
                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                        return;
                                    }

                                    // Get new FCM registration token
                                    String userToken = task.getResult();
                                    String userID=fAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference=fStore.collection("Stores").document(userID);
                                    documentReference.update("fcmToken",userToken);
                                }
                            });

                    Toast.makeText(Login.this, "logged in SuccessFully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Home.class));
                }else
                {
                    Toast.makeText(Login.this, "Error !"+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }

            });


        });

        t1.setOnClickListener(v -> {
            EditText resetMail=new EditText(v.getContext());
            AlertDialog.Builder passwordResetDialog= new AlertDialog.Builder(v.getContext());
            passwordResetDialog.setTitle("Reset Password ?");
            passwordResetDialog.setMessage("Enter your Email To Receive Reset link.");
            passwordResetDialog.setView(resetMail);


            passwordResetDialog.setPositiveButton("Yes", (dialog, which) -> {
                //Extract the email and send reset link

                String mail=resetMail.getText().toString();
                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(unused -> Toast.makeText(Login.this,"Reset Likt Sent To Your Mail",Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(Login.this,"Error! Reset Email"+e.getMessage(),Toast.LENGTH_SHORT).show());

            });
            passwordResetDialog.setNegativeButton("No", (dialog, which) -> {
                // login Page
            });
            passwordResetDialog.create().show();

        });





        Signup.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Register.class);
            startActivity(intent);
        });

    }


}