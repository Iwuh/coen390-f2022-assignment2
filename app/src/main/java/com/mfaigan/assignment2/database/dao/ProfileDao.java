package com.mfaigan.assignment2.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mfaigan.assignment2.database.entity.Profile;

import java.util.List;

@Dao
public interface ProfileDao {
    @Query("SELECT * FROM Profile")
    List<Profile> findAll();

    @Query("SELECT * FROM Profile ORDER BY Profile.uid ASC")
    List<Profile> findAllOrderedByUid();

    @Query("SELECT * FROM Profile ORDER BY Profile.surname ASC")
    List<Profile> findAllOrderedByName();

    @Query("SELECT * FROM Profile WHERE Profile.uid = :uid")
    Profile findById(long uid);

    @Insert
    void insertOne(Profile profile);

    @Insert
    void insertAll(Profile... profiles);

    @Delete
    void delete(Profile profile);
}
