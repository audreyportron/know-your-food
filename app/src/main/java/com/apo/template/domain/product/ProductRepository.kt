package com.apo.template.domain.product

import io.reactivex.Single

interface ProductRepository {
    fun getProductByEan(ean: String): Single<Product>
}