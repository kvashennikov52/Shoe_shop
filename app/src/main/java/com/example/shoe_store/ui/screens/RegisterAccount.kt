package com.example.shoe_store.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoe_store.R
import com.example.shoe_store.store.ui.viewmodels.RegisterAccountViewModel
import com.example.shoe_store.ui.components.DisableButton
import com.example.shoeshop.ui.components.BackButton



@Composable
fun RegisterAccountScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterAccountViewModel = viewModel (),
    onNavigateToSignIn: () -> Unit = {}
) {
    val state = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .imePadding()
            .systemBarsPadding()
            .padding(horizontal = 23.dp),
        verticalArrangement = Arrangement.Top,
    ) {
        Box(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {
            BackButton(onClick = onNavigateToSignIn)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.one),
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = stringResource(id = R.string.one),
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.SubTextLight),
                modifier = Modifier.padding(bottom = 40.dp)
            )

            Text(
                text = stringResource(id = R.string.one),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = state.name,
                onValueChange = viewModel::updateName,
                placeholder = { Text("xxxxxxxx") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                shape = MaterialTheme.shapes.medium
            )

            Text(
                text = stringResource(id = R.string.one),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = state.email,
                onValueChange = viewModel::updateEmail,
                placeholder = { Text("xyz@mail.com") },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = state.emailError
            )
            if (state.emailError) {
                Text(
                    text = "Некорректный формат email. (name@domain.xx)",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 4.dp, bottom = 12.dp)
                )
            } else {
                Spacer(modifier = Modifier.height(20.dp))
            }


            Text(
                text = stringResource(id = R.string.one),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = state.password,
                onValueChange = viewModel::updatePassword,
                placeholder = { Text("......") },
                visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                shape = MaterialTheme.shapes.medium,
                trailingIcon = {
                    IconButton(
                        onClick = viewModel::togglePasswordVisibility
                    ) {
                        Icon(

                            painter = painterResource(
                                id = if (state.isPasswordVisible) R.drawable.eye_open else R.drawable.eye_close
                            ),
                            contentDescription = if (state.isPasswordVisible) "Скрыть пароль" else "Показать пароль",
                            tint = colorResource(id = R.color.SubTextLight)
                        )
                    }
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(MaterialTheme.shapes.small)
                        .selectable(
                            selected = state.isTermsAccepted,
                            onClick = viewModel::toggleTermsAccepted,
                            role = Role.Checkbox
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (state.isTermsAccepted) {
                        Icon(
                            painter = painterResource(id = R.drawable.policy_check),
                            contentDescription = "Выбрано",
                            modifier = Modifier.size(16.dp),
                            tint = colorResource(id = R.color.Accent)
                        )
                    } else {
                        BorderStroke(
                            width = 2.dp,
                            color = colorResource(id = R.color.SubTextLight)
                        ).let { border ->
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(MaterialTheme.shapes.small)
                                    .border(border, MaterialTheme.shapes.small)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = stringResource(id = R.string.one),
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = R.color.Hint),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        DisableButton(
            text = stringResource(id = R.string.one),
            onClick = {
                viewModel.register(onNavigateToSignIn)
            },
            enabled = state.isFormValid
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(
                onClick = onNavigateToSignIn,
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = colorResource(id = R.color.Hint))) {
                            append(stringResource(id = R.string.one)) // Already Have Account? Log In
                        }
                    },
                    fontSize = 14.sp
                )
            }
        }
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = colorResource(id = R.color.Accent))
        }
    }

    state.dialogMessage?.let { message ->
        AlertDialog(
            onDismissRequest = viewModel::dismissDialog,
            title = { Text("Ошибка") },
            text = { Text(message) },
            confirmButton = {
                Button(onClick = viewModel::dismissDialog) {
                    Text("OK")
                }
            }
        )
    }
}



@Preview(showBackground = true)
@Composable
private fun RegisterAccountScreenPreview() {
    MaterialTheme {
        RegisterAccountScreen()
    }
}