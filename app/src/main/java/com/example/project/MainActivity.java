package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_EMAIL = "com.example.myfirstapp.EMAIL";
    public final static String EXTRA_FNAME = "com.example.myfirstapp.FNAME";
    public final static String EXTRA_LNAME = "com.example.myfirstapp.LNAME";
    public final static String EXTRA_PASS = "com.example.myfirstapp.PASS";
    public final static String EXTRA_UTYPE = "com.example.myfirstapp.UTYPE";
    private FirebaseAuth mAuth;
    EditText email;
    EditText pass;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.editText);
        pass =  findViewById(R.id.editText1);
        mAuth=FirebaseAuth.getInstance();
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

    public void userSignIn(View view)
    {

        mAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            Log.e("APP", "signInWithEmail:success");
                           final String user=FirebaseAuth.getInstance().getCurrentUser().getUid();
                            DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Users");
                            reference.addValueEventListener(new ValueEventListener()
                             {
                                 @Override
                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                 {
                                     if(dataSnapshot.child(user).child("USERTYPE").getValue().equals("DOCTOR")) {
                                              //  Toast.makeText(MainActivity.this, "Doctor", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(MainActivity.this, DoctorHome.class);
                                                startActivity(intent);
                                            }
                                            else {
                                               // Toast.makeText(MainActivity.this, "Patient", Toast.LENGTH_LONG).show();

                                                Intent intent = new Intent(MainActivity.this, PatientHome.class);
                                                startActivity(intent);

                                            }
                                        }
                                 @Override
                                 public void onCancelled(@NonNull DatabaseError databaseError) {

                                 }
                             });


                        }
                        else {

                            Log.e("APP", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void moveToEmail(View view)
    {
        Intent intent = new Intent(this, EnterEmail.class);
        startActivity(intent);
    }
}
