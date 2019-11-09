package com.apo.template.ui.characters

import com.apo.template.domain.character.Character
import com.apo.template.domain.character.CharacterRepository
import com.apo.template.tools.AppSchedulers
import com.apo.template.tools.initForTests
import io.reactivex.Single
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharactersViewModelTest {


    @Mock
    val repository: CharacterRepository = Mockito.mock(NetworkCharacterRepository::class.java)


    private val viewModel: CharactersViewModel by lazy {
        CharactersViewModel(repository, "any")
    }

    @Before
    fun before() {
        AppSchedulers.initForTests()
    }


    @Test
    fun should_get_characters_and_show_when_service_no_error() {
        //Given
        val character = Character(name = "name", aliases = listOf("Aliases"))
        BDDMockito.given(repository.getCharacters("any")).willReturn(Single.just(listOf(character)))

        //When
        viewModel.getData()

        //Then
        assertTrue(viewModel.charactersViewModel.size == 1)
        assertTrue(viewModel.charactersViewModel[0] is CharacterItemViewModel)
        assertFalse(viewModel.loading.get())
        assertFalse(viewModel.error.get())
    }


    @Test
    fun should_show_error_when_get_data_error() {
        //Given
        BDDMockito.given(repository.getCharacters("any")).willReturn(Single.error(Throwable()))

        //When
        viewModel.getData()

        //Then
        assertTrue(viewModel.charactersViewModel.size == 0)
        assertFalse(viewModel.loading.get())
        assertTrue(viewModel.error.get())
    }


    @Test
    fun should_show_error_when_characters_empty() {
        //Given
        BDDMockito.given(repository.getCharacters("any")).willReturn(Single.just(emptyList()))

        //When
        viewModel.getData()

        //Then
        assertTrue(viewModel.charactersViewModel.size == 0)
        assertTrue(viewModel.loading.get() == false)
        assertTrue(viewModel.error.get() == true)
    }
}