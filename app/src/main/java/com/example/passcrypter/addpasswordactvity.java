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

import java.util.Objects;

public class addpasswordactvity extends AppCompatActivity {
    TextInputEditText accountname,username,password;
    Spinner logoSpinner;

    Button savebutton;
    ImageView backarrow;

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
        String[] appnames={"Default","Google","Instagram","Facebook","X","Other"};
        //To display a list of items in appnames
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,appnames);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        logoSpinner.setAdapter(adapter);
        savebutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                savepassword();
            }
    });
        backarrow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });



}
private void savepassword()
{
    String accountName= Objects.requireNonNull(Objects.requireNonNull(accountname.getText()).toString());
    String userName=Objects.requireNonNull(Objects.requireNonNull(username.getText()).toString());
    String Password=Objects.requireNonNull(Objects.requireNonNull(password.getText()).toString());
    String selectLogoname= logoSpinner.getSelectedItem().toString();
    if(accountName.isEmpty()||userName.isEmpty()||Password.isEmpty()){
        Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT).show();
        return;
    }
    String confirmationmessage="Account Name: "+accountName+""+"Username: "+userName+""+"Password: "+Password+""+"App Logo: "+selectLogoname;
    Toast.makeText(this, confirmationmessage, Toast.LENGTH_SHORT).show();
}


}
