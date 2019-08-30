package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisplayPatient extends AppCompatActivity {

    public final static String EXTRA_pid = "com.example.docapp.pid";
    String pid;
    TextView name,email;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_patient);
        Intent intent = getIntent();
        pid = intent.getStringExtra(com.example.project.DoctorHome.EXTRA_patientid2);
        if (pid == null)
        {
            Log.d("posts","caught you !!");
        }
        name =  findViewById(R.id.name);
        email =  findViewById(R.id.email);
        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Users").child(pid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
            String Fname=dataSnapshot.child("FNAME").getValue(String.class);
            String Lname=dataSnapshot.child("LNAME").getValue(String.class);
            name.setText(Fname+" "+Lname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });


    }

    public void goToPresciption(View view)
    {
        Intent intent = new Intent(this, WritePresciption.class);
        intent.putExtra(DisplayPatient.EXTRA_pid, pid);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
