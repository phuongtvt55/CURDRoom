package com.example.roomcrudsimple.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.roomcrudsimple.Student;

@Database(entities = {Student.class}, version = 1)
public abstract class StudentDatabase extends RoomDatabase {
    public abstract StudentDAO studentDAO();
}
