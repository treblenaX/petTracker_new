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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class SignUp extends AppCompatActivity {
    private static final String LOGIN_DATABASE = "loginDatabase.json";
    EditText username;
    EditText password;
    EditText passwordRetry;
    Button signupButton;


    private String readFromFile()
    {
        String str = "";
        try {
            InputStream inputStream = getApplicationContext().openFileInput(LOGIN_DATABASE);

            if (inputStream != null)
            {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(receiveString);
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
            Log.i("JSON Data", arr.getJSONObject(i).getString("username"));
            String dbPassword = arr.getJSONObject(i).getString("password");
            Log.i("JSON Data", arr.getJSONObject(i).getString("password"));

            if(dbUsername.equals(username_input) && dbPassword.equals(password_input))
            {
                return true;
            }
        }
        return false;
    }

    public void addJSONObject(String username, String password) throws JSONException, IOException {
        //Transfers the old data into the new data file.
        JSONArray arr = new JSONArray(readFromFile());
        JSONArray newArr = new JSONArray();

        for(int i = 0; i < arr.length(); i++)
        {
            String dbUsername = arr.getJSONObject(i).getString("username");
            String dbPassword = arr.getJSONObject(i).getString("password");

            JSONObject obj = new JSONObject();

            newArr.put(obj);
            obj.put("username", dbUsername);
            obj.put("password", dbPassword);
        }
        //Delete file for the new file to come in
        deleteFile(LOGIN_DATABASE);

        JSONObject obj = new JSONObject();

        newArr.put(obj);
        obj.put("username", username);
        obj.put("password", password);

        OutputStreamWriter osw = new OutputStreamWriter(openFileOutput(LOGIN_DATABASE, Context.MODE_PRIVATE));
        osw.write(newArr.toString());
        osw.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = (EditText)findViewById(R.id.username_SIGNUP);
        password = (EditText)findViewById(R.id.password_SIGNUP);
        passwordRetry = (EditText)findViewById(R.id.passwordRetry_SIGNUP);
        signupButton = (Button)findViewById(R.id.signup_SIGNUP);

        signupButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        try {
                            if (compareData(username.getText().toString(), password.getText().toString()) == false)
                            {
                                if (username.getText().toString().trim().isEmpty()|| password.getText().toString().trim().isEmpty() )
                                {
                                    Toast.makeText(getApplicationContext(), "Fields are empty, please enter in them!", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    if (password.getText().toString().equals(passwordRetry.getText().toString()))
                                    {
                                        addJSONObject(username.getText().toString(), password.getText().toString());
                                        Toast.makeText(getApplicationContext(), "Sign-up successful. Welcome to Pet Tracker!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignUp.this, CalendarHomePage.class);
                                        intent.putExtra("user", username.getText().toString());
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), "Please retype passwords, they don't match!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "There's already an existing user with this information! Please retry!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
        );
    }
}
