package com.example.tictactoe.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoe.extra.Constants
import com.example.tictactoe.model.PlayerItem


class GameViewModel : ViewModel() {
    var boardStatus = Array(3) { IntArray(3) }
    var updateResult = MutableLiveData<Pair<Int, Boolean>>()
    var mSetUserMove = MutableLiveData<PlayerItem>()

    var tapCount: Int = 0

    /**
     * initialize the board when match started or match is reset.
     */
    fun initializeBoardStatus() {
        for (i in 0..2) {
            for (j in 0..2) {
                boardStatus[i][j] = -1
            }
        }
    }

    /**
     * This method is used for check the status of match.
     */
    private fun checkWinnerStatus() {
        /**
         * this loop is used to check vertical wining possibility
         */
        for (i in 0..2) {
            if (boardStatus[i][Constants.CELL_ZERO] == boardStatus[i][Constants.CELL_ONE]
                && boardStatus[i][Constants.CELL_ZERO] == boardStatus[i][Constants.CELL_TWO]
            ) {
                when {
                    boardStatus[i][Constants.CELL_ZERO] == Constants.PLAYER_ONE -> {
                        resultMessage(Constants.PLAYER_ONE, true)
                        break
                    }
                    boardStatus[i][Constants.CELL_ZERO] == Constants.PLAYER_TWO -> {
                        resultMessage(Constants.PLAYER_TWO, true)
                        break
                    }
                }
            }
        }

        /**
         * this loop is used to check horizontal wining possibility
         */
        for (i in 0..2) {
            if (boardStatus[Constants.CELL_ZERO][i] == boardStatus[Constants.CELL_ONE][i]
                && boardStatus[Constants.CELL_ZERO][i] == boardStatus[Constants.CELL_TWO][i]
            ) {
                when {
                    boardStatus[Constants.CELL_ZERO][i] == Constants.PLAYER_ONE -> {
                        resultMessage(Constants.PLAYER_ONE, true)
                        break
                    }
                    boardStatus[Constants.CELL_ZERO][i] == Constants.PLAYER_TWO -> {
                        resultMessage(Constants.PLAYER_TWO, true)
                        break
                    }
                }
            }
        }

        /**
         * This both condition is used to check diagonally wining possibility
         */
        if (boardStatus[Constants.CELL_ZERO][Constants.CELL_ZERO] == boardStatus[Constants.CELL_ONE][Constants.CELL_ONE]
            && boardStatus[Constants.CELL_ZERO][Constants.CELL_ZERO] == boardStatus[Constants.CELL_TWO][Constants.CELL_TWO]
        ) {
            when {
                boardStatus[Constants.CELL_ZERO][Constants.CELL_ZERO] == Constants.PLAYER_ONE ->
                    resultMessage(Constants.PLAYER_ONE, true)
                boardStatus[Constants.CELL_ZERO][Constants.CELL_ZERO] == Constants.PLAYER_TWO ->
                    resultMessage(Constants.PLAYER_TWO, true)
            }
        }

        if (boardStatus[Constants.CELL_ZERO][Constants.CELL_TWO] == boardStatus[Constants.CELL_ONE][Constants.CELL_ONE]
            && boardStatus[Constants.CELL_ZERO][Constants.CELL_TWO] == boardStatus[Constants.CELL_TWO][Constants.CELL_ZERO]
        ) {
            when {
                boardStatus[Constants.CELL_ZERO][Constants.CELL_TWO] == Constants.PLAYER_ONE ->
                    resultMessage(Constants.PLAYER_ONE, true)
                boardStatus[Constants.CELL_ZERO][Constants.CELL_TWO] == Constants.PLAYER_TWO ->
                    resultMessage(Constants.PLAYER_TWO, true)

            }
        }
    }

    /**
     * This method is used to update the next move and also update to user.
     */
    fun updateUserMove(row: Int, col: Int, player: Boolean, buttonPosition: Int) {
        boardStatus[row][col] = if (player) Constants.PLAYER_ONE else Constants.PLAYER_TWO
        mSetUserMove.value = PlayerItem(player, buttonPosition, tapCount++)
//        mSetUserMove = PlayerItem(moveMessage, player.not(), buttonPosition, move)
        checkWinnerStatus()

    }

    /**
     * This method is invoke when any player is win, and reset the board.
     */
    private fun resultMessage(player: Int, isWinner: Boolean = false) {
        tapCount = 0
        updateResult.value = Pair(player, isWinner)
    }

//    fun getCurrentPlayerData(): PlayerItem {
//        return mSetUserMove
//    }
}