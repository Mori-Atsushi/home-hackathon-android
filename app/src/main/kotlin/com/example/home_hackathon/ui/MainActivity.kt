package com.example.home_hackathon.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.home_hackathon.R
import com.example.home_hackathon.databinding.ActivityMainBinding
import com.example.home_hackathon.ui.keyboard.KeyboardController
import com.example.home_hackathon.ui.keyboard.KeyboardListener
import com.example.home_hackathon.ui.user.UserController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by inject()

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
            keyboardController.requestModelBuild()
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

        viewModel.users
            .onEach { userController.setData(it) }
            .launchIn(lifecycleScope)

        viewModel.currentPage
            .onEach { binding.keyboards.currentItem = it }
            .launchIn(lifecycleScope)

        viewModel.currentPage
            .onEach { binding.pageNum.text = it.toString() }
            .launchIn(lifecycleScope)

        viewModel.isEnabledLeft
            .onEach { binding.buttonLeft.isEnabled = it }
            .launchIn(lifecycleScope)

        viewModel.isEnableRight
            .onEach { binding.buttonRight.isEnabled = it }
            .launchIn(lifecycleScope)
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
