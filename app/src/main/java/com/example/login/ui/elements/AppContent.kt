package com.example.login.ui.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.login.ui.state.LoginViewModel

@Composable
fun AppContent() {

    val vm: LoginViewModel = viewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text( // Si no hay ningún usuario logueado (recibimos null), entonces mostramos "Login"
                        text = vm.loggedUser?.name ?: "Login"
                    )
                },
                actions = {
                    vm.loggedUser?.let {// Equivalente a if (vm.loggedUser != null)
                        Button(
                            onClick = {
                                vm.logOut()
                            },
                        ) { Text(text = "Log out") }
                    }
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            vm.loggedUser?.let {
                Text(text = "Acceso correcto")
                // TODO: Block con contenido para usuarios logueados
            } ?: LoginBlock(
                emailString = vm.emailString,
                changeEmailString = {vm.changeEmailString(it)},
                pwdString = vm.pwdString,
                changePwdString = {vm.changePwdString(it)},
                loginError = vm.loginError,
                enabledLogin = vm.validEmailAndPwd(),
                onLogin = {vm.login()},
            )
        }
    }
}