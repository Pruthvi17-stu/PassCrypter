package com.example.passcrypter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class settingsactivty extends AppCompatActivity {

    ImageView backarrow;
    SwitchMaterial apptheme,appmode;
    RelativeLayout reportbug,appversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.settingspage);
        backarrow=findViewById(R.id.back_arrow);
        apptheme=findViewById(R.id.themeswitch);
        appmode=findViewById(R.id.modeswitch);
        reportbug=findViewById(R.id.report_bug_button);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(settingsactivty.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }




}
