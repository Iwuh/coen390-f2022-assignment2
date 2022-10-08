package com.mfaigan.assignment2.database;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {

    @TypeConverter
    public static Date dateFromUnixTimestamp(long ts) {
        return new Date(ts);
    }

    @TypeConverter
    public static long dateToUnixTimestamp(Date date) {
        return date.getTime();
    }
}
