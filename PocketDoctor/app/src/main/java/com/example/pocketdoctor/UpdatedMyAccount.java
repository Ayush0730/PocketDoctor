package com.example.pocketdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class UpdatedMyAccount extends AppCompatActivity {

    TextView Name;
    TextView Email;
    TextView PhoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updated_my_account2);

        Name = findViewById(R.id.name);
        Email = findViewById(R.id.email);
        PhoneNo = findViewById(R.id.mobileNo);

        Intent intent = getIntent();

        String sn = intent.getStringExtra("firstname")  ;
        String se = intent.getStringExtra("email");
        String sp = intent.getStringExtra("phoneNo");


        Name.setText(sn);
        Email.setText(se);
        PhoneNo.setText(sp);

    }
}