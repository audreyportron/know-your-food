package com.apo.template.ui.product

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.Lifecycle.Event.ON_STOP
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.apo.template.domain.product.Product
import com.apo.template.domain.product.ProductService
import com.apo.template.tools.AppSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber

class SearchProductViewModel(private val productService: ProductService) : ViewModel(), LifecycleObserver {

    val id = ObservableField<String>()
    val brands = ObservableField<String>("")
    val compositeDisposable = CompositeDisposable()

    @OnLifecycleEvent(ON_STOP)
    fun clear() {
        compositeDisposable.clear()
    }


    fun onSearch(v: View) {
        compositeDisposable += productService.getProductById(id.get() ?: "")
            .subscribeOn(AppSchedulers.io())
            .observeOn(AppSchedulers.mainThread())
            .subscribe(::onSuccess, ::onError)
    }

    fun onSuccess(product: Product) {
        Timber.e("no error" + product.brands)
        brands.set(product.brands)
    }

    fun onError(t: Throwable) {
        Timber.e("error" + t.toString())
    }

}