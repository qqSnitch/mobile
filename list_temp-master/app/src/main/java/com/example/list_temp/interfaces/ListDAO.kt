package com.example.list_temp.interfaces

import androidx.core.location.LocationRequestCompat.Quality
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.list_temp.data.Group
import com.example.list_temp.data.Student
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface ListDAO {
    @Query("DELETE FROM Faculty")
    suspend fun deleteAllFaculty()

    @Query("SELECT * FROM groups")
    fun getAllGroups(): Flow<List<Group>>

    @Query("SELECT * FROM groups where faculty_id=:facultyID")
    fun getFacultyGroups(facultyID : UUID): Flow<List<Group>>

    @Insert(entity = Group::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: Group)

    @Delete(entity = Group::class)
    suspend fun deleteAllGroups()

    @Query("SELECT * FROM students")
    fun getAllStudents(): Flow<List<Student>>

    @Query("SELECT * FROM students where group_id =:groupID")
    fun getGroupStudent(groupID:UUID):Flow<List<Student>>

    @Insert(entity = Student::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student)

    @Delete(entity = Student::class)
    suspend fun deleteStudent(student: Student)

    @Query("DELETE FROM students")
    suspend fun deleteAllStudenst()
}