package com.example.tictactoe.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import com.example.tictactoe.R
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
            gameViewModel.updateValue(0, 0, currentPlayer, 0)
        }
        b01.setOnClickListener {
            gameViewModel.updateValue(0, 1, currentPlayer, 1)
        }
        b02.setOnClickListener {
            gameViewModel.updateValue(0, 2, currentPlayer, 2)
        }

        b10.setOnClickListener {
            gameViewModel.updateValue(1, 0, currentPlayer, 3)
        }
        b11.setOnClickListener {
            gameViewModel.updateValue(1, 1, currentPlayer, 4)
        }
        b12.setOnClickListener {
            gameViewModel.updateValue(1, 2, currentPlayer, 5)
        }

        b20.setOnClickListener {
            gameViewModel.updateValue(2, 0, currentPlayer, 6)
        }
        b21.setOnClickListener {
            gameViewModel.updateValue(2, 1, currentPlayer, 7)
        }
        b22.setOnClickListener {
            gameViewModel.updateValue(2, 2, currentPlayer, 8)
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

    private fun viewModelObserver() {
        gameViewModel.mSetUserMove.observe(this, {
            currentPlayer = it.isFirstPlayer
            playerStatus.text = it.playerMove
            alButton[it.buttonPosition].apply {
                isEnabled = false
                text = it.movesValue
            }
        })

//        val currentPlayerItem = gameViewModel.getCurrentPlayerData()
//        currentPlayer = currentPlayerItem.isFirstPlayer
//        playerStatus.text = currentPlayerItem.playerMove
//        if (currentPlayerItem.buttonPosition != -1)
//            alButton[currentPlayerItem.buttonPosition].apply {
//                isEnabled = false
//                text = currentPlayerItem.movesValue
//            }

        gameViewModel.updateText.observe(this, {
            playerStatus.text = it.first
            if (it.second)
                alButton.forEach { button -> button.isEnabled = false }

        })
    }
}