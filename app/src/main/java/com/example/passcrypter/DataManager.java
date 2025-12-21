package com.example.passcrypter;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DataManager {
    @Insert
    void insert(PasswordEntryValDefinition passwordEntryValDefinition);

    @Query("SELECT * FROM PasswordEntry_Value_Definition ORDER BY account_name ASC")
    List<PasswordEntryValDefinition> getAllEntries();


}
