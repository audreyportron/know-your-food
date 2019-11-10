package com.apo.template.data.repository

import com.apo.template.data.network.product.*
import com.apo.template.domain.product.DataFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class NetworkProductRepositoryTest {

    @Mock
    private lateinit var api: ProductApi

    private lateinit var productRepository: NetworkProductRepository

    @Before
    fun setup() {
        productRepository = NetworkProductRepository(api = api)
    }

    @Test
    fun should_map_into_domain_product_when_getting_product_from_api() {
        //Given
        val productJson = produtJson("1")
        val product = DataFactory.getProduct("1")
        given(api.getProduct("1")).willReturn(Single.just(productJson))

        //When
        val test = productRepository.getProductByEan("1").test()

        //Then
        test.assertNoErrors()
            .assertValueAt(0) {
                it.id == product.id
            }
        Mockito.verify(api).getProduct("1")
    }


    fun produtJson(id: String = "1") = ProductJson(
        id = id,
        product = productDetailJson()
    )

    fun productDetailJson() = ProductContentJson(
        name = "name",
        img = "urlimag",
        score = "scoreImg",
        ingredients = listOf(ingredientJson()),
        brands = "brand",
        nutriments = nutrimentsJson()
    )

    fun ingredientJson() = IngredientsJson(
        id = "id",
        text = "ingredientName",
        percent = "80",
        vegetarian = "yes",
        vegan = "yes"
    )

    fun nutrimentsJson() = NutrimentsJson(
        kcal = 150,
        carbohydrates = 0.5f,
        fat = 1.5f
    )


}