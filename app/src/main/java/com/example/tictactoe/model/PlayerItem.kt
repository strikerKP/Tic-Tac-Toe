package com.example.tictactoe.model

data class PlayerItem(
    var isFirstPlayer: Boolean = true,
    var buttonPosition: Int = -1,
    var tapCount: Int = 0
)