package com.mfaigan.assignment2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.mfaigan.assignment2.database.AccessType;
import com.mfaigan.assignment2.database.AppDatabase;
import com.mfaigan.assignment2.database.entity.Access;
import com.mfaigan.assignment2.database.entity.Profile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * On-device unit tests for the app database.
 * Testing methodology based on official documentation @ https://developer.android.com/training/data-storage/room/testing-db
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    private AppDatabase db;

    @Before
    public void setup() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), AppDatabase.class).build();
    }

    @After
    public void teardown() {
        db.close();
    }

    @Test
    public void GivenANonEmptyDatabase_WhenDeletingAProfile_ThenTheProfileIsDeleted() {
        // Arrange
        long uid = 0;
        db.profileDao().insertOne(new Profile(uid, "Doe", "Jane", 4.0, new Date(0)));
        Profile p = db.profileDao().findById(uid);

        // Act
        db.profileDao().delete(p);

        // Assert
        assertNull(db.profileDao().findById(uid));
    }

    @Test
    public void GivenAProfileWithAccesses_WhenFindingAccessesByProfileId_ThenTheAccessesAreFound() {
        // Arrange
        long uid = 1;
        db.profileDao().insertOne(new Profile(uid, "Bar", "Foo", 2.0, new Date(0)));
        Profile p = db.profileDao().findById(uid);

        db.accessDao().insertAll(
                new Access(0, uid, AccessType.Created, p.getCreationDate()),
                new Access(0, uid, AccessType.Opened, new Date(10000)),
                new Access(0, uid, AccessType.Closed, new Date(200000)));

        // Act
        List<Access> accesses = db.accessDao().findByProfileId(uid);

        // Assert
        assertEquals(3, accesses.size());
    }
}
