package com.example.elbertkcheng.pettracker_new;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;

public class SignUp extends AppCompatActivity {
    EditText username;
    EditText password;
    EditText passwordRetry;
    Button signupButton;

    public static void addJSONObject(String username, String password, JSONObject obj) throws JSONException {
        JSONObject object = new JSONObject();
        obj.put("Username", username);
        obj.put("Password", password);
    }

    public static void createJSONFile()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = (EditText)findViewById(R.id.username_SIGNUP);
        password = (EditText)findViewById(R.id.password_SIGNUP);
        passwordRetry = (EditText)findViewById(R.id.passwordRetry_SIGNUP);
        signupButton = (Button)findViewById(R.id.signup_SIGNUP);

        signupButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {

                    }
                }
        );
    }
}
