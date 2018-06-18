package com.kangning.myapplication.recovery

import android.app.Application
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch


class RecoveryRepository {


    private var mRecoveryDao: RecoveryDao
//    private var mAllWords: LiveData<List<Word>>

    constructor(application: Application) {
        val db = RecoveryRoomDatabase.getDatabase(application)
        mRecoveryDao = db!!.RecoveryDao()
//        mAllWords = mRecoveryDao.getAllWords()
    }

    fun getRecoveryItem(className: String):RecoveryEntity {
        return mRecoveryDao.getRecovery(className)
    }


    fun insert(item: RecoveryEntity) {
        launch {
            mRecoveryDao.insert(item)
        }
    }

}