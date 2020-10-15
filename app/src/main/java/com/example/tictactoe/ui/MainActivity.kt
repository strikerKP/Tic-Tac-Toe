package com.example.tictactoe.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.tictactoe.R
import com.example.tictactoe.databinding.ActivityMainBinding
import com.example.tictactoe.extra.Constants
import com.example.tictactoe.model.PlayerResultItem
import com.example.tictactoe.viewmodel.GameViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var playerResultItem: PlayerResultItem
    private lateinit var alButton: ArrayList<AppCompatButton>
    private lateinit var gameViewModel: GameViewModel
    private var currentPlayer = true
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
    }

    private fun init() {
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        gameViewModel.initializeBoardStatus()
        playerResultItem = PlayerResultItem()

        alButton = arrayListOf(
            binding.btnCellZeroZero,
            binding.btnCellZeroOne,
            binding.btnCellZeroTwo,
            binding.btnCellOneZero,
            binding.btnCellOneOne,
            binding.btnCellOneTwo,
            binding.btnCellTwoZero,
            binding.btnCellTwoOne,
            binding.btnCellTwoTwo
        )


        binding.btnCellZeroZero.setOnClickListener {
            gameViewModel.updateUserMove(
                Constants.CELL_ZERO,
                Constants.CELL_ZERO,
                currentPlayer,
                Constants.BUTTON_POSITION_ZERO
            )
        }
        binding.btnCellZeroOne.setOnClickListener {
            gameViewModel.updateUserMove(
                Constants.CELL_ZERO,
                Constants.CELL_ONE,
                currentPlayer,
                Constants.BUTTON_POSITION_ONE
            )
        }
        binding.btnCellZeroTwo.setOnClickListener {
            gameViewModel.updateUserMove(
                Constants.CELL_ZERO,
                Constants.CELL_TWO,
                currentPlayer,
                Constants.BUTTON_POSITION_TWO
            )
        }

        binding.btnCellOneZero.setOnClickListener {
            gameViewModel.updateUserMove(
                Constants.CELL_ONE,
                Constants.CELL_ZERO,
                currentPlayer,
                Constants.BUTTON_POSITION_THERE
            )
        }
        binding.btnCellOneOne.setOnClickListener {
            gameViewModel.updateUserMove(
                Constants.CELL_ONE,
                Constants.CELL_ONE,
                currentPlayer,
                Constants.BUTTON_POSITION_FOUR
            )
        }
        binding.btnCellOneTwo.setOnClickListener {
            gameViewModel.updateUserMove(
                Constants.CELL_ONE,
                Constants.CELL_TWO,
                currentPlayer,
                Constants.BUTTON_POSITION_FIVE
            )
        }

        binding.btnCellTwoZero.setOnClickListener {
            gameViewModel.updateUserMove(
                Constants.CELL_TWO,
                Constants.CELL_ZERO,
                currentPlayer,
                Constants.BUTTON_POSITION_SIX
            )
        }
        binding.btnCellTwoOne.setOnClickListener {
            gameViewModel.updateUserMove(
                Constants.CELL_TWO,
                Constants.CELL_ONE,
                currentPlayer,
                Constants.BUTTON_POSITION_SEVEN
            )
        }
        binding.btnCellTwoTwo.setOnClickListener {
            gameViewModel.updateUserMove(
                Constants.CELL_TWO,
                Constants.CELL_TWO,
                currentPlayer,
                Constants.BUTTON_POSITION_EIGHT
            )
        }

        binding.btnReset.setOnClickListener {
            currentPlayer = true
            alButton.forEach {
                it.isEnabled = true
                it.text = ""
            }
            binding.playerResultItem = PlayerResultItem(getString(R.string.reset_message))
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

            binding.playerResultItem =
                PlayerResultItem(
                    if (it.isFirstPlayer) getString(R.string.label_player_two) else getString(
                        R.string.label_player_one
                    )
                )

            alButton[it.buttonPosition].apply {
                isEnabled = false
                text = if (it.isFirstPlayer) getString(R.string.player_move_one)
                else getString(R.string.player_move_two)
            }
            if (it.tapCount == 8) binding.playerResultItem =
                PlayerResultItem(getString(R.string.match_draw_message))
        })

        gameViewModel.updateResult.observe(this, {
            binding.playerResultItem =
                PlayerResultItem(
                    if (it.first == Constants.PLAYER_ONE) getString(R.string.win_player_one)
                    else getString(R.string.win_player_two)
                )
            if (it.second)
                alButton.forEach { button -> button.isEnabled = false }
        })

    }
}