package com.example.pocketdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

public class SignUp extends AppCompatActivity {
Button continue_home;
TextInputLayout signame;
TextInputLayout sigLastname;
TextInputLayout sigEmail;
TextInputLayout sigPass;
TextInputLayout signPhoneNumber;
TextView WelcomeText;

    FirebaseDatabase rootNode;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        continue_home=findViewById(R.id.ContinueToMain);
        signame = findViewById(R.id.FirstName);
        sigLastname = findViewById(R.id.LastName);
        sigEmail = findViewById(R.id.Email);
        sigPass = findViewById(R.id.Password);
        signPhoneNumber = findViewById(R.id.Phone_no);

        WelcomeText = findViewById(R.id.welcometext);

        WelcomeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),LoginInside.class);
                startActivity(intent);

            }
        });

        continue_home.setOnClickListener(v -> {

            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("users");



            Intent intent = new Intent(getApplicationContext(),MyAccount.class);

            String Firstname = signame.getEditText().getText().toString();
            String Lastname = sigLastname.getEditText().getText().toString();
            String Email = sigEmail.getEditText().getText().toString();
            String Password = sigPass.getEditText().getText().toString();
            String PhoneNumber = signPhoneNumber.getEditText().getText().toString();

            Bundle newBundle = new Bundle();
            newBundle.putString("firstname",signame.getEditText().getText().toString());
            newBundle.putString("email",sigEmail.getEditText().getText().toString());
            newBundle.putString("phoneNo",signPhoneNumber.getEditText().getText().toString());
            MyAccountFragment objects = new MyAccountFragment();
            objects.setArguments(newBundle);

//                intent.putExtra("firstname",signame.getEditText().toString());
//                intent.putExtra("lastname",signame.getEditText().toString());
//                intent.putExtra("email",signame.getEditText().toString());
//                intent.putExtra("pass",signame.getEditText().toString());
//                intent.putExtra("phoneNo",signame.getEditText().toString());


            UserHelperClass helperClass = new UserHelperClass(Firstname,Lastname,Email,PhoneNumber,Password);

            reference.child(PhoneNumber).setValue(helperClass);

            startActivity(intent);
            finish();
        });

    }



}