package com.idz.rentit.repository.room.database

import androidx.room.Room
import com.idz.rentit.context.MyApplication

object AppLocalDB {

    @JvmStatic
    fun getAppDB(): AppLocalDbRepository {
        return Room.databaseBuilder(
            MyApplication.appContext,
            AppLocalDbRepository::class.java,
            "dbFileName.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
