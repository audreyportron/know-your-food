package com.apo.template.tools.di

import androidx.room.Room
import com.apo.template.data.network.product.ProductApi
import com.apo.template.data.repository.NetworkProductRepository
import com.apo.template.data.roomdb.GotDatabase
import com.apo.template.domain.product.ProductRepository
import com.apo.template.domain.product.ProductService
import com.apo.template.ui.product.SearchProductViewModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val viewModelModule = module {
    viewModel { SearchProductViewModel(get()) }
}
val repositoryModule = module {
    single<ProductRepository> { NetworkProductRepository(get()) }
}

val serviceModule = module {
    single { ProductService(get()) }
}


/** **********************************
 *          Network Module
 *********************************** **/

fun createOkHttpClient(): OkHttpClient {
    val logInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    return OkHttpClient.Builder()
        .connectTimeout(30L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .addInterceptor(logInterceptor)
        .build()
}


fun createRetrofit(okHttp: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl("https://world.openfoodfacts.org/api/v0/")
    .client(okHttp)
    .addConverterFactory(
        GsonConverterFactory.create(GsonBuilder().create())
    )
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()

val networkModule = module {
    single { createRetrofit(createOkHttpClient()).create(ProductApi::class.java) }
}

/** **********************************
 *          DB module
 *********************************** **/
val roomModule = module {
    single {
        Room.databaseBuilder(get(), GotDatabase::class.java, "app_got_db")
            .build()
    }
    //DAO
    single { get<GotDatabase>().categoriesDao() }
}

val roomTestModule = module {
    single {
        Room.inMemoryDatabaseBuilder(get(), GotDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
    //DAO
    single { get<GotDatabase>().categoriesDao() }
}