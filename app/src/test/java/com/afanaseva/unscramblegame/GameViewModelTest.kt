package com.afanaseva.unscramblegame
import com.afanaseva.unscramblegame.data.MAX_NO_OF_WORDS
import com.afanaseva.unscramblegame.data.SCORE_INCREASE
import com.afanaseva.unscramblegame.ui_model.GameViewModel
import org.junit.Assert.*
import org.junit.Test


class GameViewModelTest {
    private val viewModel = GameViewModel()


    @Test
    fun gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset() {
        var currentGameUiState = viewModel.uiState.value
        val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWorld)
        viewModel.updateUserGuess(correctPlayerWord)
        viewModel.checkUserGuess()

        currentGameUiState = viewModel.uiState.value

        assertEquals(SCORE_INCREASE, currentGameUiState.score)
        assertFalse(currentGameUiState.isGuessedWordWorng)
    }
    private fun getUnscrambledWord(scrambleWord: String): String {
        return com.afanaseva.unscramblegame.data.allWords.firstOrNull{
            word -> scrambleWord.toSet() == word.toSet()
        } ?: ""
    }

    @Test
    fun gameViewModel_IncorrectGuess_ErrorFlagSet(){
        val incorrectPlayerWord = "incorrect"
        viewModel.updateUserGuess(incorrectPlayerWord)
        viewModel.checkUserGuess()
        val currentGameUiState = viewModel.uiState.value
        assertEquals(0,currentGameUiState.score)

        assertTrue(currentGameUiState.isGuessedWordWorng)
    }

    @Test
    fun gameViewModel_Initialization_FirstWordLoaded(){
        val gameUiState = viewModel.uiState.value
        val unscrambledWord = getUnscrambledWord(gameUiState.currentScrambledWorld)

        assertNotEquals(unscrambledWord, gameUiState.currentScrambledWorld)
        assertTrue(gameUiState.currentWorldCount == 1)
        assertTrue(gameUiState.score == 0)
        assertFalse(gameUiState.isGameOver)
    }

    @Test
    fun gameViewModel_AllWordsGuessed_UiStateUpdatedCorrectly(){
        var expectedScore = 0
        var currentGameUiState = viewModel.uiState.value
        var correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWorld)
        repeat(MAX_NO_OF_WORDS){
            expectedScore += SCORE_INCREASE
            viewModel.updateUserGuess(correctPlayerWord)
            viewModel.checkUserGuess()
            currentGameUiState = viewModel.uiState.value
            correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWorld)
            assertEquals(expectedScore, currentGameUiState.score)
        }
        assertEquals(MAX_NO_OF_WORDS, currentGameUiState.currentWorldCount)
        assertTrue(currentGameUiState.isGameOver)

    }
}