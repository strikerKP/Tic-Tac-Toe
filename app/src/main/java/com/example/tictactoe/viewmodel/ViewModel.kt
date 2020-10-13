package com.example.tictactoe.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoe.model.PlayerItem


class GameViewModel : ViewModel() {
    var boardStatus = Array(3) { IntArray(3) }
    var updateText = MutableLiveData<Pair<String, Boolean>>()
    var mSetUserMove = MutableLiveData<PlayerItem>()

    var tapCount: Int = 0
    fun initializeBoardStatus() {
        for (i in 0..2) {
            for (j in 0..2) {
                boardStatus[i][j] = -1
            }
        }
    }

    private fun checkWinner() {
        for (i in 0..2) {
            if (boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][0] == boardStatus[i][2]) {
                if (boardStatus[i][0] == 1) {
                    updateDisplay("Player X Winner", true)
                    break
                } else if (boardStatus[i][0] == 0) {
                    updateDisplay("Player O Winner", true)
                    break
                }
            }
        }
        for (i in 0..2) {
            if (boardStatus[0][i] == boardStatus[1][i] && boardStatus[0][i] == boardStatus[2][i]) {
                if (boardStatus[0][i] == 1) {
                    updateDisplay("Player X Winner", true)
                    break
                } else if (boardStatus[0][i] == 0) {
                    updateDisplay("Player O Winner", true)
                    break
                }
            }
        }

        if (boardStatus[0][0] == boardStatus[1][1] && boardStatus[0][0] == boardStatus[2][2]) {
            if (boardStatus[0][0] == 1) {
                updateDisplay("Player X Winner", true)
            } else if (boardStatus[0][0] == 0) {
                updateDisplay("Player O Winner", true)
            }
        }

        if (boardStatus[0][2] == boardStatus[1][1] && boardStatus[0][2] == boardStatus[2][0]) {
            if (boardStatus[0][2] == 1) {
                updateDisplay("Player X Winner", true)
            } else if (boardStatus[0][2] == 0) {
                updateDisplay("Player O Winner", true)
            }
        }
    }

    fun updateValue(row: Int, col: Int, player: Boolean, buttonPosition: Int) {
        var moveMessage = if (player) "Player X Turn" else "Player O Turn"
        val move = if (player) "X" else "0"
        val value = if (player) 1 else 0
        boardStatus[row][col] = value
        tapCount++
        if (tapCount == 9)
            moveMessage = " Match Draw"
        mSetUserMove.value = PlayerItem(moveMessage, player.not(), buttonPosition, move)
//        mSetUserMove = PlayerItem(moveMessage, player.not(), buttonPosition, move)
        checkWinner()

    }

    private fun updateDisplay(text: String, isWinner: Boolean = false) {
        updateText.value = Pair(text, isWinner)

    }

//    fun getCurrentPlayerData(): PlayerItem {
//        return mSetUserMove
//    }
}