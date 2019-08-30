package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.parse.ParseObject;


public class MakeAppointment extends AppCompatActivity {

    public String docId;
    EditText t1,t2,t3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);
        Intent intent = getIntent();
        docId = intent.getStringExtra(DisplayDoc.EXTRA_docId);
        t1 =  findViewById(R.id.editText);
        t2 = findViewById(R.id.editText2);
        t3 = findViewById(R.id.editText3);

    }

    public void confirmAppointment(View view)
    {
        DatabaseReference reference1= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("APPOINTMENT");
        reference1.child("DOCTOR ID").setValue(docId);
        reference1.child("REASON").setValue(t1.getText().toString());
        reference1.child("DATE").setValue(t2.getText().toString());
        reference1.child("TIME").setValue(t3.getText().toString());
        DatabaseReference reference2=FirebaseDatabase.getInstance().getReference().child("Users").child(docId).child("APPOINTMENT");
        reference2.child("PATIENT ID").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference2.child("REASON").setValue(t1.getText().toString());
        reference2.child("DATE").setValue(t2.getText().toString());
        reference2.child("TIME").setValue(t3.getText().toString());
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast2 = Toast.makeText(context, "BOOKED!!", duration);
        toast2.show();
        Intent intent = new Intent(this, PatientHome.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
