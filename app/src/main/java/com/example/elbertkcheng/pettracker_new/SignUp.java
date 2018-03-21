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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SignUp extends AppCompatActivity {

    /**
     * This class handles the sign-up of the users and writes the new sign-up data into a JSON file
     * which would then be referenced by in the LogIn.class
     */

    private static final String LOGIN_DATABASE = "loginDatabase.json";
    EditText username;
    EditText password;
    EditText passwordRetry;
    Button signupButton;


    private String readFromFile()
    {
        String str = "";
        try {
            //Opens the JSON file for reading
            InputStream inputStream = getApplicationContext().openFileInput(LOGIN_DATABASE);

            //If the file isn't empty gather all of the file text contents into String str and return it.
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
        //Assign JSONArray arr with the EXISTING arrays of data read from the file
        JSONArray arr = new JSONArray(readFromFile());

        /**For loop to cross-check the user-inputed information with the information in EACH object
         * and return true if both the username AND the password are equal. If not, return false.
         */

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

        //Existing JSONArray of JSONObjects grabbed from the database file
        JSONArray arr = new JSONArray(readFromFile());
        //New, empty JSONArray ready to store information
        JSONArray newArr = new JSONArray();

        /**
         * This method reads in the existing data of the JSONArray arr and saves it into the new
         * JSONArray newArr along with the new JSONObject of the new sign-up details
         * and then deletes the database and saves a new one with the updated data.
         *
         * TL;DR: Overwrites the LOGIN_DATABASE data
         */

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

        getSupportActionBar().setTitle("Sign-Up");
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
                            /**
                             * First if statement: Uses the earlier compareData method to see if there
                             * are existing usernames with the inputted username and only allows
                             * the process to go through if the username is UNIQUE.
                             *
                             * Second if statement: Checks if the username input or the password input is empty or only contains spaces.
                             *
                             * Third if statement: Check if the inputted password is equal to the
                             * password confirmation field. True = pass, false = error.
                             */
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
