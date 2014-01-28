package com.activeandroid;

import android.database.sqlite.SQLiteDatabase;

public abstract class DbMetaData {

    public abstract int getDatabaseVersion();

    public abstract String getDatabaseName();

    public abstract String getMigrationPath();

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public boolean isResettable() { return false; }
}
