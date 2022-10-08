package com.mfaigan.assignment2;

import static org.junit.Assert.assertEquals;
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
    public void GivenADatabase_WhenAddingANewProfile_ThenTheProfileIsAdded() {
        // Act
        db.profileDao().insertAll(new Profile(0, "Johnson", "Bob", 3.0, new Date(0)));
        List<Profile> profiles = db.profileDao().findByName("Johnson", "Bob");

        // Assert
        assertEquals(1, profiles.size());
    }

    @Test
    public void GivenANonEmptyDatabase_WhenDeletingAProfile_ThenTheProfileIsDeleted() {
        // Arrange
        db.profileDao().insertAll(new Profile(0, "Doe", "Jane", 4.0, new Date(0)));
        Optional<Profile> p = db.profileDao().findByName("Doe", "Jane").stream().findFirst();
        assertTrue(p.isPresent());

        // Act
        db.profileDao().delete(p.get());

        // Assert
        assertEquals(0, db.profileDao().findByName("Doe", "Jane").size());
    }

    @Test
    public void GivenAProfileWithAccesses_WhenFindingAccessesByProfileId_ThenTheAccessesAreFound() {
        // Arrange
        db.profileDao().insertAll(new Profile(0, "Bar", "Foo", 2.0, new Date(0)));
        Optional<Profile> p = db.profileDao().findByName("Bar", "Foo").stream().findFirst();
        assertTrue(p.isPresent());

        db.accessDao().insertAll(
                new Access(0, p.get().getUid(), AccessType.Created, p.get().getCreationDate()),
                new Access(0, p.get().getUid(), AccessType.Opened, new Date(10000)),
                new Access(0, p.get().getUid(), AccessType.Closed, new Date(200000)));

        // Act
        List<Access> accesses = db.accessDao().findByProfileId(p.get().getUid());

        // Assert
        assertEquals(3, accesses.size());
    }
}
