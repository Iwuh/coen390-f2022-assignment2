package com.mfaigan.assignment2.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.mfaigan.assignment2.database.dao.AccessDao;
import com.mfaigan.assignment2.database.dao.ProfileDao;
import com.mfaigan.assignment2.database.entity.Access;
import com.mfaigan.assignment2.database.entity.Profile;

@Database(entities = {Profile.class, Access.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public static final String DB_NAME = "Assignment2_DB";

    private static volatile AppDatabase instance;

    protected AppDatabase() {
    }

    private static AppDatabase create(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DB_NAME).allowMainThreadQueries().build();
    }

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    public abstract ProfileDao profileDao();

    public abstract AccessDao accessDao();
}
