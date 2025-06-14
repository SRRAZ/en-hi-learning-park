package com.bhashasetu.app.database.converters;

import androidx.room.TypeConverter;

import com.bhashasetu.app.model.gamification.AchievementType;

/**
 * Type converter for AchievementType enum in Room database
 */
public class AchievementTypeConverter {
    
    @TypeConverter
    public static AchievementType toAchievementType(String value) {
        return value == null ? null : AchievementType.valueOf(value);
    }
    
    @TypeConverter
    public static String fromAchievementType(AchievementType type) {
        return type == null ? null : type.name();
    }
}