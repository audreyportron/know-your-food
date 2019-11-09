package com.apo.template.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apo.template.R
import com.apo.template.domain.categories.CategoriesRepository
import com.apo.template.domain.categories.Category
import com.apo.template.tools.AppSchedulers
import com.apo.template.ui.categories.HomeActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {

    private val categoriesRepository: CategoriesRepository by inject()
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_Launcher)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        compositeDisposable += categoriesRepository.getCategories(forceApiLoad = true)
            .subscribeOn(AppSchedulers.io())
            .observeOn(AppSchedulers.mainThread())
            .subscribe(::onSuccess, ::onError)
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    private fun onSuccess(categories: List<Category>) {
        startActivity(HomeActivity.getIntent(this))
        finish()
    }

    private fun onError(t: Throwable) {
        AlertDialog.Builder(this)
            .create().apply {
                setTitle(R.string.error)
                setMessage(getString(R.string.error_message))
                setButton(
                    AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok)
                ) { dialog, which -> finish() }

            }.show()
    }
}
