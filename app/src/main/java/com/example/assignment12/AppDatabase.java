package com.example.assignment12;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DetailsModel.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract Dao dao();
}
