package com.idz.rentit.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.launch

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

fun createRetrofitInstance(): CityApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://data.gov.il/api/3/action/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(CityApiService::class.java)
}

class CitiesViewModel : ViewModel() {
    private val apiService = createRetrofitInstance()

    private val _cities = MutableLiveData<List<String>>()
    val cities: LiveData<List<String>> get() = _cities

    fun fetchCities() {
        viewModelScope.launch {
            try {
                val response = apiService.getCities("8f714b6f-c35c-4b40-a0e7-547b675eee0e", 1500)
                val cityNames = response.result.records.map { it.cityNameEn.trim() }
                _cities.postValue( cityNames.filter { it.isNotEmpty() }
                    .map { it.lowercase().replaceFirstChar { char -> char.uppercase() } })
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
}

interface CityApiService {
    @GET("datastore_search")
    suspend fun getCities(
        @Query("resource_id") resourceId: String,
        @Query("limit") limit: Int = 1500
    ): ApiResponse
}


data class ApiResponse(
    val result: ResultData
)

data class ResultData(
    val records: List<CityRecord>
)

data class CityRecord(
    @SerializedName("city_name_en") val cityNameEn: String
)