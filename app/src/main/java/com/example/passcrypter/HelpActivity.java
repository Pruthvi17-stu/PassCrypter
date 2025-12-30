package com.example.passcrypter;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class HelpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helpactivity);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setTitle("Help & FAQ's");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ImageButton backarrow= findViewById(R.id.back_arrow);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HelpActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        TextView helpcontenttv =findViewById(R.id.help_content_textview);
        String helpTexthtml=
                "<h3>App Security & Encryption</h3>" +
                        "<p><b>Q: Is my data secure? How does encryption work?</b><br/>" +
                        "<b>A:</b> Yes. Your passwords are encrypted using AES-256, a robust, industry-standard encryption algorithm. The encryption keys are securely stored in the Android Keystore System. Your data is encrypted on your device and is never sent to any external server.</p>" +

                        "<h3>Core App Features</h3>" +
                        "<p><b>Q: How do I add a new password?</b><br/>" +
                        "<b>A:</b> Tap the floating plus (+) button on the main screen, fill in the details, and tap 'Save'.</p>" +

                        "<p><b>Q: How do I view or copy a saved password?</b><br/>" +
                        "<b>A:</b> Tap on any entry in the list. A confirmation box will appear. Tap 'Yes' to view the password. In the next dialog, you can tap the 'Copy' button to copy the password to your clipboard.</p>" +

                        "<p><b>Q: How do I delete all my passwords?</b><br/>" +
                        "<b>A:</b> Go to the 'Add & Manage Passwords' screen and tap the 'Clear All' button. <b>Warning:</b> This action is permanent and cannot be undone.</p>" +

                        "<h3>App Modes & Settings</h3>" +
                        "<p><b>Q: What is 'Serious Mode'?</b><br/>" +
                        "<b>A:</b> 'Serious Mode' requires you to authenticate with your fingerprint, PIN, or pattern every time you open the app.</p>" +

                        "<h3>Contact & Support</h3>" +
                        "<p><b>Q: I have a suggestion. How can I contact you?</b><br/>" +
                        "<p><b>A:</b> We appreciate your feedback! Please send an email to <b>pruthviindalkar@gmail.com</b>.</p>";
        helpcontenttv.setText(Html.fromHtml(helpTexthtml, Html.FROM_HTML_MODE_LEGACY));
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}
