package com.afanaseva.unscramblegame.data

data class GameUiState(
    val currentScrambledWorld : String = "",
    val currentWorldCount : Int = 1,
    val score : Int = 0,
    val isGuessedWordWorng : Boolean = false,
    val isGameOver : Boolean = false
)