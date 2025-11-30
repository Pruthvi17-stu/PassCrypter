package com.example.passcrypter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordHolder>{

     Context context;
     List<PasswordEntry> passwordEntries;

    public PasswordAdapter(Context context,List<PasswordEntry> passwordEntries) {
        this.context = context;
        this.passwordEntries = passwordEntries;
    }

    public PasswordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PasswordHolder(LayoutInflater.from(context).inflate(R.layout.item_password,parent,false)) ;
    }

    @Override
    public void onBindViewHolder(@NonNull PasswordHolder holder, int position) {
      holder.itemtv.setText(passwordEntries.get(position).getItemtitle());
      holder.passwordtv.setText(passwordEntries.get(position).getPassword());
      holder.passwordappview.setImageResource(passwordEntries.get(position).getAppimage());
    }

    @Override
    public int getItemCount() {
        return passwordEntries.size();
    }
}
