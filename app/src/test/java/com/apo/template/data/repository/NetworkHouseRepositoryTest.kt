package com.apo.template.data.repository

import com.apo.template.data.network.CategoriesTypeApi
import com.apo.template.data.network.houses.HouseJson
import com.apo.template.domain.house.House
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NetworkHouseRepositoryTest {
    @Mock
    private lateinit var api: CategoriesTypeApi

    private val repository by lazy {
        NetworkHouseRepository(api)
    }

    @Test
    fun should_return_houses() {
        //Given
        BDDMockito.given(api.getHouses("any", 1, 5)).willReturn(Single.just(listOf(houseJson)))
        //When
        val test = repository.getHouses("any", 1, 5).test()
        //Then
        test.assertNoErrors()
            .assertValueAt(0) {
                it.size == 1
                        && it[0].id == house.id
                        && it[0].name == house.name
                        && it[0].title == house.title
                        && it[0].region == house.region

            }
    }

    /** **********************************
     *          Data Mock
     *********************************** **/
    val houseJson = HouseJson(id = "1", name = "nameHouse", title = "Title house", region = "region")
    val house = House(id = "1", name = "nameHouse", title = "Title house", region = "region")
}