package com.kangning.myapplication.recovery

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface RecoveryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recoveryEntity: RecoveryEntity)

    @Query("DELETE FROM recovery_table")
    fun deleteAll()

    @Query("SELECT * from recovery_table WHERE classIdentity = :classIdentity")
    fun getRecovery(classIdentity: String): RecoveryEntity

    @Query("DELETE FROM recovery_table WHERE classIdentity = :classIdentity")
    fun deleteRecovery(classIdentity: String)
}