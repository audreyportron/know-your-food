package com.apo.template.data.repository

import com.apo.template.data.network.categories.CategoriesApi
import com.apo.template.data.network.categories.CategoryJson
import com.apo.template.data.roomdb.CategoriesDAO
import com.apo.template.data.roomdb.CategoryEntity
import com.apo.template.domain.categories.Category
import com.apo.template.domain.categories.CategoryType
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainCategoriesRepositoryTest {

    @Mock
    private lateinit var categoriesApi: CategoriesApi
    @Mock
    private lateinit var categoriesDao: CategoriesDAO

    private val repository by lazy {
        MainCategoriesRepository(categoriesApi, categoriesDao)
    }


    @Test
    fun should_get_data_from_ws_when_force_true() {
        //Given
        given(categoriesApi.getCategories()).willReturn(Single.just(listOf(categoryJson)))

        //When
        val test = repository.getCategories(true).test()

        //Then
        test.assertNoErrors()
            .assertValueAt(0) { categories ->
                categories.size == 1
                        && categories[0].id == category.id
                        && categories[0].title == category.title
                        && categories[0].apiLink == category.apiLink
                        && categories[0].type == category.type
            }
        verify(categoriesDao, Mockito.atLeastOnce()).insertAll(listOf(categoryEntity))

    }

    @Test
    fun should_return_data_from_db_when_exist_and_force_load_false() {
        //Given
        given(categoriesDao.getAll()).willReturn(Single.just(listOf(categoryEntity)))

        //When
        val test = repository.getCategories(false).test()

        //Then
        test.assertNoErrors()
            .assertValueAt(0) { categories ->
                categories.size == 1
                        && categories[0].id == category.id
                        && categories[0].title == category.title
                        && categories[0].apiLink == category.apiLink
                        && categories[0].type == category.type
            }
        verify(categoriesApi, Mockito.never()).getCategories()
    }

    @Test
    fun should_return_data_from_db_when_network_call_fail_and_exist() {
        //Given
        given(categoriesApi.getCategories()).willReturn(Single.error(Throwable()))
        given(categoriesDao.getAll()).willReturn(Single.just(listOf(categoryEntity)))

        //When
        val test = repository.getCategories(true).test()

        //Then
        test.assertNoErrors()
            .assertValueAt(0) { categories ->
                categories.size == 1
                        && categories[0].id == category.id
                        && categories[0].title == category.title
                        && categories[0].apiLink == category.apiLink
                        && categories[0].type == category.type
            }
        verify(categoriesApi).getCategories()
        verify(categoriesDao, Mockito.never()).insertAll(listOf(categoryEntity))

    }

    @Test
    fun should_return_error_when_ws_fail_no_db_and_force_fail_true() {
        //Given
        given(categoriesApi.getCategories()).willReturn(Single.error(Throwable()))
        given(categoriesDao.getAll()).willReturn(Single.error(Throwable()))

        //When
        val test = repository.getCategories(true).test()

        //Then
        test.assertError() {
            it is Throwable
        }
    }

    @Test
    fun should_return_error_when_ws_fail_no_db_and_force_fail_false() {
        //Given
        given(categoriesDao.getAll()).willReturn(Single.error(Throwable()))

        //When
        val test = repository.getCategories(false).test()

        //Then
        test.assertError() {
            it is Throwable
        }
    }

    /** **********************************
     *          Data mock
     *********************************** **/
    val categoryJson =
        CategoryJson(id = 1, title = "title1", apiLink = "href1")
    val categoryEntity = CategoryEntity(id = 1, title = "title1", apiLink = "href1")
    val category = Category(
        id = 1,
        title = "title1",
        type = CategoryType.BOOKS,
        apiLink = "href1"
    )
}