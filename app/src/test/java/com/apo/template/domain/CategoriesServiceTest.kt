package com.apo.template.domain

import com.apo.template.domain.categories.CategoriesRepository
import com.apo.template.domain.categories.CategoriesService
import com.apo.template.domain.categories.Category
import com.apo.template.domain.categories.CategoryType
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CategoriesServiceTest {
    @Mock
    private lateinit var repository: CategoriesRepository


    private val service by lazy {
        CategoriesService(repository)
    }

    @Test
    fun should_order_categories_by_title() {
        //Given
        val category1 = Category(
            id = 1,
            title = "edc",
            type = CategoryType.BOOKS,
            apiLink = "href1"
        )
        val category2 = Category(
            id = 2,
            title = "abc",
            type = CategoryType.CHARACTERS,
            apiLink = "href2"
        )
        val category3 = Category(
            id = 3,
            title = "zbc",
            type = CategoryType.HOUSES,
            apiLink = "href2"
        )
        given(repository.getCategories(false)).willReturn(Single.just(listOf(category1, category2, category3)))

        //When
        val test = service.getCategoriesByName(false).test()

        //Then
        test.assertNoErrors()
            .assertValueAt(0) {
                it[0].id == category2.id
                        && it[1].id == category1.id
                        && it[2].id == category3.id
            }
    }

    @Test
    fun should_not_return_unknown_categories_when_get_categories_is_call() {
        //Given
        val category1 = Category(
            id = 1,
            title = "edc",
            type = CategoryType.BOOKS,
            apiLink = "href1"
        )
        val category2 = Category(
            id = 2,
            title = "abc",
            type = CategoryType.UNKNOWN,
            apiLink = "href2"
        )
        val category3 = Category(
            id = 3,
            title = "zbc",
            type = CategoryType.HOUSES,
            apiLink = "href2"
        )
        given(repository.getCategories(false)).willReturn(Single.just(listOf(category1, category2, category3)))

        //When
        val test = service.getCategoriesByName(false).test()

        //Then
        test.assertNoErrors()
            .assertValueAt(0) {
                it.size == 2
                        && it[0].id == category1.id
                        && it[1].id == category3.id
            }
    }


}