package com.apo.template.data.network

import com.apo.template.data.network.book.BookJson
import com.apo.template.data.network.character.CharacterJson
import com.apo.template.data.network.houses.HouseJson
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CategoriesTypeApi {

    @GET("test{url}/")
    fun getBooks(@Path("url", encoded = true) url: String): Single<List<BookJson>>

    @GET("test{url}")
    fun getHouses(
        @Path("url", encoded = true) url: String,
        @Query("_page") page: Int,
        @Query("_limit") limit: Int
    ): Single<List<HouseJson>>

    @GET("test{url}/")
    fun getCharacters(@Path("url", encoded = true) url: String): Single<List<CharacterJson>>

}