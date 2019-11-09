package com.apo.template.data.repository

import com.apo.template.data.network.CategoriesTypeApi
import com.apo.template.data.network.book.BookJson
import com.apo.template.domain.book.Book
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NetworkBookRepositoryTest {
    @Mock
    private lateinit var api: CategoriesTypeApi

    private val repository by lazy {
        NetworkBookRepository(api)
    }

    @Test
    fun should_return_books() {
        //Given
        given(api.getBooks("any")).willReturn(Single.just(listOf(bookJson)))
        //When
        val test = repository.getBooks("any").test()
        //Then
        test.assertNoErrors()
            .assertValueAt(0) {
                it.size == 1
                        && it[0].authors == book.authors
                        && it[0].name == book.name
                        && it[0].country == book.country
                        && it[0].pages == book.pages
            }
    }

    /** **********************************
     *          Data Mock
     *********************************** **/
    val bookJson = BookJson(listOf("Author1"), "USA", "name", 100)
    val book = Book(listOf("Author1"), "USA", "name", 100)
}