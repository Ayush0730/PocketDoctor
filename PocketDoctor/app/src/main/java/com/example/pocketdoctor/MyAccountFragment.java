package com.example.pocketdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import org.w3c.dom.Text;

public class MyAccountFragment extends Fragment {

    public MyAccountFragment(){



    }

    TextView Name;
    TextView Email;
    TextView PhoneNo;

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_my_account, container, false);

        Name = view.findViewById(R.id.namee);
        Email = view.findViewById(R.id.emaill);
        PhoneNo = view.findViewById(R.id.mobileNoo);

        if(getArguments() != null){
            String sn = getArguments().getString("firstname");
            String se = getArguments().getString("email");
            String sp = getArguments().getString("phoneNo");

            Name.setText(sn);
            Email.setText(se);
            PhoneNo.setText(sp);


        }


        return view;

    }
}