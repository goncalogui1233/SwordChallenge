package com.goncalo.data.network

import com.goncalo.data.mappers.CatImageInformation
import com.goncalo.data.mappers.CatBreedInformation
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface CatInformationApi {

    companion object {

        /**
         * In a real project, the api-key should not be placed here.
         * For safety, the key should be stored in a .properties file and that
         * file not committed to the remote.
         */
        const val API_KEY = "REDACTED"
    }

    @GET("v1/breeds")
    suspend fun getCatList(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Header("x-api-key") apiKey: String = API_KEY
    ): Response<List<CatBreedInformation>>

    @GET("v1/images/{image_id}")
    suspend fun getCatImage(
        @Path("image_id") imageId: String,
        @Header("x-api-key") apiKey: String = API_KEY
    ): Response<CatImageInformation>

    @GET("v1/breeds/search")
    suspend fun getCatSearchList(
        @Query("q") query: String,
        @Query("attach_image") attachImage: Int = 1,
        @Header("x-api-key") apiKey: String = API_KEY
    ): Response<List<CatBreedInformation>>


}