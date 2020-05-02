package com.example.invoicegenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

public class Login extends AppCompatActivity {
    Button login;
    TextView forgotPassword;
    EditText name, password;
    static String username;
    static String userpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.loginbtn);
        name = findViewById(R.id.loginName);
        password = findViewById(R.id.loginPassword);
        forgotPassword = findViewById(R.id.loginForgotPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if(TextUtils.isEmpty(name.getText().toString().trim() )){
                    name.setError("Empty Field");
                }

                else if(TextUtils.isEmpty(password.getText().toString().trim())){
                    password.setError("Empty Field");
                }
                else{

                    username = name.getText().toString().trim();
                    userpass = password.getText().toString().trim();
                    Login(Login.this, username, userpass);
                }

            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ForgotPassword.class));
            }
        });

    }


    @SuppressLint("StaticFieldLeak")
    final public void Login(final Context context, final String username, String password){


        ContentValues contentValues = new ContentValues();

        contentValues.put("name", username);
        contentValues.put("password",password);



        new ServerCommunicator("http://lamp.ms.wits.ac.za/~s1838407/gmLogin.php", contentValues) {
            @Override
            protected void onPostExecute(String output) {

                if(output.equals("0")){

                    Toast.makeText(context,"login failed",Toast.LENGTH_SHORT).show();

                }

                else if(output.equals("1")){

                    Toast.makeText(context, "successfully logged in",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, Main2Activity.class);
                    startActivity(intent);
                    //intent.putExtra("USERNAME",TextUsername)
                    ((Activity) context).finish();
                    //finish();
                }

            }
        }.execute();

    }

}
