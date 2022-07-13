package com.alva.codedelaroute.screens.home_screen.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.navigationBarsWithImePadding

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun ProfileBottomSheet() {
    val email = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp).imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Your Account",
            fontSize = 24.sp,
            lineHeight = 32.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF212121)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            "You can easily study on any other device using the same account logged in without missing your progress and Pro pack!",
            fontSize = 14.sp,
            lineHeight = 18.sp,
            color = Color(0xFF575758),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(50.dp))

        TextField(
            value = email.value,
            onValueChange = {
                email.value = it
            },
            label = { Text("Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email, imeAction = ImeAction.Go),
            placeholder = { Text("Type your email") },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            "By signing in your accept our", fontSize = 14.sp, lineHeight = 18.sp, color = Color(0xFF575758)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            "PRIVACY POLICY",
            fontSize = 14.sp,
            lineHeight = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF212121)
        )
    }
}