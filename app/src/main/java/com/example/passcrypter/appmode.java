package com.example.passcrypter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class appmode extends AppCompatActivity {
    ImageView backarrow;
    Switch normalmode, seriousmode;
    TextView currentappmode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.appmodeactivity);
        backarrow = findViewById(R.id.back_arrow);
        normalmode = findViewById(R.id.normalmodeswitch);
        seriousmode = findViewById(R.id.seriousmodeswitch);
        currentappmode = findViewById(R.id.text_current_mode);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(appmode.this, MainActivity.class);
                startActivity(intent);
            }


        });


    }

}

