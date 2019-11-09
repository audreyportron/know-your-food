package com.apo.template.domain.product

object DataFactory {

    fun getProduct(id: String = "1") = Product(
        id = id,
        img = "urlimag",
        scoreImg = "scoreImg",
        ingredients = listOf(getIngredient()),
        brands = "brand",
        nutriments = getNutriment()
    )

    fun getIngredient() = Ingredient(
        id = "id",
        text = "ingredientName",
        percent = "80",
        vegetarian = true,
        vegan = true
    )

    fun getNutriment() = Nutriments(
        kcal = 150,
        carbohydrates = 0.5f,
        fat = 1.5f
    )
}