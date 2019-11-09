package com.apo.template.domain.product

import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProductServiceTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    private val productService: ProductService by lazy {
        ProductService(productRepository)
    }

    @Test
    fun should_getProduct_by_id_when_get_product() {
        //Given
        val product = DataFactory.getProduct("1")
        given(productRepository.getProductByEan("1")).willReturn(Single.just(product))

        //When
        val test = productService.getProductById("1").test()

        //Then
        test.assertNoErrors()
            .assertValueAt(0) {
                it.id == product.id
            }
        Mockito.verify(productRepository).getProductByEan("1")
    }
}