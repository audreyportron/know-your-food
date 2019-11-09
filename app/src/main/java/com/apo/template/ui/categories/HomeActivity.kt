package com.apo.template.ui.categories

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import com.apo.template.R
import com.apo.template.databinding.HomeActivityBinding
import com.apo.template.domain.categories.Category
import com.apo.template.domain.categories.CategoryType
import com.apo.template.ui.book.BooksActivity
import com.apo.template.ui.characters.CharactersActivity
import com.apo.template.ui.houses.HousesActivity
import org.koin.android.ext.android.setProperty
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : AppCompatActivity(), CategoriesViewModel.Listener {


    private val catViewModel: CategoriesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        val bindingActivity: HomeActivityBinding = DataBindingUtil.setContentView(
            this,
            R.layout.home_activity
        )

        setProperty(CategoriesViewModel.LISTENER_ID, this)
        this.lifecycle.addObserver(catViewModel)
        bindingActivity.model = catViewModel
    }

    /** **********************************
     *          Categories Listener
     *********************************** **/
    override fun onItemClick(category: Category, sharedView: TextView) {
        when (category.type) {
            CategoryType.BOOKS -> startActivity(BooksActivity.getIntent(this, category.apiLink, category.title))
            CategoryType.HOUSES -> {

                val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    sharedView,
                    ViewCompat.getTransitionName(sharedView)!! // "!!" added because minSDK is superieur at 21
                )
                startActivity(
                    HousesActivity.getIntent(this, category.apiLink, category.title, sharedView),
                    options.toBundle()
                )
            }
            CategoryType.CHARACTERS -> startActivity(
                CharactersActivity.getIntent(
                    this,
                    category.apiLink,
                    category.title
                )
            )

        }
    }

    /** **********************************
     *          Companion
     *********************************** **/
    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }

}
