package com.apo.template.domain.categories

import io.reactivex.Single

interface CategoriesRepository {
    fun getCategories(forceApiLoad: Boolean): Single<List<Category>>
}