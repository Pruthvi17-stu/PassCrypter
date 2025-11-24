package com.example.passcrypter;

import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Loginmodulebackend extends AppCompatActivity {
    EditText username;
    EditText password;
    Button Submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.loginmodule);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginlayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        username=findViewById(R.id.etUsername);
        password=findViewById(R.id.etPassword);
        Submit=findViewById(R.id.submitbt);
        username.requestFocus();
        InputMethodManager userin = (InputMethodManager)getSystemService(Loginmodulebackend.INPUT_METHOD_SERVICE);
        userin.showSoftInput(username,InputMethodManager.SHOW_IMPLICIT);
        password.requestFocus();
        InputMethodManager passwordin=(InputMethodManager)getSystemService(Loginmodulebackend.INPUT_METHOD_SERVICE);
        passwordin.showSoftInput(password,InputMethodManager.SHOW_IMPLICIT);
        Submit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String UserNameInput=username.getText().toString().trim();
                String PasswordInput=username.getText().toString().trim();

                 if (UserNameInput.isEmpty() || PasswordInput.isEmpty()) {
                    Toast.makeText(Loginmodulebackend.this, "Both fields are required", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(Loginmodulebackend.this, "Login Sucessful", Toast.LENGTH_SHORT).show();

                    //Creating an intent from the login page to the home page
                     Intent homeintent = new Intent(Loginmodulebackend.this,MainActivity.class);
                     homeintent.putExtra("username" ,UserNameInput);
                     startActivity(homeintent);
                     finish();

                }

            }
        });



    }
}