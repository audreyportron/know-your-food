package com.apo.template.ui.characters

import com.apo.template.R
import com.apo.template.domain.character.Character
import org.junit.Assert.assertTrue
import org.junit.Test

class CharacterItemViewModelTest {
    @Test
    fun should_display_Character_with_alias() {
        //Given
        val character = Character(
            name = "name",
            aliases = listOf("myAlias")
        )

        //When
        val model = CharacterItemViewModel(character)

        //Then
        assertTrue(model.characterTextId == R.string.character_with_alias)
        assertTrue(model.characterTextArgs == arrayListOf(character.name, character.aliases.first()))
    }

    @Test
    fun should_display_Character_without_alias() {
        //Given
        val character = Character(
            name = "name",
            aliases = listOf("")
        )

        //When
        val model = CharacterItemViewModel(character)

        //Then
        assertTrue(model.characterTextId == R.string.character_empty_alias)
        assertTrue(model.characterTextArgs == arrayListOf(character.name))
    }

    @Test
    fun should_display_Character_withbank_first_alias() {
        //Given
        val character = Character(
            name = "name",
            aliases = emptyList()
        )

        //When
        val model = CharacterItemViewModel(character)

        //Then
        assertTrue(model.characterTextId == R.string.character_empty_alias)
        assertTrue(model.characterTextArgs == arrayListOf(character.name))
    }
}