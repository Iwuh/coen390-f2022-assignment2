package com.mfaigan.assignment2.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.mfaigan.assignment2.database.AccessType;

import java.util.Date;

@Entity
public class Access {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    private int profileId;
    private AccessType accessType;
    // Contrary to the class name, java.util.Date stores a date and a time with ms precision.
    private Date timestamp;

    public Access(int uid, int profileId, AccessType accessType, Date timestamp) {
        this.uid = uid;
        this.profileId = profileId;
        this.accessType = accessType;
        this.timestamp = timestamp;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}