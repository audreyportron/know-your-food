package com.apo.template.data.repository

import com.apo.template.data.network.categories.CategoriesApi
import com.apo.template.data.network.categories.toCategory
import com.apo.template.data.roomdb.CategoriesDAO
import com.apo.template.data.roomdb.toCategory
import com.apo.template.data.roomdb.toCategoryEntity
import com.apo.template.domain.categories.CategoriesRepository
import com.apo.template.domain.categories.Category
import io.reactivex.Single

class MainCategoriesRepository(
    private val categoriesApi: CategoriesApi,
    private val categoriesDao: CategoriesDAO
) : CategoriesRepository {
    override fun getCategories(forceApiLoad: Boolean): Single<List<Category>> {

        val categories =
            if (forceApiLoad) null
            else categoriesDao.getAll().map { categoriesEntity -> categoriesEntity.map { it.toCategory() } }
                .onErrorReturn { null }

        return if (categories != null) categories
        else categoriesApi.getCategories().map { categoriesJson ->
            categoriesJson.map { categoryJson ->
                categoryJson.toCategory()
            }
        }.doOnSuccess {
            val categoryEntities = it.map { category -> category.toCategoryEntity() }
            categoriesDao.insertAll(categoryEntities)
        }.onErrorResumeNext { t ->
            //Try to get datas from db
            categoriesDao.getAll().map {
                it.let { entities ->
                    entities.map { entity ->
                        entity.toCategory()
                    }
                }
            }
        }
    }
}
