package com.example.login.ui.elements

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Composable
fun LoginBlock(
    emailString: String,
    changeEmailString: (String) -> Unit,
    pwdString: String,
    changePwdString: (String) -> Unit,
    loginError: Boolean,
    enabledLogin: Boolean,
    onLogin: () -> Unit,
) {
    if (loginError) {
        Text(
            text = "Email o contraseña inválidos",
            color = Color.Red,
            //modifier = Modifier.padding(10.dp)
        )
    }

    OutlinedTextField(
        value = emailString,
        onValueChange = changeEmailString,
        label = { Text(text = "Mail") },
        isError = loginError,
        //modifier = Modifier.padding(5.dp)
    )

    OutlinedTextField(
        value = pwdString,
        onValueChange = changePwdString,
        label = { Text(text = "Password") },
        isError = loginError,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        //modifier = Modifier.padding(5.dp)
    )

    Button(
        onClick = onLogin,
        enabled = enabledLogin
    ) { Text(text = "Log in") }

}