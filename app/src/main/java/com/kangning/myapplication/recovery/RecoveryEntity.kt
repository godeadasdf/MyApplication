package com.kangning.myapplication.recovery

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "recovery_table")
class RecoveryEntity(
        @PrimaryKey
        val className: String,
        val jsonString: String)