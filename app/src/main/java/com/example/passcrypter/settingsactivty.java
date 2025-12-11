package com.example.passcrypter;

import static android.content.Intent.createChooser;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.switchmaterial.SwitchMaterial;
import androidx.appcompat.app.AppCompatDelegate;

public class settingsactivty extends AppCompatActivity {
    public final String APP_PREFERENCES = "APPSETTINGS";
    public final String KEY_THEME = "isNIGHT_MODE";

    ImageView backarrow,appmode;
    SwitchMaterial apptheme;
    RelativeLayout reportbug, appversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.settingspage);
        backarrow = findViewById(R.id.back_arrow);
        apptheme = findViewById(R.id.themeswitch);
        appmode = findViewById(R.id.modeswitch);
        reportbug = findViewById(R.id.report_bug_button);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settingsactivty.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        apptheme.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            SharedPreferences spf = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
            SharedPreferences.Editor editor = spf.edit();
            editor.putBoolean(KEY_THEME, isChecked);
            editor.apply();
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                Toast.makeText(settingsactivty.this, "Night mode enabled", Toast.LENGTH_SHORT).show();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                Toast.makeText(settingsactivty.this, "Night mode disabled", Toast.LENGTH_SHORT).show();
            }


        }));
        appmode.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settingsactivty.this, appmode.class);
                startActivity(intent);
                finish();
            }
        });

        reportbug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipentEmail = "prutviindalkar@gmail.com";
                String subject = "Regarding the bugs in the app";
                String body = "Describe the bug you found";
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(android.net.Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipentEmail});
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, body);
                try {
                    startActivity(createChooser(intent, "Send Email"));
                    Toast.makeText(settingsactivty.this, "Opening email app", Toast.LENGTH_SHORT).show();

                } catch (ActivityNotFoundException e) {
                    Toast.makeText(settingsactivty.this, "No email app found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }}