package com.example.elbertkcheng.pettracker_new;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LogIn extends AppCompatActivity {

    public void onClick(View v)
    {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //com.example.elbertkcheng.pettracker_new.Create the widgets
        EditText accountEmail = (EditText)findViewById(R.id.accountEmail);
        EditText password = (EditText)findViewById(R.id.Password);
        Button signUp = (Button)findViewById(R.id.SignUp);
        Button ok_button = (Button)findViewById(R.id.LogInOKButton);

        //Set click listener for the Sign-Up Button.
        signUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i("ButtonTester", "Button is pressed!");
                startActivity(new Intent(LogIn.this, SignUp.class));
            }

        });

        //Set click listener for the OK Button.
        ok_button.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                Log.i("OK", "Ok button is pressed!");
                startActivity(new Intent(LogIn.this, CalendarHomePage.class));
            }
        });


    }


}
