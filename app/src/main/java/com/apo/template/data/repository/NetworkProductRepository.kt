package com.apo.template.data.repository

import com.apo.template.data.network.product.IngredientsJson
import com.apo.template.data.network.product.NutrimentsJson
import com.apo.template.data.network.product.ProductApi
import com.apo.template.data.network.product.ProductJson
import com.apo.template.domain.product.Ingredient
import com.apo.template.domain.product.Nutriments
import com.apo.template.domain.product.Product
import com.apo.template.domain.product.ProductRepository
import io.reactivex.Single

class NetworkProductRepository(private val api: ProductApi) : ProductRepository {
    override fun getProductByEan(ean: String): Single<Product> {
        return api.getProduct(ean).map {
            it.toProduct()
        }

    }
}

private fun ProductJson.toProduct() =
    Product(
        id = id,
        brands = product.brands,
        img = product.img,
        scoreImg = product.score,
        ingredients = product.ingredients.toIngredients(),
        nutriments = product.nutriments.toNutriments()
    )

private fun NutrimentsJson.toNutriments() = Nutriments(
    kcal = kcal,
    carbohydrates = carbohydrates,
    fat = fat
)

private fun List<IngredientsJson>.toIngredients() = this.map { it.toIngredient() }

private fun IngredientsJson.toIngredient(): Ingredient {

    return Ingredient(
        id = id,
        text = text,
        percent = percent ?: "",
        vegan = vegan?.toRealBoolean() ?: false,
        vegetarian = vegetarian?.toRealBoolean() ?: false,
        labels = labels ?: ""
    )
}

private fun String.toRealBoolean(): Boolean {
    return if (this == "yes") true else false

}
