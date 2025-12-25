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

    PasswordAdapter adapter;
    // Variables for this screen
    private AppDatabase db;
    private RecyclerView recyclerView;
    private PasswordAdapter passwordAdapter;

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
        db = AppDatabase.getDatbase(this);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));





    }
@Override
    protected void onResume() {
        super.onResume();

        loadPasswordEntries();
    }

    public void loadPasswordEntries() {

        new Thread(() -> {

            final List<PasswordEntryValDefinition> entries = db.dataManager().getAllEntries();


            runOnUiThread(() -> {

                passwordAdapter = new PasswordAdapter(entries);
                recyclerView.setAdapter(passwordAdapter);
            });
        }).start();
    }
}




