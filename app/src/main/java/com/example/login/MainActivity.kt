package com.example.login

import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.login.ui.theme.LoginTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LoginScreen()
                }
            }
        }
    }
}

val email = "mail@server.com"
val password = "12345678"

@Composable
fun LoginScreen() {
    // Necesario usar rememberSaveable para que muestren los cambios en la barra
    var title by rememberSaveable { mutableStateOf("Login") }
    var loginOk by rememberSaveable { mutableStateOf(false) }
    var texto by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                actions = {
                    if (loginOk) {
                        Button(
                            onClick = {
                                loginOk = false
                                title = "Login"
                                texto = ""
                            },
                        ) { Text(text = "Log out") }
                    }
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val focusManager = LocalFocusManager.current

            var inputMail by rememberSaveable { mutableStateOf("") }
            var inputPassword by rememberSaveable { mutableStateOf("") }

            var isError by rememberSaveable { mutableStateOf(false) }

            Text(
                text = texto,
                color = if (isError) Color.Red else Color.Unspecified,
                modifier = Modifier.padding(10.dp)
            )

            if (!loginOk) {

                OutlinedTextField(
                    value = inputMail,
                    onValueChange = {
                        inputMail = it.trim()
                    }, // Aquí trim se puede usar porque no hay espacios.
                    label = { Text(text = "Mail") },
                    isError = isError,
                    modifier = Modifier.padding(5.dp)
                )

                OutlinedTextField(
                    value = inputPassword,
                    onValueChange = { inputPassword = it },
                    label = { Text(text = "Password") },
                    isError = isError,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.padding(5.dp)
                )

                Button(
                    onClick = {
                        if (validateLogin(inputMail, inputPassword)) {
                            loginOk = true
                            texto = "Acceso correcto"
                            title = "Pepe"
                            inputMail = ""
                            inputPassword = ""
                            isError = false
                        } else {
                            isError = true
                            loginOk = false
                            texto = "Email o contraseña inválidos"
                        }
                        focusManager.clearFocus() // Esconde el teclado
                    },
                    enabled = (inputPassword.length > 7 && inputMail.isValidEmail()),
                    modifier = Modifier.padding(5.dp)
                ) { Text(text = "Log in") }
            }
        }
    }
}

/**
 * Valida el inicio de sesión comparando el correo electrónico y la contraseña
 */
fun validateLogin(inputMail: String, inputPassword: String): Boolean {
    return inputMail == email && inputPassword == password
}

/**
 * Método de extensión: a una clase ya existente se le añade un método.
 * En este caso, validamos que contenga texto y que tenga formato de correo electrónico.
 * this = la String sobre la que se ejecuta el método.
 */
fun String.isValidEmail() = this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
