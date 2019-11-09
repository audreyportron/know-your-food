package com.apo.template.ui.categories

import android.widget.TextView
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.apo.template.domain.categories.CategoriesService
import com.apo.template.domain.categories.Category
import com.apo.template.tools.AppSchedulers
import com.apo.template.ui.binding.AutobindedViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class CategoriesViewModel(
    val service: CategoriesService,
    private val listener: Listener?
) : ViewModel(), LifecycleObserver {

    companion object {
        const val LISTENER_ID = "CategoryViewModel_listener"
    }

    interface Listener {
        fun onItemClick(category: Category, sharedView: TextView)
    }

    val categoriesViewModel = ObservableArrayList<AutobindedViewModel>()
    val loading = ObservableBoolean(false)
    val error = ObservableBoolean(false)

    private val compositeDisposable = CompositeDisposable()
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun getData() {
        compositeDisposable += service.getCategoriesByName(false)
            .subscribeOn(AppSchedulers.io())
            .observeOn(AppSchedulers.mainThread())
            .doOnSubscribe { loading.set(true) }
            .doFinally { loading.set(false) }
            .subscribe(::onSuccess, ::onError)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun clear() {
        compositeDisposable.clear()
    }

    private fun onSuccess(categories: List<Category>) {
        categoriesViewModel.clear()
        if (categories.isEmpty()) error.set(true)
        else {
            categoriesViewModel.addAll(categories.map {
                CategoryItemViewModel(
                    category = it,
                    onItemClick = ::onCategoryClick
                )
            })
            error.set(false)
        }
    }

    private fun onError(t: Throwable) {
        categoriesViewModel.clear()
        error.set(true)
    }

    private fun onCategoryClick(category: Category, sharedView: TextView) {
        listener?.onItemClick(category, sharedView)
    }

}