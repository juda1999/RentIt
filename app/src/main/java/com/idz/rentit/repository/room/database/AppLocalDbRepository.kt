package com.idz.rentit.repository.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.idz.rentit.repository.models.Property
import com.idz.rentit.repository.models.User

@Database(entities = [Property::class, User::class], exportSchema = false, version = 1)
abstract class AppLocalDbRepository : RoomDatabase() {
    abstract fun propertyDao(): PropertyDao
    abstract fun userDao(): UserDao
}
