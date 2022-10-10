package com.mfaigan.assignment2.database;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {

    /**
     * Given a Unix timestamp, obtain the equivalent Date object for use in entity classes.
     *
     * @param ts A Unix timestamp in milliseconds.
     * @return A Date object containing the equivalent timestamp.
     */
    @TypeConverter
    public static Date dateFromUnixTimestamp(long ts) {
        return new Date(ts);
    }

    /**
     * Given a Date object, obtain the equivalent Unix timestamp for database storage.
     *
     * @param date A Date object containing the desired timestamp.
     * @return The equivalent Unix timestamp in milliseconds.
     */
    @TypeConverter
    public static long dateToUnixTimestamp(Date date) {
        return date.getTime();
    }
}
