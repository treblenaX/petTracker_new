package com.example.elbertkcheng.pettracker_new;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class SignUp extends AppCompatActivity {
    EditText username;
    EditText password;
    EditText passwordRetry;
    Button signupButton;

    public void addJSONObject(String username, String password) throws JSONException {
        JSONObject obj = new JSONObject();

        obj.put("Username", username);
        obj.put("Password", password);

    }

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
                    public void onClick(View view)
                    {
                        try {
                            addJSONObject(username.getText().toString(), password.getText().toString());
                            startActivity(new Intent(SignUp.this, LogIn.class));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
        );
    }
}
