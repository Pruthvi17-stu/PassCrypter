package com.example.passcrypter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PasswordHolder extends RecyclerView.ViewHolder{
    ImageView passwordappview;
    TextView itemtv,passwordtv;

    public PasswordHolder(@NonNull View itemView) {
        super(itemView);
        passwordappview=itemView.findViewById(R.id.password_app_view);
        itemtv=itemView.findViewById(R.id.itemtv);
        passwordtv=itemView.findViewById(R.id.passwordtv);
    }
}
