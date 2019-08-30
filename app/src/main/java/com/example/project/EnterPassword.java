package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;


public class EnterPassword extends AppCompatActivity {

    String EMAIL;
    String FNAME;
    String LNAME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        EMAIL = intent.getStringExtra(MainActivity.EXTRA_EMAIL);
        FNAME = intent.getStringExtra(MainActivity.EXTRA_FNAME);
        LNAME = intent.getStringExtra(MainActivity.EXTRA_LNAME);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_enter_password);
        overridePendingTransition(R.anim.activity_in,R.anim.old_activity_out);

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

    public void moveToUserType(View view)
    {
        Intent intent = new Intent(this, EnterUserType.class);
        EditText pass = findViewById(R.id.editText);
        intent.putExtra( MainActivity.EXTRA_PASS, pass.getText().toString());
        intent.putExtra(MainActivity.EXTRA_EMAIL, EMAIL);
        intent.putExtra(MainActivity.EXTRA_FNAME, FNAME);
        intent.putExtra(MainActivity.EXTRA_LNAME, LNAME);
        Log.e("ERROR", "email "+EMAIL);
        Log.e("ERROR", "fname "+FNAME);
        Log.e("ERROR", "lname "+LNAME);

        startActivity(intent);
    }
}
