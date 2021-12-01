package com.example.pocketdoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginInside extends AppCompatActivity {

    EditText lphone;
    EditText lpass;

    Button Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_inside);

        lphone = findViewById(R.id.login_phone);
        lpass = findViewById(R.id.login_pass);
        Next = findViewById(R.id.NextScreen);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUser();
            }
        });



//        loginUser();

    }

//    public void loginUser(){
//
//        isUser();
//
//    }

    public void isUser(){

        String userenterphone = lphone.getText().toString().trim();
        String userenterpass = lpass.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        Query checkuser = reference.orderByChild("phoneNo").equalTo(userenterphone);
        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){

                    lphone.setError(null);

                    String passwordfromdb = snapshot.child(userenterphone).child("password").getValue(String.class);

                    if (passwordfromdb.equals(userenterpass)){

                        lphone.setError(null);

                        String firstnamefromdb = snapshot.child(userenterphone).child("firstname").getValue(String.class);
                        String lastnamefromdb = snapshot.child(userenterphone).child("lastname").getValue(String.class);
                        String emailfromdb = snapshot.child(userenterphone).child("email").getValue(String.class);
                        String phonefromdb = snapshot.child(userenterphone).child("phoneNo").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(),MyAccount.class);
                        intent.putExtra("fname",firstnamefromdb);
                        intent.putExtra("lname",lastnamefromdb);
                        intent.putExtra("emaill",emailfromdb);
                        intent.putExtra("pno",phonefromdb);

                        startActivity(intent);
                        finish();

                    }
                    else {

                        lpass.setError("Wrong Password");

                    }

                }
                else{

                    lphone.setError("No Such user exists");
                    lphone.requestFocus();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}