package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.parse.ParseObject;
import com.parse.ParseUser;


public class WritePresciption extends AppCompatActivity {

    String pid;
    EditText pres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_presciption);
        Intent intent = getIntent();
        pid = intent.getStringExtra(DisplayPatient.EXTRA_pid);
        if(pid == null)
        {
            Log.d("posts","Caugth you");
        }
        else {
            Log.d("posts", pid);
        }
        pres =  findViewById(R.id.editText);


    }

    public void sendPres(View view)
    {
        DatabaseReference reference1= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("PRESCRIPTION");
        reference1.child(pid).child("MEDICINES").setValue(pres.getText().toString().toUpperCase());
        DatabaseReference reference2=FirebaseDatabase.getInstance().getReference().child("Users").child(pid).child("PRESCRIPTION").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference2.child("MEDICINES").setValue(pres.getText().toString().toUpperCase());
        Context context = getApplicationContext();
        CharSequence text = "Sent Prescription!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        Intent intent= new Intent(WritePresciption.this,DoctorHome.class);
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
