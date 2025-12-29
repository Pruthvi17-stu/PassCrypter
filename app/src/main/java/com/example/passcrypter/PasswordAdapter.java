package com.example.passcrypter;

import static android.app.ProgressDialog.show;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
               String Maintitle=currentEntry.getAccountName()+"("+currentEntry.getUsername()+")";
               holder.accountNameTextView.setText(Maintitle);
               String realpassword=currentEntry.getPassword();
               StringBuilder maskedPassword=new StringBuilder();
               for(int i=0;i<realpassword.length();i++){
                   maskedPassword.append("*");
               }
               if(realpassword.length()>16){
                   holder.usernameTextView.setText("*******");
               }
               else{
                   holder.usernameTextView.setText(maskedPassword);

               }
               String logo=currentEntry.getLogo().toLowerCase();
                switch(logo){
                    case "default":
                        holder.logoImageView.setImageResource(R.drawable.add);
                        break;
                    case "google":
                        holder.logoImageView.setImageResource(R.drawable.google);
                        break;
                    case "instagram":
                        holder.logoImageView.setImageResource(R.drawable.instagram);
                        break;
                    case "github":
                        holder.logoImageView.setImageResource(R.drawable.github);
                        break;
                        case "facebook":
                        holder.logoImageView.setImageResource(R.drawable.facebook);
                        break;
                        case "x":
                        holder.logoImageView.setImageResource(R.drawable.x);
                        break;
                      case "spotify":
                        holder.logoImageView.setImageResource(R.drawable.spotify);
                        break;
                        case "other":
                        holder.logoImageView.setImageResource(R.drawable.otherapp);
                        break;
                    default:
                        holder.logoImageView.setImageResource(R.drawable.add);

                }
                holder.itemView.setOnClickListener(v -> {
                    Context context=v.getContext();
                    new AlertDialog.Builder(context)
                            .setTitle("View Password")
                            .setMessage("View the password for " + currentEntry.getAccountName() + "?")
                            .setPositiveButton("Yes", (dialog, which) -> {

                                new AlertDialog.Builder(context)
                                        .setTitle("Password for " + currentEntry.getAccountName())
                                        .setMessage("Password: \n\n"+realpassword)
                                        .setPositiveButton("OK", null)
                                        .setNeutralButton("Copy", (dialog1, which1) ->{
                                            ClipboardManager clipboard=(ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                            ClipData clip =ClipData.newPlainText("Password",realpassword);
                                            clipboard.setPrimaryClip(clip);
                                            Toast.makeText(context, "Password copied", Toast.LENGTH_SHORT).show();
                                        })
                                        .show();
                            })
                            .setNegativeButton("No", null)
                            .show();

                });
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
