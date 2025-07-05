package com.example.duodingo.data.repository


object WordsRepository {
    data class Word(val english: String, val russian: String)
    data class Topic(val title: String, val words: List<Word>)

    val topics = listOf(
        Topic("Животные", listOf(
            Word("cat", "кот"),
            Word("dog", "собака"),
            Word("bird", "птица"),
            Word("fish", "рыба"),
            Word("horse", "лошадь"),
            Word("cow", "корова"),
            Word("sheep", "овца"),
            Word("pig", "свинья"),
            Word("rabbit", "кролик"),
            Word("mouse", "мышь")
        )),
        Topic("Еда", listOf(
            Word("apple", "яблоко"),
            Word("bread", "хлеб"),
            Word("cheese", "сыр"),
            Word("egg", "яйцо"),
            Word("milk", "молоко"),
            Word("meat", "мясо"),
            Word("rice", "рис"),
            Word("soup", "суп"),
            Word("sugar", "сахар"),
            Word("butter", "масло")
        )),
        Topic("Дом", listOf(
            Word("house", "дом"),
            Word("room", "комната"),
            Word("window", "окно"),
            Word("door", "дверь"),
            Word("table", "стол"),
            Word("chair", "стул"),
            Word("bed", "кровать"),
            Word("kitchen", "кухня"),
            Word("bathroom", "ванная"),
            Word("garden", "сад")
        )),
        Topic("Путешествия", listOf(
            Word("city", "город"),
            Word("village", "деревня"),
            Word("road", "дорога"),
            Word("bridge", "мост"),
            Word("river", "река"),
            Word("mountain", "гора"),
            Word("forest", "лес"),
            Word("field", "поле"),
            Word("park", "парк"),
            Word("travel", "путешествие")
        )),
        Topic("Технологии", listOf(
            Word("computer", "компьютер"),
            Word("phone", "телефон"),
            Word("internet", "интернет"),
            Word("algorithm", "алгоритм"),
            Word("network", "сеть"),
            Word("picture", "картина"),
            Word("game", "игра"),
            Word("keyboard", "клавиатура"),
            Word("robot", "робот"),
            Word("code", "код")
        ))
    )
} 