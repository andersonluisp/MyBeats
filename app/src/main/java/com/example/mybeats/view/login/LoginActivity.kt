package com.example.mybeats.view.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.mybeats.R
import com.example.mybeats.databinding.ActivityLoginBinding
import com.example.mybeats.view.products.ProductsActivity
import com.example.mybeats.view.signup.SignUpActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)
        setupFullScreen()
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenCreated {
            viewModel.loginState.collect { loginState ->
                when (loginState) {
                    is LoginViewModel.ViewState.Initial -> showInitialState()
                    is LoginViewModel.ViewState.Loading -> showLoadingState(true)
                    is LoginViewModel.ViewState.Success -> startProductsActivity()
                    is LoginViewModel.ViewState.Error -> showLoginFailedState(loginState)
                }
            }
        }
    }

    private fun showLoadingState(isLoading: Boolean) = binding.btnEnter.setLoading(isLoading)

    private fun showInitialState() = with(binding) {
        showLoadingState(false)
        tilPassword.error = null
        tilUser.error = null
    }

    private fun showLoginFailedState(loginState: LoginViewModel.ViewState.Error) = with(binding) {
        showLoadingState(false)
        when (loginState.error) {
            LoginViewModel.ViewState.LoginErrors.USER_NOT_FOUND -> {
                btnEnter.isEnabled = false
                tilUser.error = getString(R.string.user_not_found)
            }
            LoginViewModel.ViewState.LoginErrors.WRONG_PASSWORD -> {
                btnEnter.isEnabled = false
                tilPassword.error = getString(R.string.invalid_password)
            }
            else -> {
                Snackbar.make(this.root, getString(R.string.error_try_again_later), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun startProductsActivity() {
        val intent = Intent(this, ProductsActivity::class.java)
        startActivity(intent)
        finish()
    }

    @Suppress("DEPRECATION")
    private fun setupFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun setupListeners() = with(binding) {
        btnEnter.setOnClickListener {
            val username = binding.etUser.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.signIn(username, password)
        }
        etUser.addTextChangedListener {
            tilUser.error = null
            if (tilPassword.error == null) btnEnter.isEnabled = true
        }
        etPassword.addTextChangedListener {
            tilPassword.error = null
            if (tilUser.error == null) btnEnter.isEnabled = true
        }
        tvSubscribe.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
