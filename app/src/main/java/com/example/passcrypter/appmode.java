package com.example.passcrypter;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class appmode extends AppCompatActivity {
    ImageView backarrow;
    Switch normalmode, seriousmode;
    TextView currentappmode;
    public static final String Prefs_name = "AppSettings";
    public static final String KEY_MODE = "isSeriousModeOn";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.appmodeactivity);
        backarrow = findViewById(R.id.back_arrow);
        normalmode = findViewById(R.id.normalmodeswitch);
        seriousmode = findViewById(R.id.seriousmodeswitch);
        currentappmode = findViewById(R.id.text_current_mode);



        loadandSetcurrentMode();
        setuplisteners();
    }

    public void loadandSetcurrentMode() {
        SharedPreferences spf = getSharedPreferences(Prefs_name, MODE_PRIVATE);
        boolean isSeriousModeOn = spf.getBoolean(KEY_MODE, false);
        normalmode.setOnCheckedChangeListener(null);
        seriousmode.setOnCheckedChangeListener(null);

        normalmode.setChecked(!isSeriousModeOn);
        seriousmode.setChecked(isSeriousModeOn);

        updateCurrentappmodetext(isSeriousModeOn);
        setuplisteners();
    }
    public void setuplisteners()
    {
        normalmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    seriousmode.setChecked(false);
                    savemode(false);
                    updateCurrentappmodetext(false);
                    Toast.makeText(appmode.this, "Normal Mode activitated", Toast.LENGTH_SHORT).show();


                }
            }
        });
        seriousmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                   showSeriousModeConfirmationDialog();
                }
                else {
                    normalmode.setChecked(true);


                }
            }
        });



    }
    public void updateCurrentappmodetext(boolean isSerious)
    {
        if(isSerious)
        {
            currentappmode.setText("Current text:Serious Mode");
        }
        else{
            currentappmode.setText("Current text:Normal Mode");

        }

    }
    public void savemode(boolean isSerious)
    {
        SharedPreferences spf = getSharedPreferences(Prefs_name, MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putBoolean(KEY_MODE, isSerious);
        editor.apply();
    }

    private void showSeriousModeConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Enable Serious Mode?")
                .setMessage("The app will require authentication every time it is opened. Are you sure?")
                .setPositiveButton("Yes, Enable", (dialog, which) -> {
                    // User confirmed. Complete the action.
                    normalmode.setChecked(false);
                    savemode(true);
                    updateCurrentappmodetext(true);
                    Toast.makeText(this, "Serious Mode Activated", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // User canceled. Revert the switch to the off position.
                    seriousmode.setChecked(false);
                })
                .setCancelable(false)
                .show();
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(appmode.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}





