package com.example.passcrypter;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;


import androidx.biometric.BiometricManager;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.MenuProvider;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Lifecycle;


import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    ExtendedFloatingActionButton addfab1;
    ExtendedFloatingActionButton modefab2;

    FloatingActionButton addpasswordfab4;
    MaterialToolbar mainmt;
    LinearProgressIndicator strengthprogress;
    TextView strengthdescript;
    AppDatabase db;
    EncryptionImplementer encryptionImplementer;

    private java.util.concurrent.Executor executor;
    private androidx.biometric.BiometricPrompt biometricPrompt;
    private androidx.biometric.BiometricPrompt.PromptInfo promptInfo;
    private static final Set<String> COMMON_PASSWORDS = new HashSet<>(Arrays.asList(
            // Original list
            "123456", "12345678", "123456789", "password", "qwerty", "abcdef", "admin",

            // Numeric patterns
            "12345", "111111", "000000", "123123", "654321", "1234567",

            // Keyboard patterns
            "qwerasdf", "zxcvbnm", "asdfghjkl", "qwertyuiop", "poiuytrewq",

            // Common words and variations
            "welcome", "login", "guest", "root", "secret", "master", "access",
            "password123", "pass123", "letmein", "p@ssword", "user",

            // Time-based (Often used by defaults)
            "2024", "2025", "2026", "spring", "summer", "winter", "autumn"
    ));




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        db=AppDatabase.getDatbase(getApplicationContext());
        try{
            encryptionImplementer=new EncryptionImplementer(this);
        }
        catch (Exception e){
            Toast.makeText(this, "Cannot Check Password Strength.", Toast.LENGTH_SHORT).show();
            return;
        }

        strengthprogress=findViewById(R.id.strength_progress_bar);
        strengthdescript=findViewById(R.id.strength_description);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;

        });
        SharedPreferences spf = getSharedPreferences(appmode.Prefs_name, MODE_PRIVATE);
        boolean isSeriousModeOn = spf.getBoolean(appmode.KEY_MODE, false);

        if (isSeriousModeOn) {
            setupAndShowBiometricPrompt();

        } else {
            initalizeApp();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        // Always analyze passwords when the screen becomes visible
        passwordanalyzer();
    }

    // --- FULLY CORRECTED PASSWORD ANALYZER METHOD ---
    public void passwordanalyzer() {
        // 1. START A NEW BACKGROUND THREAD to handle database and decryption
        new Thread(() -> {
            List<PasswordEntryValDefinition> allEntries = db.dataManager().getAllEntries();

            if (allEntries == null || allEntries.isEmpty()) {
                runOnUiThread(() -> {
                    strengthdescript.setText("No passwords saved yet.");
                    strengthprogress.setProgress(0);
                    strengthprogress.setIndicatorColor(getColor(com.google.android.material.R.color.design_default_color_primary));
                });
                return;
            }

            int weakPasswordCount = 0;
            for (PasswordEntryValDefinition entry : allEntries) {
                // 2. USE A TRY-CATCH BLOCK for decryption
                try {
                    // 3. USE CORRECT METHOD NAME: decrypt()
                    String decryptedPassword = encryptionImplementer.decryptedkey(entry.getPassword());
                    if (isPasswordWeak(decryptedPassword)) {
                        weakPasswordCount++;
                    }
                } catch (GeneralSecurityException e) {
                    // Log error but don't crash the app
                    e.printStackTrace();
                }
            }

            int totalPasswords = allEntries.size();
            int weakPercentage = (int) (((double) weakPasswordCount / totalPasswords) * 100);

            // Create final variables for use inside the lambda
            final int finalWeakCount = weakPasswordCount;

            // 4. UPDATE THE UI ON THE MAIN THREAD
            runOnUiThread(() -> {
                strengthdescript.setText(finalWeakCount + " out of " + totalPasswords + " passwords are weak");
                strengthprogress.setProgress(weakPercentage, true);

                if (weakPercentage == 0) {
                    strengthprogress.setIndicatorColor(ContextCompat.getColor(this, android.R.color.holo_green_dark)); // All strong
                } else if (weakPercentage < 30) {
                    strengthprogress.setIndicatorColor(ContextCompat.getColor(this, android.R.color.holo_orange_light)); // Some weak
                } else {
                    strengthprogress.setIndicatorColor(ContextCompat.getColor(this, android.R.color.holo_red_dark)); // Many weak
                }
            });
        }).start(); // 5. THIS .start() CALL NOW CORRECTLY APPLIES TO THE NEW THREAD
    }
    public boolean isPasswordWeak(String password){
        if (password == null || password.length() < 8) {
            return true;
        }
        if (COMMON_PASSWORDS.contains(password.toLowerCase())) {
            return true;
        }
        if (password.matches("[0-9]+")) {
            return true;
        }
        if (password.matches("[a-z]+")) {
            return true;
        }
        if (password.matches("[A-Z]+")) {
            return true;
        }
        return false;
    }






    private void setupAndShowBiometricPrompt() {
        executor = ContextCompat.getMainExecutor(this);

        biometricPrompt = new androidx.biometric.BiometricPrompt(MainActivity.this, executor, new androidx.biometric.BiometricPrompt.AuthenticationCallback() {

            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(), "Authentication required. Exiting.", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull androidx.biometric.BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                Toast.makeText(getApplicationContext(), "Authentication succeeded!", Toast.LENGTH_SHORT).show();
                initalizeApp();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();

                Toast.makeText(getApplicationContext(), "Authentication failed. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });


        promptInfo = new androidx.biometric.BiometricPrompt.PromptInfo.Builder()
                .setTitle("Authentication Required")
                .setSubtitle("Log in using your pattern, PIN, or biometric")

                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG | BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build();

        biometricPrompt.authenticate(promptInfo);
    }


    public void initalizeApp() {


        Intent getHomeIntent = new Intent();
        String gethomeintentextra = getHomeIntent.getStringExtra("username");

        addfab1 = findViewById(R.id.addexfab);
        modefab2 = findViewById(R.id.modeexfab);

        mainmt = findViewById(R.id.toolbar);
        addpasswordfab4=findViewById(R.id.fab1);
        mainmt.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {

                menuInflater.inflate(R.menu.toolbar_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.action_settings) {
                    Intent settingsintent = new Intent(MainActivity.this, settingsactivty.class);
                    startActivity(settingsintent);
                    return true;
                }
                if(menuItem.getItemId()==R.id.action_help)
                {
                    Intent helpintent=new Intent(MainActivity.this, HelpActivity.class);
                    startActivity(helpintent);
                }
                return false;
            }
        }, MainActivity.this, Lifecycle.State.RESUMED);


        addfab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addintent = new Intent(MainActivity.this, addandmanagepage.class);
                startActivity(addintent);


            }
        });
        modefab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, appmode.class);
                startActivity(intent);


            }
        });
        addpasswordfab4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, addpasswordactvity.class);
                startActivity(intent);

            }
        });


    }
}





