package com.apo.template.data.network.categories

import com.apo.template.domain.categories.Category
import com.apo.template.domain.categories.toCategoryType


fun CategoryJson.toCategory(): Category =
    Category(
        id = id,
        title = title,
        type = id.toCategoryType(),
        apiLink = apiLink
    )


