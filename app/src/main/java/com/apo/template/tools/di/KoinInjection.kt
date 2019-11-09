package com.apo.template.tools.di

import androidx.room.Room
import com.apo.template.data.network.CategoriesTypeApi
import com.apo.template.data.network.categories.CategoriesApi
import com.apo.template.data.repository.MainCategoriesRepository
import com.apo.template.data.roomdb.GotDatabase
import com.apo.template.domain.book.BookRepository
import com.apo.template.domain.categories.CategoriesRepository
import com.apo.template.domain.categories.CategoriesService
import com.apo.template.domain.character.CharacterRepository
import com.apo.template.domain.house.HouseRepository
import com.apo.template.ui.book.BooksViewModel
import com.apo.template.ui.categories.CategoriesViewModel
import com.apo.template.ui.characters.CharactersViewModel
import com.apo.template.ui.houses.HousesViewModel
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
    viewModel<CategoriesViewModel> { CategoriesViewModel(get(), getProperty(CategoriesViewModel.LISTENER_ID)) }
    viewModel<BooksViewModel> { BooksViewModel(get(), getProperty(BooksViewModel.URL_ID)) }
    viewModel<HousesViewModel> { HousesViewModel(get(), getProperty(HousesViewModel.URL_ID)) }
    viewModel<CharactersViewModel> { CharactersViewModel(get(), getProperty(CharactersViewModel.URL_ID)) }
}
val repositoryModule = module {
    single<CategoriesRepository> { MainCategoriesRepository(get(), get()) }
    single<BookRepository> { NetworkBookRepository(get()) }
    single<HouseRepository> { NetworkHouseRepository(get()) }
    single<CharacterRepository> { NetworkCharacterRepository(get()) }
}

val serviceModule = module {
    single { CategoriesService(get()) }
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
    .baseUrl("https://android.mobgen.com/")
    .client(okHttp)
    .addConverterFactory(
        GsonConverterFactory.create(GsonBuilder().create())
    )
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()

val networkModule = module {
    single { createRetrofit(createOkHttpClient()).create(CategoriesApi::class.java) }
    single { createRetrofit(createOkHttpClient()).create(CategoriesTypeApi::class.java) }
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