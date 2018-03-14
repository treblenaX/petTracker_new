package com.example.elbertkcheng.pettracker_new;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LogIn extends AppCompatActivity {
    private static final String LOGIN_DATABASE = "loginDatabase.json";
    EditText username_LOGIN;
    EditText password_LOGIN;
    Button signUp_LOGIN;
    Button ok_LOGIN;

    //Grabs the login JSON from assets folder

    private String readFromFile()
    {
        String str = "";
        try {
            InputStream inputStream = getApplicationContext().openFileInput(LOGIN_DATABASE);

            if (inputStream != null)
            {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String recieveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((recieveString = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(recieveString);
                }

                inputStream.close();
                str = stringBuilder.toString();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return str;
    }
    public boolean compareData(String username_input, String password_input) throws JSONException, FileNotFoundException {

        JSONArray arr = new JSONArray(readFromFile());

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

    public boolean checkDatabase() {
        File file = getApplicationContext().getFileStreamPath(LOGIN_DATABASE);
        if(!file.exists())
        {
            return false;
        }
        return true;
    }

    public void initializeLoginDatabase()
    {
        try {
            JSONArray arr = new JSONArray();
            JSONObject obj = new JSONObject();

            arr.put(obj);
            obj.put("username", "exampleuser");
            obj.put("password", "password");

            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput(LOGIN_DATABASE, Context.MODE_PRIVATE));
            osw.write(arr.toString());
            osw.close();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //Checks if database already exists
        if (checkDatabase() == false)
        {
            //Creates new database if none exists
            Log.i("CREATING DATABASE", "DATABASE DIDN'T EXIST, CREATING DATABASE");
            initializeLoginDatabase();
        }

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
                        String user = username_LOGIN.getText().toString();
                        Intent intent = new Intent(getApplicationContext(), CalendarHomePage.class);
                        intent.setClass(getApplicationContext(), CalendarHomePage.class);
                        intent.putExtra("user", user);
                        startActivity(intent);

                    }
                    else
                    {
                        Log.i("LOGIN", "NO LOGIN FOUND");
                        Toast.makeText(getApplicationContext(), "Log-in information invalid, please try again!", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });


    }


}
