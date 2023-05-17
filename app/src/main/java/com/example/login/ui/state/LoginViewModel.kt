package com.example.login.ui.state

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.login.data.model.User

class LoginViewModel : ViewModel() {

    // Variables para comprobar
    val userEmail = "mail@server.com"
    val userPwd = "12345678"
    val userName = "Pepe"


    private var _emailString by mutableStateOf("")
    val emailString get() = _emailString

    fun changeEmailString(it: String) {
        _emailString = it.trim()
    }

    private var _pwdString by mutableStateOf("")
    val pwdString get() = _pwdString

    fun changePwdString(it: String) {
        _pwdString = it
    }

    // Interrogación: la variable puede ter un valor nulo
    private var _loggedUser: User? by mutableStateOf(null)
    val loggedUser get() = _loggedUser

    private var _loginError by mutableStateOf(false)
    val loginError get() = _loginError

    fun login() {
        if (_emailString == userEmail && _pwdString == userPwd) {
            _loggedUser = User(_emailString, _pwdString, userName)
            _emailString = ""
            _pwdString = ""
            _loginError = false
        } else {
            _loginError = true
        }
    }

    fun logOut() {
        _loggedUser = null
    }

    fun validEmailAndPwd(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(_emailString).matches() &&
                _pwdString.length > 7
    }

}