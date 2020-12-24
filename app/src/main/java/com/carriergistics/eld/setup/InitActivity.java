package com.carriergistics.eld.setup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.carriergistics.eld.R;
import com.carriergistics.eld.utils.Data;

/*
    Activity that is activated on first startup. Offers the user the option to either login or sign up

 */
public class InitActivity extends AppCompatActivity {
    TextView login;
    TextView signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        login = findViewById(R.id.chooseLoginBtn);
        signup = findViewById(R.id.chooseSignupBtn);
        Data.init(getApplicationContext());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SetupActivity.class);
                startActivity(intent);
            }
        });
    }
}