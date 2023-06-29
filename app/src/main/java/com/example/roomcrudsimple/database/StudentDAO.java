package com.example.roomcrudsimple.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roomcrudsimple.Student;

import java.util.List;

@Dao
public interface StudentDAO {
    @Insert
    void insert(Student student);

    @Update
    void update(Student student);

    @Delete
    void delete(Student student);

    @Query("SELECT * FROM student")
    List<Student> getListStudent();

    @Query("SELECT * FROM student where id= :id")
    List<Student> checkId(String id);
}
