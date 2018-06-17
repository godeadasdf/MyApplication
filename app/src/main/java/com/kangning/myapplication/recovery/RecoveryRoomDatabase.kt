package com.kangning.myapplication.recovery

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import android.content.Context
import android.arch.persistence.room.Room


@Database(entities = [RecoveryEntity::class], version = 1)
abstract class RecoveryRoomDatabase : RoomDatabase() {

    abstract fun RecoveryDao(): RecoveryDao

    companion object {
        @Volatile
        private var INSTANCE: RecoveryRoomDatabase? = null


        fun getDatabase(context: Context): RecoveryRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(RecoveryRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                RecoveryRoomDatabase::class.java, "recovery_database")
                                .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}