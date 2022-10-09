package com.mfaigan.assignment2.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.mfaigan.assignment2.database.entity.Access;

import java.util.List;

@Dao
public interface AccessDao {
    @Query("SELECT * FROM Access WHERE Access.profileId = :profileId ORDER BY Access.timestamp DESC")
    List<Access> findByProfileId(long profileId);

    @Insert
    long insertOne(Access access);

    @Insert
    long[] insertAll(Access... accesses);
}
