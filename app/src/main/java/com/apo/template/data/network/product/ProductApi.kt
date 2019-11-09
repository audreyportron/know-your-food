package com.apo.template.data.network.product

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {

    @GET("product/{productEan}.json")
    fun getProduct(@Path("productEan") productEan: String): Single<ProductJson>
}