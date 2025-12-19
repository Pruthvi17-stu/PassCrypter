package com.example.passcrypter;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;


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
        normalmode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
            {
                seriousmode.setChecked(false);
                savemode(false);
                updateCurrentappmodetext(false);
                Toast.makeText(appmode.this, "Normal Mode activitated", Toast.LENGTH_SHORT).show();


            }
        });
        seriousmode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
            {
               showSeriousModeConfirmationDialog();
            }
            else {
                normalmode.setChecked(true);


            }
        });
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(appmode.this, MainActivity.class);
                startActivity(intent);
            }
        });



    }
    public void updateCurrentappmodetext(boolean isSerious)
    {
        if(isSerious)
        {
            currentappmode.setText("Current text:Serious Mode");
        }
        else{currentappmode.setText("Current text:Normal Mode");
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
        BiometricManager biometrics =BiometricManager.from(appmode.this);
        int availableauthenticator=BiometricManager.Authenticators.BIOMETRIC_STRONG|BiometricManager.Authenticators.DEVICE_CREDENTIAL ;
        int result=biometrics.canAuthenticate(availableauthenticator);
        if (result == BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED) {

            new AlertDialog.Builder(this)
                    .setTitle("Screen Lock Required")
                    .setMessage("To use Serious Mode, you must first set up a screen lock (like a PIN or password). Would you like to set one up now?")
                    .setPositiveButton("Yes, Go to Settings", (dialog, which) -> {
                        Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                        startActivity(intent);
                        seriousmode.setChecked(false);
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        seriousmode.setChecked(false);
                    })
                    .setCancelable(false)
                    .show();
            return;
        }
        new AlertDialog.Builder(this)
                .setTitle("Enable Serious Mode?")
                .setMessage("The app will require authentication every time it is opened. Are you sure?")
                .setPositiveButton("Yes, Enable", (dialog, which) -> {

                    normalmode.setChecked(false);
                    savemode(true);
                    updateCurrentappmodetext(true);
                    Toast.makeText(this, "Serious Mode Activated", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {

                    seriousmode.setChecked(false);
                })
                .setCancelable(false)
                .show();

    }
}





