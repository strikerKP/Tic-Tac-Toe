package com.example.tictactoe.model

data class PlayerItem(
    var playerMove: String = "Player X Turn",
    var isFirstPlayer: Boolean = true,
    var buttonPosition: Int = -1,
    var movesValue: String = "X"
)