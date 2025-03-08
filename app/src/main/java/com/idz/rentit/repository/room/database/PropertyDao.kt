package com.idz.rentit.repository.room.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.idz.rentit.repository.models.Property

@Dao
interface PropertyDao {
    @Query("SELECT * FROM Property")
    fun getAllProperties(): LiveData<List<Property>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(properties: Property)

    @Query("SELECT * FROM Property WHERE price <= :price AND " +
            "location LIKE '%' || :location || '%' AND " +
            "hasShelter IS :hasShelter AND " +
            "isFurnished IS :isFurnished")
    fun getAllPropertiesByFilter(
        price: Double,
        location: String,
        hasShelter: Boolean,
        isFurnished: Boolean
    ): List<Property>
}
