package com.mfaigan.assignment2.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mfaigan.assignment2.database.entity.Profile;

@Dao
public interface ProfileDao {
    @Query("SELECT * FROM Profile WHERE Profile.uid = :uid")
    Profile findById(int uid);

    @Insert
    void insertAll(Profile... profiles);

    @Delete
    void delete(Profile profile);
}
