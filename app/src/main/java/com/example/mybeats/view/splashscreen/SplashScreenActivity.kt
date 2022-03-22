package com.example.mybeats.view.splashscreen

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mybeats.data.local.sharedpreferences.LoginSharedPreferences
import com.example.mybeats.data.model.User
import com.example.mybeats.databinding.ActivitySplashScreenBinding
import com.example.mybeats.view.login.LoginActivity
import com.example.mybeats.view.products.ProductsActivity
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    companion object {
        private const val ANIMATION_SPEED = 1.5F
    }

    private val binding: ActivitySplashScreenBinding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }
    private val viewModel: SplashScreenViewModel by viewModel()

    private var destination: SplashScreenViewModel.ViewState.SplashScreenNavigation? = null
    private var loggedUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupFullScreen()
        setupAnimation()
        setupObservers()
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenCreated {
            viewModel.userStateFlow.collect {
                when (it) {
                    is SplashScreenViewModel.ViewState.Initial -> {
                        binding.splashScreenAnimation.playAnimation()
                        attemptLoginWithSavedCredentials()
                    }
                    is SplashScreenViewModel.ViewState.Navigate -> {
                        destination = it.destination
                        loggedUser = it.data
                    }
                }
            }
        }
    }

    private fun navigate(
        user: User?,
        destination: SplashScreenViewModel.ViewState.SplashScreenNavigation?
    ) {
        val intent = when (destination) {
            SplashScreenViewModel.ViewState.SplashScreenNavigation.NAVIGATE_TO_PRODUCTS_ACTIVITY -> {
                Intent(this, ProductsActivity::class.java)
            }
            else -> {
                Intent(this, LoginActivity::class.java)
            }
        }
        user?.let {
            intent.putExtra("user", user)
        }
        startActivity(intent)
        finish()
    }

    private fun attemptLoginWithSavedCredentials() {
        val credentials = LoginSharedPreferences.getSavedCredentials(this)
        viewModel.attemptLogin(credentials["username"], credentials["password"])
    }

    private fun setupAnimation() {
        binding.splashScreenAnimation.apply {
            speed = ANIMATION_SPEED
            addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) = Unit
                override fun onAnimationEnd(p0: Animator?) {
                    navigate(loggedUser, destination)
                }
                override fun onAnimationCancel(p0: Animator?) = Unit
                override fun onAnimationRepeat(p0: Animator?) = Unit
            })
        }
    }
    @Suppress("DEPRECATION")
    private fun setupFullScreen() {
        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }
}
