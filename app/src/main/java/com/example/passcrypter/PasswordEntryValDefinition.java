package com.example.passcrypter;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.material.textfield.TextInputEditText;

@Entity(tableName = "PasswordEntry_Value_Definition")
public class PasswordEntryValDefinition {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "account_name")
    public String accountName;
    @ColumnInfo(name="username")
        public String username;
    @ColumnInfo(name="password")
        public String password;
    @ColumnInfo(name="logo")
        public String logo;

    public PasswordEntryValDefinition(String accountName, String username, String password, String logo) {

        this.accountName = accountName;
        this.username = username;
        this.password = password;
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
