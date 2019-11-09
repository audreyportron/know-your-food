package com.apo.template.domain.categories

data class Category(
    val id: Int,
    val title: String,
    val type: CategoryType,
    val apiLink: String
)

enum class CategoryType(val id: Int) {
    BOOKS(1),
    HOUSES(2),
    CHARACTERS(3),
    UNKNOWN(-1)
}

fun Int.toCategoryType(): CategoryType {
    return when (this) {
        1 -> CategoryType.BOOKS
        2 -> CategoryType.HOUSES
        3 -> CategoryType.CHARACTERS
        else -> CategoryType.UNKNOWN

    }
}