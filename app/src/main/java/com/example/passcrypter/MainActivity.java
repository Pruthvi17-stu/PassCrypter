package com.example.passcrypter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.MenuProvider;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    ExtendedFloatingActionButton addfab1;
    ExtendedFloatingActionButton modefab2;
    ExtendedFloatingActionButton filefab3;
    MaterialToolbar mainmt;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;

        });
        Intent getHomeIntent = new Intent();
        String gethomeintentextra = getHomeIntent.getStringExtra("username");

        addfab1=findViewById(R.id.addexfab);
        modefab2=findViewById(R.id.modeexfab);
        filefab3=findViewById(R.id.filesfab3);
        mainmt=findViewById(R.id.toolbar);
        mainmt.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                // THIS IS THE MISSING PIECE:
                // Inflate your menu resource file.
                // Replace 'R.menu.main_menu' with the actual name of your menu file if it's different.
                menuInflater.inflate(R.menu.toolbar_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                // Your click handling logic here is correct.
                if (menuItem.getItemId() == R.id.action_settings) {
                    Intent settingsintent = new Intent(MainActivity.this, settingsactivty.class);
                    startActivity(settingsintent);
                    return true; // We handled the menu item click
                }
                return false; // We did not handle the menu item click
            }
        }, this, Lifecycle.State.RESUMED);




        addfab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addintent = new Intent(MainActivity.this,addandmanagepage.class);
                startActivity(addintent);


            }
        });
        modefab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




    }
}