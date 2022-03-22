package com.example.mybeats.view.signup

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.mybeats.R
import com.example.mybeats.databinding.ActivitySignUpBinding
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    private val viewModel: SignUpViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupFullScreen()
        setupObservers()
        setupListeners()
    }

    private fun setupListeners() = with(binding) {
        etName.addTextChangedListener {
            tilName.error = null
            enableRegisterButton()
        }

        etUsername.addTextChangedListener {
            tilUsername.error = null
            enableRegisterButton()
        }

        etPassword.addTextChangedListener {
            tilPassword.error = null
            enableRegisterButton()
        }

        btnRegister.setOnClickListener {
            val name = etName.text.toString()
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            viewModel.signUp(name, username, password)
        }
    }

    private fun enableRegisterButton() = with(binding) {
        if (tilName.error == null && tilUsername.error == null && tilPassword.error == null)
            btnRegister.isEnabled = true
    }

    private fun setupObservers() {

        lifecycleScope.launchWhenCreated {
            viewModel.registerViewState.collect { viewState ->
                when (viewState) {
                    is SignUpViewModel.ViewState.Initial -> Unit
                    is SignUpViewModel.ViewState.Loading -> binding.btnRegister.setLoading(true)
                    is SignUpViewModel.ViewState.Success -> {
                        Toast.makeText(
                            this@SignUpActivity,
                            getString(R.string.user_created_success),
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.btnRegister.setLoading(false)
                        finish()
                    }
                    is SignUpViewModel.ViewState.Error -> showFieldError(viewState.error)
                }
            }
        }
    }

    private fun showFieldError(fieldErrorList: List<SignUpViewModel.ViewState.FieldError>) = with(binding) {
        btnRegister.apply {
            setLoading(false)
            isEnabled = false
        }
        fieldErrorList.forEach {
            when (it) {
                SignUpViewModel.ViewState.FieldError.EMPTY_NAME ->
                    tilName.error = getString(R.string.name_can_not_empty)
                SignUpViewModel.ViewState.FieldError.EMPTY_USERNAME ->
                    tilUsername.error = getString(R.string.user_can_not_empty)
                SignUpViewModel.ViewState.FieldError.EMPTY_PASSWORD ->
                    tilPassword.error = getString(R.string.password_can_not_empty)
                SignUpViewModel.ViewState.FieldError.USER_ALREADY_REGISTERED ->
                    tilUsername.error = getString(R.string.user_already_registered)
            }
        }
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
}
