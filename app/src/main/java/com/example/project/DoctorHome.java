package com.example.project;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class DoctorHome extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public final static String EXTRA_patientid2 = "com.example.myfirstapp.PID2";
    ListView mlistview;
    ArrayAdapter<String> adapter;
    ArrayList<String> entries;
    ArrayList<String> Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);
        mlistview = findViewById(android.R.id.list);
        entries = new ArrayList<String>();
        Id=new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, entries);
        mlistview.setAdapter(adapter);
        mlistview.setOnItemClickListener(this);
        updateData();
    }

    public void updateData()
    {
       DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("APPOINTMENT");
       reference.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot)
         {
           final String PId=dataSnapshot.child("PATIENT ID").getValue(String.class);
           final String Reason=dataSnapshot.child("REASON").getValue(String.class);
           final String Time=dataSnapshot.child("TIME").getValue(String.class);
           final String Date=dataSnapshot.child("DATE").getValue(String.class);
           FirebaseDatabase.getInstance().getReference().child("Users").child(PId).addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot Snapshot)
               {
               String Fname=Snapshot.child("FNAME").getValue(String.class);
               String Lname=Snapshot.child("LNAME").getValue(String.class);
               adapter.setNotifyOnChange(true);
               adapter.add("You Have Appointment "+"with "+Fname+" "+Lname+" at "+Time+" on "+Date+" Due to "+Reason);
               Id.add(PId);
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });

         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
     });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            FirebaseAuth.getInstance().signOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        final String res = adapter.getItem(i);
        Toast.makeText(this, res + " selected", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(DoctorHome.this, DisplayPatient.class);
        intent.putExtra(DoctorHome.EXTRA_patientid2,Id.get(i));
        startActivity(intent);
    }
}
