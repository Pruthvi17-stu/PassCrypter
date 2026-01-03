package com.example.passcrypter;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Objects;

public class addpasswordactvity extends AppCompatActivity {
    TextInputEditText accountname,username,password;
    Spinner logoSpinner;

    Button savebutton;
    ImageView backarrow;
    private EncryptionImplementer encryptionImplementer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.actitvity_addpassword);
        accountname=findViewById(R.id.account_name_edittext);
        username=findViewById(R.id.username_edittext);
        password=findViewById(R.id.password_edittext);
        logoSpinner=findViewById(R.id.logo_spinner);
        savebutton=findViewById(R.id.save_button);
        backarrow=findViewById(R.id.back_arrowadd);
        String[] appnames={"Default","Google","Instagram","Facebook","X","github","Spotify","Other"};
        //To display a list of items in appnames
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,appnames);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        logoSpinner.setAdapter(adapter);
        savebutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                savepassword();
                Intent intent= new Intent(addpasswordactvity.this, addandmanagepage.class);
                startActivity(intent);
            }
    });
        backarrow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        try{
            encryptionImplementer = new EncryptionImplementer(this);
        }
        catch (GeneralSecurityException | IOException e){
            Toast.makeText(this, "Critical Security Error. Exiting.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            finish();}



}
private void savepassword() {
    String accountName = Objects.requireNonNull(accountname.getText()).toString().trim();
    String userName = Objects.requireNonNull(username.getText()).toString().trim();
    String plainTextPassword = Objects.requireNonNull(password.getText()).toString().trim();
    String selectLogoname = logoSpinner.getSelectedItem().toString();
    if (accountName.isEmpty() || userName.isEmpty() || plainTextPassword.isEmpty()) {
        Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT).show();
        return;
    }
    String confirmationmessage = "Account Name: " + accountName + "" + "Username: " + userName + "" + "Password: " + plainTextPassword + "" + "App Logo: " + selectLogoname;
    Toast.makeText(this, confirmationmessage, Toast.LENGTH_SHORT).show();
    try {
        String encryptedpassword=encryptionImplementer.encrypt(plainTextPassword);
        final PasswordEntryValDefinition newEntry = new PasswordEntryValDefinition(accountName, userName, encryptedpassword, selectLogoname);
//        newEntry.setAccountName(accountName);
//        newEntry.setUsername(userName);
//        newEntry.setPassword(plainTextPassword);
//        newEntry.setLogo(selectLogoname);
        final AppDatabase db = AppDatabase.getDatbase(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                db.dataManager().insert(newEntry);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT);
                        finish();
                    }
                });
            }
        }).start();

    } catch (GeneralSecurityException e) {
        Toast.makeText(this, "Error.Could not save password", Toast.LENGTH_SHORT).show();
        e.printStackTrace();

    }
}

}
