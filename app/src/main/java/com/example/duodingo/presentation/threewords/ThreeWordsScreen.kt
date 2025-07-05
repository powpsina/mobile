package com.example.duodingo.presentation.threewords

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import com.example.duodingo.R

@Composable
fun ThreeWordsScreen(
    onBack: () -> Unit,
    viewModel: ThreeWordsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ThreeWordsEffect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
                is ThreeWordsEffect.ShowTranslation -> {
                    Toast.makeText(
                        context,
                        "Перевод успешно получен",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.wallpaper),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.3f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .wrapContentHeight(),
                elevation = CardDefaults.cardElevation(8.dp),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Column(
                    modifier = Modifier.padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Переводчик слова",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Button(
                        onClick = { viewModel.onEvent(ThreeWordsEvent.OnRandomWordRequested) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        enabled = !state.isLoading
                    ) {
                        Text(
                            if (state.isLoading && state.randomWord.isEmpty()) "Генерация..."
                            else "Сгенерировать случайное слово",
                            fontSize = 16.sp
                        )
                    }

                    TextField(
                        value = state.userWord,
                        onValueChange = {
                            viewModel.onEvent(ThreeWordsEvent.OnWordChanged(it))
                        },
                        placeholder = { Text("Введите английское слово") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = MaterialTheme.shapes.large,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = if (state.randomWord.isNotBlank())
                                Color(0xFFE3F2FD) else MaterialTheme.colorScheme.surface
                        ),
                        isError = state.error != null
                    )

                    Button(
                        onClick = { viewModel.onEvent(ThreeWordsEvent.OnTranslateRequested) },
                        enabled = state.userWord.isNotBlank() && !state.isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        Text(
                            if (state.isLoading && state.userWord.isNotBlank()) "Перевод..."
                            else "Перевести",
                            fontSize = 18.sp
                        )
                    }

                    if (state.translation.isNotBlank()) {
                        Spacer(modifier = Modifier.height(20.dp))
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(2.dp),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text(
                                    "Перевод:",
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                                Text(
                                    state.translation,
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }

                    if (state.randomWord.isNotBlank() && !state.isLoading) {
                        Text(
                            text = "Сгенерировано слово: ${state.randomWord}",
                            color = Color.Gray,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            Button(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Text("Назад", fontSize = 18.sp)
            }
        }
    }
}