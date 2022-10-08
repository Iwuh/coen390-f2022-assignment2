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

    @Query("SELECT * FROM Profile WHERE Profile.uid = :uid")
    Profile findById(int uid);

    @Query("SELECT * FROM Profile WHERE Profile.surname = :surname AND Profile.name = :name")
    List<Profile> findByName(String surname, String name);

    @Insert
    void insertAll(Profile... profiles);

    @Delete
    void delete(Profile profile);
}
