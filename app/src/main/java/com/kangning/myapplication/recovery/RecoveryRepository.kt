package com.kangning.myapplication.recovery

import android.app.Application


class RecoveryRepository {


    private var mRecoveryDao: RecoveryDao

    constructor(application: Application) {
        val db = RecoveryRoomDatabase.getDatabase(application)
        mRecoveryDao = db!!.RecoveryDao()
    }

    fun getRecoveryItem(classIdentity: String): RecoveryEntity {
        return mRecoveryDao.getRecovery(classIdentity)
    }


    fun insert(item: RecoveryEntity) {
        mRecoveryDao.insert(item)
    }

    fun deleteRecoveryItem(classIdentity: String) {
        mRecoveryDao.deleteRecovery(classIdentity)
    }

}