package com.example.study_choi

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class UserModel(
    @ColumnInfo val loginId: String,
    @ColumnInfo val name: String,
    @ColumnInfo val password: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo val userId: Int
)