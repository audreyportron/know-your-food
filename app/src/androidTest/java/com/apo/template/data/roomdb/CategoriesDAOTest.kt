package com.apo.template.data.roomdb


import androidx.test.runner.AndroidJUnit4
import com.apo.template.tools.di.roomTestModule
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.standalone.StandAloneContext
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class CategoriesDAOTest : KoinTest {
    val gotDatabase: GotDatabase by inject()
    val categoriesDao: CategoriesDAO by inject()

    @Before()
    fun before() {
        StandAloneContext.loadKoinModules(roomTestModule)
    }

    @After
    fun after() {
        gotDatabase.close()
        stopKoin()
    }

    @Test
    fun should_saved_all_categories_and_return_all() {
        //Given
        val categoryEntity1 = CategoryEntity(id = 1, title = "title1", apiLink = "link1")
        val categoryEntity2 = CategoryEntity(id = 2, title = "title2", apiLink = "link2")

        //When
        categoriesDao.insertAll(listOf(categoryEntity1, categoryEntity2))

        val categoriesEntity = categoriesDao.getAll()

        //Then
        assertTrue(categoriesEntity.size == 2)
        assertTrue(categoriesEntity.contains(categoryEntity1))
        assertTrue(categoriesEntity.contains(categoryEntity2))
    }

}