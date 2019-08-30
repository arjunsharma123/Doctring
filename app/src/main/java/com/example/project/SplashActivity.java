package com.example.project;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity
{
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {

                mAuth=FirebaseAuth.getInstance();
                if(mAuth.getCurrentUser()!=null)
                {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Toast.makeText(SplashActivity.this, dataSnapshot.child("FNAME").getValue(String.class), Toast.LENGTH_LONG).show();
                            if (dataSnapshot.child("USERTYPE").getValue(String.class).equals("DOCTOR")) {
                                //Toast.makeText(SplashActivity.this, "Doctor", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SplashActivity.this, DoctorHome.class);
                                startActivity(intent);
                                finish();
                            } else if(dataSnapshot.child("USERTYPE").getValue(String.class).equals("PATIENT"))
                            {
                                //Toast.makeText(SplashActivity.this, "Doctor", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SplashActivity.this, PatientHome.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                                {
                                    Toast.makeText(SplashActivity.this, "error", Toast.LENGTH_LONG).show();

                                }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else
                {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },500);


    }
}
