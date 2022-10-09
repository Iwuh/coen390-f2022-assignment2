package com.mfaigan.assignment2;

import android.content.Context;

import com.mfaigan.assignment2.database.AccessType;
import com.mfaigan.assignment2.database.AppDatabase;
import com.mfaigan.assignment2.database.entity.Access;
import com.mfaigan.assignment2.database.entity.Profile;

import java.util.Date;
import java.util.List;

/**
 * Provides an API to manage the database from Activity code without needing to invoke DAO functions directly.
 */
public class DatabaseHelper {

    private final AppDatabase db;

    public DatabaseHelper(Context ctx) {
        db = AppDatabase.getInstance(ctx);
    }

    public void addNewProfile(long uid, String surname, String name, double gpa) {
        Date now = new Date();
        db.profileDao().insertOne(new Profile(uid, surname, name, gpa, now));
        db.accessDao().insertOne(new Access(0, uid, AccessType.Created, now));
    }

    public List<Profile> getAllProfiles() {
        return db.profileDao().findAll();
    }

    public List<Profile> getAllProfilesOrdered() {
        return db.profileDao().findAllOrdered();
    }
}
