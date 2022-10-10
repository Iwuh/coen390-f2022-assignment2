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

    /**
     * Creates a new profile and adds it to the database. Also creates an access entry for the profile creation.
     *
     * @param uid     The unique ID of the profile.
     * @param surname The family name of the profile.
     * @param name    The given name of the profile.
     * @param gpa     The GPA of the profile.
     */
    public void addNewProfile(long uid, String surname, String name, double gpa) {
        Date now = new Date();
        db.profileDao().insertOne(new Profile(uid, surname, name, gpa, now));
        db.accessDao().insertOne(new Access(0, uid, AccessType.Created, now));
    }

    /**
     * Removes a profile from the database. Also creates an access entry for profile deletion.
     *
     * @param profile The profile to delete.
     */
    public void deleteProfile(Profile profile) {
        db.profileDao().delete(profile);
        db.accessDao().insertOne(new Access(0, profile.getUid(), AccessType.Deleted, new Date()));
    }

    /**
     * Adds an arbitrary access to the database for a given profile ID.
     *
     * @param profileId  The ID of the profile that this access belongs to.
     * @param accessType The type of access that occurred.
     */
    public void addNewAccess(long profileId, AccessType accessType) {
        db.accessDao().insertOne(new Access(0, profileId, accessType, new Date()));
    }

    /**
     * Finds a given profile by its unique ID.
     *
     * @param uid The unique ID of the desired profile.
     * @return The corresponding Profile object if found, otherwise null.
     */
    public Profile getProfile(long uid) {
        return db.profileDao().findById(uid);
    }

    /**
     * Gets a list of all profiles, ordered by unique ID ascending.
     *
     * @return The sorted list of profiles.
     */
    public List<Profile> getAllProfilesOrderedByUid() {
        return db.profileDao().findAllOrderedByUid();
    }

    /**
     * Gets a list of all profiles, ordered alphabetically by surname.
     *
     * @return The sorted list of profiles.
     */
    public List<Profile> getAllProfilesOrderedByName() {
        return db.profileDao().findAllOrderedByName();
    }

    /**
     * Gets a list of all accesses to a given profile, ordered by timestamp descending.
     *
     * @param profileId The ID of the profile whose accesses should be found.
     * @return The sorted list of accesses.
     */
    public List<Access> getAllAccessesToProfile(long profileId) {
        return db.accessDao().findByProfileId(profileId);
    }
}
