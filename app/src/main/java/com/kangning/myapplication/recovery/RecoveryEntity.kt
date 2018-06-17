package com.kangning.myapplication.recovery

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "recovery_table")
class RecoveryEntity(
        val className: String,
        val jsonString: String) {
    @field:PrimaryKey(autoGenerate = true)
    var id: Int = 0
}