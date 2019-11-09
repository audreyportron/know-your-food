package com.apo.template.ui.product

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.apo.template.R
import com.apo.template.databinding.HomeActivityBinding
import com.apo.template.ui.categories.CategoriesViewModel
import org.koin.android.ext.android.setProperty
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : AppCompatActivity() {


    private val searchProductViewModel: SearchProductViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        val bindingActivity: HomeActivityBinding = DataBindingUtil.setContentView(
            this,
            R.layout.home_activity
        )

        setProperty(CategoriesViewModel.LISTENER_ID, this)
        this.lifecycle.addObserver(searchProductViewModel)
        bindingActivity.model = searchProductViewModel
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
