package com.example.roomdb

import androidx.room.*

/**
 * Created by Sandeep on 12-Dec-22.
 */

@Dao
interface StudentDao {
    @Query("SELECT * FROM student_table")
    fun getAllStudents(): List<Student>

    @Query("SELECT * FROM student_table WHERE roll_no LIKE :rollNo LIMIT 1")
    suspend fun findByRoll(rollNo: Int): Student

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(student: Student)

    @Delete
    suspend fun delete(student: Student)

    @Query("DELETE FROM student_table")
    suspend fun deleteAll()

    @Query("UPDATE student_table SET first_name=:firstName,last_name=:lastName WHERE roll_no LIKE :rollNo")
    suspend fun update(firstName: String, lastName: String, rollNo: Int)

}