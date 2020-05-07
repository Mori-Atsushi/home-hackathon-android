package com.example.home_hackathon.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.home_hackathon.R
import com.example.home_hackathon.databinding.ActivityMainBinding
import com.example.home_hackathon.ui.ext.bind
import com.example.home_hackathon.ui.ext.setVisibleGone
import com.example.home_hackathon.ui.keyboard.KeyboardController
import com.example.home_hackathon.ui.keyboard.KeyboardListener
import com.example.home_hackathon.ui.user.UserController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    private val userController = UserController()
    private val keyboardController = KeyboardController(object : KeyboardListener {
        override fun onChangeKey(key: Int, isDown: Boolean) {
            viewModel.touch(key, isDown)
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        bindViewModel()
    }

    private fun setupViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.keyboards.also {
            it.isUserInputEnabled = false
            it.adapter = keyboardController.adapter
            keyboardController.setData(null)
            it.setCurrentItem(viewModel.currentPageValue, false)
        }
        binding.users.adapter = userController.adapter
    }

    private fun bindViewModel() {
        binding.buttonLeft.setOnClickListener {
            viewModel.leftPage()
        }

        binding.buttonRight.setOnClickListener {
            viewModel.rightPage()
        }

        bind(viewModel.keyboard, keyboardController::setData)
        bind(viewModel.users, userController::setData)
        bind(viewModel.currentPage) {
            binding.keyboards.currentItem = it
        }
        bind(viewModel.currentPage) {
            binding.pageNum.text = it.toString()
        }
        bind(viewModel.isEnabledLeft) {
            binding.buttonLeft.isEnabled = it
        }
        bind(viewModel.isEnableRight) {
            binding.buttonRight.isEnabled = it
        }
        bind(viewModel.isLoading, binding.progressBar::setVisibleGone)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}
