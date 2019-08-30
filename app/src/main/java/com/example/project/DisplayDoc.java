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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.project.HomeFragment.EXTRA_selectedDoc;


public class DisplayDoc extends AppCompatActivity
{
    public final static String EXTRA_docId = "com.example.myfirstapp.DOCID";
    public final static String EXTRA_curId = "com.example.myfirstapp.CURID";
    TextView name,email,phone,speciality,hname,hadd;
    String docId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_doc);
        name = findViewById(R.id.textView2);
        email = findViewById(R.id.textView4);
        phone= findViewById(R.id.textView6);
        speciality = findViewById(R.id.textView8);
        hname=findViewById(R.id.textView10);
        hadd=findViewById(R.id.textView12);
        Intent intent = getIntent();
        final String docName = intent.getStringExtra(EXTRA_selectedDoc);
        final String fname=docName.split(" ")[0].trim().toUpperCase();
        final String lname=docName.split(" ")[1].trim().toUpperCase();
        name.setText(docName);
        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
            for (DataSnapshot snapshot: dataSnapshot.getChildren())
            {
                if(snapshot.child("FNAME").getValue(String.class).equals(fname)&&snapshot.child("LNAME").getValue(String.class).equals(lname)&&snapshot.child("USERTYPE").getValue(String.class).equals("DOCTOR"))
                {

                    //phone.setText(snapshot.child("PHONE").getValue(String.class));
                    speciality.setText(snapshot.child("SPECIALITY").getValue(String.class));
                    hname.setText(snapshot.child("HOSPITAL NAME").getValue(String.class));
                    hadd.setText(snapshot.child("HOSPITAL ADDRESS").getValue(String.class));
                    docId=snapshot.getKey();
                }
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(DisplayDoc.this, "error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void makeAppointment(View view)
    {
        Intent intent = new Intent(this, MakeAppointment.class);
        intent.putExtra(DisplayDoc.EXTRA_docId, docId);
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
        if (id == R.id.action_settings)
        {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
