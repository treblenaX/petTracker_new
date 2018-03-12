package com.example.elbertkcheng.pettracker_new;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class LogIn extends AppCompatActivity {
    EditText username_LOGIN;
    EditText password_LOGIN;
    Button signUp_LOGIN;
    Button ok_LOGIN;

    //Grabs the login JSON from assets folder
    public String loadJSONFromAsset(Context context)
    {
        String json = null;
        try {
            InputStream is = context.getAssets().open("loginDatabase.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    public boolean compareData(String username_input, String password_input) throws JSONException {
        JSONArray arr = new JSONArray(loadJSONFromAsset(this));

        for(int i = 0; i < arr.length(); i++)
        {
            String dbUsername = arr.getJSONObject(i).getString("username");
            String dbPassword = arr.getJSONObject(i).getString("password");

            if(dbUsername.equals(username_input) && dbPassword.equals(password_input))
            {
                return true;
            }
        }
        return false;
    }

    public void onClick(View v)
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //com.example.elbertkcheng.pettracker_new.Create the widgets
        username_LOGIN = (EditText)findViewById(R.id.username_LOGIN);
        password_LOGIN = (EditText)findViewById(R.id.password_LOGIN);
        signUp_LOGIN = (Button)findViewById(R.id.signUp_LOGIN);
        ok_LOGIN = (Button)findViewById(R.id.ok_LOGIN);

        //Set click listener for the Sign-Up Button.
        signUp_LOGIN.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i("ButtonTester", "Button is pressed!");
                startActivity(new Intent(LogIn.this, SignUp.class));
            }

        });

        //Set click listener for the OK Button.
        ok_LOGIN.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                Log.i("OK", "Ok button is pressed!");
                try {
                    Log.i("username", username_LOGIN.getText().toString());
                    Log.i("password", password_LOGIN.getText().toString());

                    if (compareData(username_LOGIN.getText().toString(), password_LOGIN.getText().toString()) == true)
                    {
                        startActivity(new Intent(LogIn.this, CalendarHomePage.class));
                    }
                    else
                    {
                        Log.i("LOGIN", "NO LOGIN FOUND");
                        Toast.makeText(getApplicationContext(), "Log-in information invalid, please try again!", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }


}
