package com.apo.template.ui.categories

import android.view.View
import android.widget.TextView
import com.apo.template.BR
import com.apo.template.R
import com.apo.template.domain.categories.Category
import com.apo.template.ui.binding.AutobindedViewModel
import kotlinx.android.synthetic.main.home_category_item.view.*

class CategoryItemViewModel(
    private val category: Category,
    private val onItemClick: (Category, TextView) -> Unit,
    override val layout: Int = R.layout.home_category_item,
    override val variable: Int = BR.model
) : AutobindedViewModel {
    val title = category.title
    fun onItemClick(v: View) {
        onItemClick.invoke(category, v.home_category_item_name)
    }

    fun transitionName(baseName: String) = "${baseName}_${category.id}"
}
