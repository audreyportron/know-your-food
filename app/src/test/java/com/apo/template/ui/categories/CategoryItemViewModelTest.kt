package com.a

import android.widget.TextView
import com.apo.template.domain.categories.Category
import com.apo.template.domain.categories.CategoryType
import com.apo.template.ui.categories.CategoryItemViewModel
import org.junit.Assert.assertTrue
import org.junit.Test

class CategoryItemViewModelTest {
    @Test
    fun should_display_item_title() {
        //Given
        val category = Category(
            id = 1,
            title = "title1",
            type = CategoryType.BOOKS,
            apiLink = "href1"
        )

        //When
        val model = CategoryItemViewModel(category, ::listener)

        //Then
        assertTrue(model.title == category.title)
    }

    fun listener(cat:Category, text: TextView){}
}