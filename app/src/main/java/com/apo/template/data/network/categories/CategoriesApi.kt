package com.apo.template.data.network.categories

import io.reactivex.Single
import retrofit2.http.GET

interface CategoriesApi {
    @GET("test/api1/index")
    fun getCategories(): Single<List<CategoryJson>>
}