package com.example.passcrypter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigInteger;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder>{


        private List<PasswordEntryValDefinition> databaseEntries;

        public PasswordAdapter(List<PasswordEntryValDefinition> entries) {

            this.databaseEntries = entries;
        }


        public static class PasswordViewHolder extends RecyclerView.ViewHolder {

            TextView accountNameTextView;
            TextView usernameTextView;
            ImageView logoImageView;

            public PasswordViewHolder(@NonNull View itemView) {
                super(itemView);

                accountNameTextView = itemView.findViewById(R.id.itemtv);
                usernameTextView = itemView.findViewById(R.id.passwordtv);
                logoImageView = itemView.findViewById(R.id.password_app_view);
            }
        }


        @NonNull
        @Override
        public PasswordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_password, parent, false);
            return new PasswordViewHolder(itemView); // Use the correct ViewHolder class name.
        }

        @Override
        public void onBindViewHolder(@NonNull PasswordViewHolder holder, int position) {

            if (databaseEntries != null) {
                PasswordEntryValDefinition currentEntry = databaseEntries.get(position);
                holder.accountNameTextView.setText(currentEntry.getAccountName());
                holder.usernameTextView.setText(currentEntry.getUsername());


            }
        }

        @Override
        public int getItemCount() {

            if (databaseEntries == null) {
                return 0;
            } else {
                return databaseEntries.size();
            }
        }
    }
