package com.example.taskify.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

// App color definition
val appColor = Color(0xFF6650a4)

@Composable
fun IntroScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to Taskify!",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = appColor,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(16.dp))

        // Using AnnotatedString to style the name portion
        Text(
            text = buildAnnotatedString {
                append("Taskify is a simple and intuitive task management app that helps you stay organized and productive. ")
                append("It was built by ")
                withStyle(style = MaterialTheme.typography.bodyLarge.toSpanStyle().copy(fontWeight = FontWeight.Bold, color = appColor)) {
                    append("Augustine Onyirioha")
                }
                append(" in partial fulfillment of my WS2024 Mobile Coding course.")
            },
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(16.dp))

        // Continue button to navigate to the main screen
        Button(onClick = { navController.navigate("main") }) {
            Text(text = "Continue")
        }
    }
}
