package com.example.tictactoe.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import com.example.tictactoe.R
import com.example.tictactoe.extra.Constants
import com.example.tictactoe.viewmodel.GameViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var alButton: ArrayList<AppCompatButton>
    private lateinit var gameViewModel: GameViewModel
    private var currentPlayer = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        gameViewModel.initializeBoardStatus()

        alButton = arrayListOf(b00, b01, b02, b10, b11, b12, b20, b21, b22)


        b00.setOnClickListener {
            gameViewModel.updateUserMove(
                Constants.CELL_ZERO,
                Constants.CELL_ZERO,
                currentPlayer,
                Constants.BUTTON_POSITION_ZERO
            )
        }
        b01.setOnClickListener {
            gameViewModel.updateUserMove(
                Constants.CELL_ZERO,
                Constants.CELL_ONE,
                currentPlayer,
                Constants.BUTTON_POSITION_ONE
            )
        }
        b02.setOnClickListener {
            gameViewModel.updateUserMove(
                Constants.CELL_ZERO,
                Constants.CELL_TWO,
                currentPlayer,
                Constants.BUTTON_POSITION_TWO
            )
        }

        b10.setOnClickListener {
            gameViewModel.updateUserMove(
                Constants.CELL_ONE,
                Constants.CELL_ZERO,
                currentPlayer,
                Constants.BUTTON_POSITION_THERE
            )
        }
        b11.setOnClickListener {
            gameViewModel.updateUserMove(
                Constants.CELL_ONE,
                Constants.CELL_ONE,
                currentPlayer,
                Constants.BUTTON_POSITION_FOUR
            )
        }
        b12.setOnClickListener {
            gameViewModel.updateUserMove(
                Constants.CELL_ONE,
                Constants.CELL_TWO,
                currentPlayer,
                Constants.BUTTON_POSITION_FIVE
            )
        }

        b20.setOnClickListener {
            gameViewModel.updateUserMove(
                Constants.CELL_TWO,
                Constants.CELL_ZERO,
                currentPlayer,
                Constants.BUTTON_POSITION_SIX
            )
        }
        b21.setOnClickListener {
            gameViewModel.updateUserMove(
                Constants.CELL_TWO,
                Constants.CELL_ONE,
                currentPlayer,
                Constants.BUTTON_POSITION_SEVEN
            )
        }
        b22.setOnClickListener {
            gameViewModel.updateUserMove(
                Constants.CELL_TWO,
                Constants.CELL_TWO,
                currentPlayer,
                Constants.BUTTON_POSITION_EIGHT
            )
        }

        btnReset.setOnClickListener {
            currentPlayer = true
            alButton.forEach {
                it.isEnabled = true
                it.text = ""
            }
            playerStatus.text = getString(R.string.reset_message)
            gameViewModel.initializeBoardStatus()
        }

        viewModelObserver()
    }

    /**
     * @param mSetUserMove observe when user perform any moves.
     * @param updateResult observe the final result.
     */
    private fun viewModelObserver() {
        gameViewModel.mSetUserMove.observe(this, {
            currentPlayer = it.isFirstPlayer.not()
            playerStatus.text =
                if (it.isFirstPlayer) getString(R.string.label_player_two) else getString(R.string.label_player_one)
            alButton[it.buttonPosition].apply {
                isEnabled = false
                text = if (it.isFirstPlayer) getString(R.string.player_move_one)
                else getString(R.string.player_move_two)
            }
            if (it.tapCount == 8) playerStatus.text = getString(R.string.match_draw_message)
        })

//        val currentPlayerItem = gameViewModel.getCurrentPlayerData()
//        currentPlayer = currentPlayerItem.isFirstPlayer
//        playerStatus.text = currentPlayerItem.playerMove
//        if (currentPlayerItem.buttonPosition != -1)
//            alButton[currentPlayerItem.buttonPosition].apply {
//                isEnabled = false
//                text = currentPlayerItem.movesValue
//            }

        gameViewModel.updateResult.observe(this, {
            playerStatus.text =
                if (it.first == Constants.PLAYER_ONE) getString(R.string.win_player_one)
                else getString(R.string.win_player_two)
            if (it.second)
                alButton.forEach { button -> button.isEnabled = false }

        })
    }
}