package com.example.passcrypter;

import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.Room;

@Database(entities = {PasswordEntryValDefinition.class},version = 1)
public  abstract class AppDatabase extends RoomDatabase {
    public abstract  DataManager dataManager();
    private static AppDatabase instance;
 static  AppDatabase getDatbase(final Context context)
 {
     if(instance == null)
     {
         synchronized (AppDatabase.class)
         {
             if(instance==null)
             {
                 instance= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"PasswordEntry_Value_Definition").build();
             }
         }
     }
     return instance;
 }


}

