package com.example.project;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import java.net.FileNameMap;


public class EnterName extends AppCompatActivity {
    String EMAIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        EMAIL = intent.getStringExtra(MainActivity.EXTRA_EMAIL);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_enter_name);
        overridePendingTransition(R.anim.activity_in,R.anim.old_activity_out);
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
        switch (item.getItemId())
        {

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void moveToPassword(View view)
    {
        Intent intent = new Intent(this, EnterPassword.class);
        intent.putExtra(MainActivity.EXTRA_EMAIL, EMAIL);
        Log.e("ERROR", EMAIL);
        EditText Fname = findViewById(R.id.editText);
        intent.putExtra(MainActivity.EXTRA_FNAME, Fname.getText().toString());
        EditText Lname = findViewById(R.id.editText2);
        intent.putExtra(MainActivity.EXTRA_LNAME, Lname.getText().toString());


        startActivity(intent);
    }


}
