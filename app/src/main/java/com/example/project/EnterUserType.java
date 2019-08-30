package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EnterUserType extends AppCompatActivity {

    String USERTYPE;
    String EMAIL;
    String FNAME;
    String LNAME;
    String PASS;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_enter_user_type);
        overridePendingTransition(R.anim.activity_in,R.anim.old_activity_out);
        Intent intent = getIntent();
        EMAIL = intent.getStringExtra(MainActivity.EXTRA_EMAIL);
        PASS = intent.getStringExtra(MainActivity.EXTRA_PASS);
        FNAME = intent.getStringExtra(MainActivity.EXTRA_FNAME).toUpperCase();
        LNAME = intent.getStringExtra(MainActivity.EXTRA_LNAME).toUpperCase();
        mAuth=FirebaseAuth.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        switch (item.getItemId()) {

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void userSignUp(View view)
    {

       final String tag = "PARSE";
        Log.e(tag,EMAIL);
        Log.e(tag, FNAME);
        Log.e(tag, LNAME);
        Log.e(tag, USERTYPE);
       final Context context = getApplicationContext();
        final CharSequence text = "Signed Up!";
        final int duration = Toast.LENGTH_SHORT;
        mAuth.createUserWithEmailAndPassword(EMAIL,PASS)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            reference.setValue(USERTYPE);
                            Log.e(tag, "createUserWithEmail:success");
                            reference.child("FNAME").setValue(FNAME);
                            reference.child("LNAME").setValue(LNAME);
                            reference.child("USERTYPE").setValue(USERTYPE);
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            if(USERTYPE.equals("DOCTOR"))
                            {
                                Intent intent = new Intent(EnterUserType.this, EnterSpeciality.class);
                                startActivity(intent);
                            }
                            else
                                {
                                Intent intent = new Intent(EnterUserType.this, PatientHome.class);
                                startActivity(intent);
                            }

                        }
                        else
                            {
                                Log.e(tag, "createUserWithEmail:failure", task.getException());
                              Toast.makeText(EnterUserType.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioButton:
                if (checked)
                    USERTYPE = "DOCTOR";
                break;
            case R.id.radioButton2:
                if (checked)
                    USERTYPE = "PATIENT";
                break;
        }

    }

}
