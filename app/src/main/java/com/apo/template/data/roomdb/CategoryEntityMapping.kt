package com.apo.template.data.roomdb

import com.apo.template.domain.categories.Category
import com.apo.template.domain.categories.toCategoryType

fun Category.toCategoryEntity(): CategoryEntity = CategoryEntity(
    id = id,
    title = title, apiLink = apiLink
)

fun CategoryEntity.toCategory(): Category =
    Category(
        id = id,
        title = title,
        type = id.toCategoryType(),
        apiLink = apiLink
    )


