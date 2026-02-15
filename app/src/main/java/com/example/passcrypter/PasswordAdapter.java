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
        private final EncryptionImplementer encryptionImplementer;
        private final AppDatabase db;

        public PasswordAdapter(List<PasswordEntryValDefinition> entries, EncryptionImplementer encryptionImplementer, AppDatabase db) {
            this.encryptionImplementer=encryptionImplementer;
            this.databaseEntries = entries;
            this.db=db;
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
                String Maintitle = currentEntry.getAccountName() + "(" + currentEntry.getUsername() + ")";
                holder.accountNameTextView.setText(Maintitle);
                String realpassword = currentEntry.getPassword();
                StringBuilder maskedPassword = new StringBuilder();
                for (int i = 0; i < realpassword.length(); i++) {
                    maskedPassword.append("*");
                }
                if (realpassword.length() > 16) {
                    holder.usernameTextView.setText("*******");
                } else {
                    holder.usernameTextView.setText(maskedPassword);

                }
                String logo = currentEntry.getLogo().toLowerCase();
                switch (logo) {
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
                    Context context = v.getContext();
                    new AlertDialog.Builder(context)
                            .setTitle("View Password")
                            .setMessage("View the password for " + currentEntry.getAccountName() + "?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                try {
                                    // DECRYPT THE PASSWORD HERE
                                    String decryptedPassword = encryptionImplementer.decryptedkey(realpassword);

                                    // Now show the decrypted password
                                    new AlertDialog.Builder(context)
                                            .setTitle("Password for " + currentEntry.getAccountName())
                                            .setMessage("Password: \n\n" + decryptedPassword) // Use the decrypted password
                                            .setPositiveButton("OK", null)
                                            .setNeutralButton("Copy", (dialog1, which1) -> {
                                                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                                ClipData clip = ClipData.newPlainText("Password", decryptedPassword); // Copy decrypted password
                                                clipboard.setPrimaryClip(clip);
                                                Toast.makeText(context, "Password copied", Toast.LENGTH_SHORT).show();
                                            })
                                            .show();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(context, "Could not decrypt password.", Toast.LENGTH_SHORT).show();
                                }
                            }).setNegativeButton("No",null);

                });
                // --- LONG CLICK LISTENER (for updating/deleting) ---
                holder.itemView.setOnLongClickListener(v -> {
                    Context context = v.getContext();
                    PasswordEntryValDefinition entryForOptions = databaseEntries.get(holder.getAdapterPosition());
                    final CharSequence[] options = {"Update Password", "Delete", "Cancel"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Options for " + entryForOptions.getAccountName());
                    builder.setItems(options, (dialog, which) -> {
                        if (which == 0) { // "Update Password" was clicked

                            Toast.makeText(context, "Update clicked!", Toast.LENGTH_SHORT).show();
                            showUpdatePasswordDialog(context, entryForOptions, holder.getAdapterPosition());
                        } else if (which == 1) { // "Delete" was clicked
                            Toast.makeText(context, "Delete not implemented yet", Toast.LENGTH_SHORT).show();
                        } else if (which == 2) { // "Cancel" was clicked
                            dialog.dismiss();
                        }
                    });

                    builder.show();
                    return true;
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

    private void showUpdatePasswordDialog(Context context, PasswordEntryValDefinition entry, int position) {
        final int MAX_CHANGES = 5; // Your limit
        int changesRemaining = MAX_CHANGES - entry.getChangecount();

        if (changesRemaining <= 0) {
            new AlertDialog.Builder(context)
                    .setTitle("Change Limit Reached")
                    .setMessage("You cannot change this password anymore.")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        // Inflate the custom dialog layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.update_password_dialog, null);

        // Get UI elements from the dialog
        final TextView title = dialogView.findViewById(R.id.update_dialog_title);
        final TextView limitText = dialogView.findViewById(R.id.update_dialog_change_limit);
        final com.google.android.material.textfield.TextInputEditText newPasswordInput = dialogView.findViewById(R.id.update_dialog_new_password_input);

        title.setText("Update: " + entry.getAccountName());
        limitText.setText(changesRemaining + " changes remaining.");

        // Create and show the dialog
        new AlertDialog.Builder(context)
                .setView(dialogView)
                .setPositiveButton("Update", (d, w) -> {
                    String newPassword = newPasswordInput.getText().toString().trim();
                    if (!newPassword.isEmpty()) {
                        updatePasswordInDatabase(context, entry, newPassword, position);
                    } else {
                        Toast.makeText(context, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void updatePasswordInDatabase(Context context, PasswordEntryValDefinition entry, String newPassword, int position) {
        new Thread(() -> {
            try {
                // Encrypt the new password
                String encryptedPassword = encryptionImplementer.encrypt(newPassword);
                // Update the entry object
                entry.setPassword(encryptedPassword);
                entry.setChangecount(entry.getChangecount()  + 1); // Increment counter
                // Update the database
                db.dataManager().updateEntry(entry);

                // Run on UI thread to show Toast and update RecyclerView
                if (context instanceof android.app.Activity) {
                    ((android.app.Activity) context).runOnUiThread(() -> {
                        Toast.makeText(context, "Password updated successfully!", Toast.LENGTH_SHORT).show();
                        // This is crucial to see the change immediately
                        notifyItemChanged(position);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (context instanceof android.app.Activity) {
                    ((android.app.Activity) context).runOnUiThread(() -> Toast.makeText(context, "Error updating password.", Toast.LENGTH_SHORT).show());
                }
            }
        }).start();
    }
}
