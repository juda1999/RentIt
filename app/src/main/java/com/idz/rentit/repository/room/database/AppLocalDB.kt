package com.idz.rentit.repository.room.database

import androidx.room.Room
import com.idz.rentit.context.MyApplication

object AppLocalDB {

    @JvmStatic
        val getAppDB: AppLocalDbRepository by lazy {
            val context = MyApplication.Globals.appContext
                ?: throw IllegalStateException("Application context not available")

            Room.databaseBuilder(
                context,
                AppLocalDbRepository::class.java,
                "rentIt.db"
            ).fallbackToDestructiveMigration()
                .build()
        }
    }
