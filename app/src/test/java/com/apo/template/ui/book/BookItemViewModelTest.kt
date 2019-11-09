package com.apo.template.ui.book

import com.apo.template.R
import com.apo.template.domain.book.Book
import org.junit.Assert.assertTrue
import org.junit.Test

class BookItemViewModelTest {
    @Test
    fun should_display_book() {
        //Given
        val book = Book(listOf("Author1"), "USA", "name", 100)

        //When
        val viewModel = BookItemViewModel(book)
        //Then
        assertTrue(viewModel.title == book.name)
        assertTrue(viewModel.country == book.country)
        assertTrue(viewModel.mainAuthor == book.authors.first())
        assertTrue(viewModel.pagesArg[0] == book.pages.toString())
        assertTrue(viewModel.pagesResId == R.string.book_pages)
    }
}