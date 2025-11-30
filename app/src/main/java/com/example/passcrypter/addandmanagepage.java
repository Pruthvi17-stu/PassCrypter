package com.example.passcrypter;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class addandmanagepage extends AppCompatActivity {
   RecyclerView passwordrv;
    PasswordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.addandmanagepage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.addandmanage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;

        });
        passwordrv=findViewById(R.id.recyclerview);
        List<PasswordEntry> passwordEntries=new ArrayList<PasswordEntry>();
        passwordEntries.add(new PasswordEntry("Instagram","123instagram",R.drawable.instagram));
        passwordEntries.add(new PasswordEntry("Google","123google",R.drawable.google));
        passwordEntries.add(new PasswordEntry("spotify","123facebook",R.drawable.spotify));
        passwordrv.setLayoutManager(new LinearLayoutManager(addandmanagepage.this));
        passwordrv.setAdapter(new PasswordAdapter(addandmanagepage.this,passwordEntries));





    }
}
