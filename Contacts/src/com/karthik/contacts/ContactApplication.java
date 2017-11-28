package com.karthik.contacts;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

/**
 * Created by android on 14/11/17.
 */
public class ContactApplication extends Application {

    private  SQLiteDatabase database;
    private static ContactApplication contactApplication;
    public static final String TABLE_NAME = "contacts";
    public static final String _ID = "mobno";


    @Override
    public void onCreate() {
        super.onCreate();
        contactApplication=this;
        File dbDir = getDir(this.getClass().getName(), Context.MODE_PRIVATE);
        File mDatabaseFile = new File(dbDir, "database_contacts.db");
        database = SQLiteDatabase.openOrCreateDatabase(mDatabaseFile.getPath(), null, null);
    }
    public static ContactApplication getInstance(){return contactApplication;}

    public  SQLiteDatabase getDatabase(){

        return database;
    }
}
