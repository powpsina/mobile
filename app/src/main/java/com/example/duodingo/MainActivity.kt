package com.example.duodingo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.duodingo.ui.theme.DuodingoTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import kotlin.random.Random
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.style.TextAlign
import com.example.duodingo.data.repository.WordsRepository
import java.util.Calendar
import com.example.duodingo.presentation.threewords.ThreeWordsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var darkTheme by rememberSaveable { mutableStateOf(false) }
            var showSettings by remember { mutableStateOf(false) }

            DuodingoTheme(darkTheme = darkTheme) {
                var selectedTopic by remember { mutableStateOf<WordsRepository.Topic?>(null) }
                var showMatch by remember { mutableStateOf(false) }
                var showThreeWords by remember { mutableStateOf(false) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Duodingo") },
                            actions = {
                                IconButton(onClick = { showSettings = true }) {
                                    Icon(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = "Настройки"
                                    )
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    when {
                        showSettings -> SettingsScreen(
                            darkTheme = darkTheme,
                            onToggleTheme = { isDark -> darkTheme = isDark },
                            onBack = { showSettings = false }
                        )
                        showThreeWords -> ThreeWordsScreen(onBack = { showThreeWords = false })
                        selectedTopic == null -> LevelsScreen(
                            modifier = Modifier.padding(innerPadding),
                            onTopicSelected = { topic ->
                                selectedTopic = topic
                                showMatch = false
                            },
                            onWordOfDayClick = { showThreeWords = true }
                        )
                        !showMatch -> TopicScreen(
                            topic = selectedTopic!!,
                            onStartMatch = { showMatch = true },
                            onBack = { selectedTopic = null }
                        )
                        else -> MatchScreen(
                            topic = selectedTopic!!,
                            onBack = { showMatch = false }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LevelsScreen(
    modifier: Modifier = Modifier,
    onTopicSelected: (WordsRepository.Topic) -> Unit,
    onWordOfDayClick: () -> Unit
) {
    val context = LocalContext.current
    val topics = remember { WordsRepository.topics }

    // Локальный список слов для слова дня
    val wordsOfDay = listOf(
        "lesson" to "урок",
        "apple" to "яблоко",
        "dog" to "собака",
        "book" to "книга",
        "sun" to "солнце",
        "car" to "машина",
        "house" to "дом",
        "tree" to "дерево",
        "water" to "вода",
        "milk" to "молоко",
        "table" to "стол",
        "chair" to "стул",
        "window" to "окно",
        "door" to "дверь",
        "pen" to "ручка",
        "school" to "школа",
        "teacher" to "учитель",
        "student" to "студент",
        "bag" to "сумка",
        "bread" to "хлеб",
        "cheese" to "сыр",
        "egg" to "яйцо",
        "butter" to "масло",
        "sugar" to "сахар",
        "salt" to "соль",
        "meat" to "мясо",
        "fish" to "рыба",
        "soup" to "суп",
        "rice" to "рис",
        "city" to "город"
    )
    val todayIndex = (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1) % wordsOfDay.size
    val wordOfDay = wordsOfDay[todayIndex]

    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.wallpaper),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.3f
        )
        Column(modifier = Modifier.padding(16.dp)) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Text(
                    text = "Английский язык — ключ к новым возможностям! Это приложение поможет вам легко и интересно выучить новые слова и улучшить свои знания.",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Выберите тему для изучения:")
            topics.forEach { topic ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            onTopicSelected(topic)
                        },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Text(
                        text = topic.title,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onWordOfDayClick() },
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "ежедневный словарь",
                        fontSize = 16.sp,
                    )
                }
            }
        }
    }
}

@Composable
fun TopicScreen(topic: WordsRepository.Topic, onStartMatch: () -> Unit, onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.wallpaper),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 1.0f
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = topic.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Text(
                            text = "Изучите слова:",
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        topic.words.forEach { word ->
                            Text(
                                text = "${word.english} — ${word.russian}",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                androidx.compose.material3.Button(
                    onClick = onStartMatch,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Перейти к упражнению")
                }
                androidx.compose.material3.OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        "Назад",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun MatchScreen(topic: WordsRepository.Topic, onBack: () -> Unit) {
    val words = topic.words
    val russianWords = remember { words.map { it.russian }.shuffled(Random(System.currentTimeMillis())) }
    val englishWords = remember { words.map { it.english }.shuffled(Random(System.currentTimeMillis() + 1)) }
    var selectedRussian by remember { mutableStateOf<String?>(null) }
    var selectedEnglish by remember { mutableStateOf<String?>(null) }
    var matchedPairs by remember { mutableStateOf(setOf<Pair<String, String>>()) }
    var errorPair by remember { mutableStateOf<Pair<String, String>?>(null) }
    val coroutineScope = rememberCoroutineScope()

    fun checkMatch(rus: String, eng: String) {
        val word = words.find { it.russian == rus && it.english == eng }
        if (word != null) {
            matchedPairs = matchedPairs + (rus to eng)
            selectedRussian = null
            selectedEnglish = null
            errorPair = null
        } else {
            errorPair = rus to eng
            coroutineScope.launch {
                delay(600)
                selectedRussian = null
                selectedEnglish = null
                errorPair = null
            }
        }
    }

    val isDark = isSystemInDarkTheme()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Упражнение: сопоставьте слова",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Нажмите на русское слово, затем на английское, чтобы найти пары.",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                russianWords.forEach { rus ->
                                    val isMatched = matchedPairs.any { it.first == rus }
                                    val isSelected = selectedRussian == rus
                                    val isError = errorPair?.first == rus
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        elevation = CardDefaults.cardElevation(2.dp),
                                        shape = RoundedCornerShape(8.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = when {
                                                isMatched -> Color(0xFFB9F6CA)
                                                isSelected -> Color(0xFFCCFF90)
                                                isError -> Color(0xFFFFCDD2)
                                                else -> MaterialTheme.colorScheme.surface
                                            }
                                        )
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable(enabled = !isMatched && selectedRussian == null && selectedEnglish == null) {
                                                    selectedRussian = rus
                                                }
                                        ) {
                                            Text(
                                                text = rus,
                                                fontSize = 16.sp,
                                                modifier = Modifier.padding(12.dp),
                                                color = when {
                                                    isMatched && isDark -> Color.White
                                                    isMatched && !isDark -> Color.Black
                                                    isSelected && isDark -> Color.White
                                                    isSelected && !isDark -> Color.Black
                                                    else -> MaterialTheme.colorScheme.onSurface
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                englishWords.forEach { eng ->
                                    val isMatched = matchedPairs.any { it.second == eng }
                                    val isSelected = selectedEnglish == eng
                                    val isError = errorPair?.second == eng
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        elevation = CardDefaults.cardElevation(2.dp),
                                        shape = RoundedCornerShape(8.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = when {
                                                isMatched -> Color(0xFFB9F6CA)
                                                isSelected -> Color(0xFFCCFF90)
                                                isError -> Color(0xFFFFCDD2)
                                                else -> MaterialTheme.colorScheme.surface
                                            }
                                        )
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable(enabled = !isMatched && selectedRussian != null && selectedEnglish == null) {
                                                    selectedEnglish = eng
                                                    if (selectedRussian != null) {
                                                        checkMatch(selectedRussian!!, eng)
                                                    }
                                                }
                                        ) {
                                            Text(
                                                text = eng,
                                                fontSize = 16.sp,
                                                modifier = Modifier.padding(12.dp),
                                                color = when {
                                                    isMatched && isDark -> Color.White
                                                    isMatched && !isDark -> Color.Black
                                                    isSelected && isDark -> Color.White
                                                    isSelected && !isDark -> Color.Black
                                                    else -> MaterialTheme.colorScheme.onSurface
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        if (matchedPairs.size == words.size) {
                            Text(
                                text = "Поздравляем! Все пары найдены!",
                                color = Color(0xFF388E3C),
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                        }
                    }
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                androidx.compose.material3.OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Назад к словам")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DuodingoTheme {
        LevelsScreen(onTopicSelected = {}, onWordOfDayClick = {})
    }
}