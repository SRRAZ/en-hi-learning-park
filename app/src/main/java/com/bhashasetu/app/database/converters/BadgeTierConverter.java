package com.bhashasetu.app.database.converters;

import androidx.room.TypeConverter;

import com.bhashasetu.app.model.gamification.BadgeTier;

/**
 * Type converter for BadgeTier enum in Room database
 */
public class BadgeTierConverter {
    
    @TypeConverter
    public static BadgeTier toBadgeTier(String value) {
        return value == null ? null : BadgeTier.valueOf(value);
    }
    
    @TypeConverter
    public static String fromBadgeTier(BadgeTier tier) {
        return tier == null ? null : tier.name();
    }
}