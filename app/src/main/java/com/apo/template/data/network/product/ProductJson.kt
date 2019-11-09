package com.apo.template.data.network.product

import com.google.gson.annotations.SerializedName

data class ProductJson(
    @SerializedName("code") val id: String,
    @SerializedName("product") val product: ProductContentJson
)

data class ProductContentJson(
    @SerializedName("image_front_small_url") val img: String,
    @SerializedName("image_nutrition_thumb_url") val score: String,
    @SerializedName("ingredients") val ingredients: List<IngredientsJson>,
    @SerializedName("brands") val brands: String,
    @SerializedName("nutriments") val nutriments: NutrimentsJson
)


data class IngredientsJson(
    @SerializedName("id") val id: String,
    @SerializedName("text") val text: String,
    @SerializedName("percent") val percent: String? = "",
    @SerializedName("vegan") val vegan: String? = "",
    @SerializedName("vegetarian") val vegetarian: String? = "",
    @SerializedName("labels") val labels: String? = ""
)

data class NutrimentsJson(

    @SerializedName("energy_value") val kcal: Int, //: 10,
    @SerializedName("carbohydrates") val carbohydrates: Float,//: 2.3,
    @SerializedName("fat") val fat: Float //: 0.5,
//    @SerializedName("sugars_100g"): 2.3,
//    @SerializedName("salt_value": 0.01,
//    @SerializedName("carbon-footprint-from-known-ingredients_100g": 43.5,
//    @SerializedName("nova-group": 4,
//    @SerializedName("sodium": 0.004,
//    @SerializedName("nova-group_serving": 4,
//    @SerializedName("sugars_value": 2.3,
//    @SerializedName("proteins": 0.5,
//    @SerializedName("nova-group_100g": 4,
//    @SerializedName("nutrition-score-uk_100g": 0,
//    @SerializedName("saturated-fat": 0,
//    @SerializedName("salt_100g": 0.01,
//    @SerializedName("energy_100g": 42,
//    @SerializedName("nutrition-score-fr_100g": 4,
//    @SerializedName("carbohydrates_100g": 2.3,
//    @SerializedName("proteins_unit": "g",
//@SerializedName("salt": 0.01,
//@SerializedName("carbon-footprint-from-known-ingredients_product": 326,
//@SerializedName("salt_unit": "g",
//@SerializedName("energy_unit": "kcal",
//@SerializedName("nutrition-score-uk": 0,
//"saturated-fat_value": 0,
//"saturated-fat_unit": "g",
//"proteins_value": 0.5,
//"sodium_unit": "g",
//"energy": 42,
//"fat_unit": "g",
//"sugars_unit": "g",
//"proteins_100g": 0.5,
//"carbohydrates_unit": "g",
//"sugars": 2.3,
//"fat_value": 0.5,
//"carbohydrates_value": 2.3,
//"fat_100g": 0.5,
//"sodium_100g": 0.004,
//"nutrition-score-fr": 4,
//"sodium_value": 0.004,
//"saturated-fat_100g": 0
)