package com.example.pdmedical_store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Home extends AppCompatActivity {
    Button Complete;
    TextView orders;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        orders = findViewById(R.id.orderDetails);
        Complete=findViewById(R.id.complete);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        Complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CompleteRegister.class);
                startActivity(intent);
            }
        });

        String currentUserId = mAuth.getCurrentUser().getUid();

        db.collection("orders").whereEqualTo("storeid", currentUserId).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        String orderDetails = "";
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String description = document.get("description").toString();
                                String address = document.get("address").toString();
                                orderDetails += "Description = " + description + "\nAddress = " + address + "\n";
                                Log.d("Ayush", document.getId() + " => " + document.getData());
                            }
                            orders.setText(orderDetails);
                        } else {
                            Log.d("Ayush", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
}