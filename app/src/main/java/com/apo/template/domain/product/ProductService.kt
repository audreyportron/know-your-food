package com.apo.template.domain.product

import io.reactivex.Single

class ProductService(val productRepository: ProductRepository) {

    fun getProductById(ean: String): Single<Product> = productRepository.getProductByEan(ean)

}