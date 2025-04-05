package com.example.list_temp.repository

import com.example.list_temp.data.Faculty
import com.example.list_temp.data.Group
import com.example.list_temp.data.Student
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface DBRepository {
    fun getFaculty():
            Flow<List<Faculty>>
    suspend fun  insertFaculrty(faculty: Faculty)
    suspend fun  updateFaculrty(faculty: Faculty)
    suspend fun  insertAllFaculty(facultyList:List<Faculty>)
    suspend fun  deleteFaculrty(faculty: Faculty)
    suspend fun  deleteAllFaculty()

    fun getAllGroups():Flow<List<Group>>
    fun getFacultyGroup(facultyID: UUID):Flow<List<Group>>
    suspend fun insertGroup(group:Group)
    suspend fun deleteGroup(group:Group)
    suspend fun deleteAllGroups()

    fun getAllStudents():Flow<List<Student>>
    fun getGroupStudents(groupID:UUID):Flow<List<Student>>
    suspend fun insertStudent(student:Student)
    suspend fun deleteStudent(student:Student)
    suspend fun deleteAllStudents()
}