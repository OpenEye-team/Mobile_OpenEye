package com.txtlabs.openeye.ui.auth.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.txtlabs.openeye.data.ResultState
import com.txtlabs.openeye.data.post.RegisterBody
import com.txtlabs.openeye.data.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _emailError = MutableLiveData<String>()
    val emailError: LiveData<String> = _emailError

    private val _userNameError = MutableLiveData<String>()
    val userNameError: LiveData<String> = _userNameError

    private val _passwordConfirmationError = MutableLiveData<String>()
    val passwordConfirmationError: LiveData<String> = _passwordConfirmationError

    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String> = _passwordError

    private val _registerState = MutableLiveData<ResultState<Boolean>>()
    val registerState: LiveData<ResultState<Boolean>> = _registerState

    fun register(
        email: String,
        username: String,
        password: String,
        confirmationPassword: String
    ) {
        if (email == "" || email.isEmpty()) {
            _emailError.value = "Silakan masukkan Email terlebih dahulu!"
        } else if (username == "" || username.isEmpty()) {
            _userNameError.value = "Silakan masukkan Nama terlebih dahulu!"
        }  else if (confirmationPassword == "" || confirmationPassword.isEmpty()) {
            _passwordConfirmationError.value = "Silakan masukkan password terlebih dahulu!"
        } else if (password == "" || password.isEmpty()) {
            _passwordError.value = "Silakan masukkan password terlebih dahulu!"
        } else if (password != confirmationPassword) {
            _passwordConfirmationError.value = "Password tidak sama!"
        } else if (password.length < 8) {
            _passwordError.value = "Password minimal 8 karakter!"
        } else if (confirmationPassword.length < 8) {
            _passwordConfirmationError.value = "Password minimal 8 karakter!"
        } else if (username.length < 3) {
            _userNameError.value = "Nama minimal 3 karakter!"
        } else if (username.length > 20) {
            _userNameError.value = "Nama maksimal 20 karakter!"
        } else if (email.length < 3) {
            _emailError.value = "Email minimal 3 karakter!"
        } else if (email.length > 20) {
            _emailError.value = "Email maksimal 20 karakter!"
        } else if (password.length > 20) {
            _passwordError.value = "Password maksimal 20 karakter!"
        } else if (confirmationPassword.length > 20) {
            _passwordConfirmationError.value = "Password maksimal 20 karakter!"
        } else {
            pushSignUp(
                email, username, password, confirmationPassword
            )
        }
    }

    private fun pushSignUp(
        email: String,
        username: String,
        password: String,
        confirmationPassword: String,
    ) {
        _registerState.value = ResultState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val signUpBody = RegisterBody(
                 email, username, password, confirmationPassword
            )
            val apiClient = ApiConfig.getApiService().postRegister(signUpBody)
            try {
                Log.d("RegisterViewModel", "pushSignUp: $apiClient")
                _registerState.postValue(ResultState.Success(true))
                Log.d("RegisterViewModel", "pushSignUp: $apiClient")
            } catch (e: Exception) {
                _registerState.postValue(ResultState.Failure(e))
            }
        }
    }

}