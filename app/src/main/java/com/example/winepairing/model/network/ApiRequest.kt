package com.example.winepairing.model.network

import com.example.winepairing.BuildConfig
import com.example.winepairing.model.data.Wine
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL = "https://api.spoonacular.com/food/wine/"
const val CLIENT_ID = BuildConfig.SPOONACULAR_ACCESS_KEY

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val networkLoggingInterceptor =
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
val retrofit = Retrofit.Builder()
    .client(OkHttpClient.Builder().addInterceptor(networkLoggingInterceptor).build())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
interface ApiRequest {
    @GET("pairing?")
    suspend fun getWinePairing(@Query("food") food: String,
                               @Query("apiKey") apiKey: String= CLIENT_ID): Wine

}

object WineApi {
    val retrofitService: ApiRequest by lazy { retrofit.create(ApiRequest::class.java) }
}