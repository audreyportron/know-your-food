package com.apo.template.domain.product

data class Product(
    val id: String,
    val name: String,
    val brands: String,
    val img: String,
    val scoreImg: String,
    val ingredients: List<Ingredient>,
    val nutriments: Nutriments
)


data class Ingredient(
    val id: String,
    val text: String,
    val percent: String,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val labels: String = ""
)

data class Nutriments(
    val kcal: Int,
    val carbohydrates: Float,//: 2.3,
    val fat: Float
)